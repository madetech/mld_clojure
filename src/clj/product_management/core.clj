(ns product-management.core
  (:require [product-management.handler :as handler]
            [luminus.repl-server :as repl]
            [luminus.http-server :as http]
            [luminus-migrations.core :as migrations]
            [product-management.config :refer [env]]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.tools.logging :as log]
            [product-management.env :refer [defaults]]
            [luminus.logger :as logger]
            [mount.core :as mount])
  (:gen-class))

(def cli-options
  [["-p" "--port PORT" "Port number"
    :parse-fn #(Integer/parseInt %)]])

(mount/defstate ^{:on-reload :noop}
                http-server
                :start
                (http/start
                  (-> env
                      (assoc :handler handler/app)
                      (update :port #(or (-> env :options :port) %))))
                :stop
                (http/stop http-server))

(mount/defstate ^{:on-reload :noop}
                repl-server
                :start
                (when-let [nrepl-port (env :nrepl-port)]
                  (repl/start {:port nrepl-port}))
                :stop
                (when repl-server
                  (repl/stop repl-server)))

(defn stop-app []
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (shutdown-agents))

(defn start-app [args]
  (doseq [component (-> args
                        (parse-opts cli-options)
                        mount/start-with-args
                        :started)]
    (log/info component "started"))
  (logger/init (:log-config env))
  ((:init defaults))
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app)))

(defn -main [& args]
  (cond
    (some #{"migrate" "rollback"} args)
    (do
      (mount/start #'product-management.config/env)
      (migrations/migrate args (env :database-url))
      (System/exit 0))
    :else
    (start-app args)))

(ns product-management.test.db.core
  (:require [product-management.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [product-management.config :refer [env]]
            [conman.core :refer [with-transaction]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'product-management.config/env
      #'product-management.db.core/*db*)
    (migrations/migrate ["migrate"](:database-url env))
    (f)))

(deftest test-product
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (let [product {:name "Test Product"
                   :slug "test-product"
                   :is_active 1}]
      (is (= 1 (db/create-product! t-conn product)))
      (let [result (db/get-products t-conn {})]
        (is (= 1 (count result)))
        (is (= product (dissoc (first result) :id)))))))
