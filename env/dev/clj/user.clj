(ns user
  (:require [mount.core :as mount]
            product-management.core))

(defn start []
  (mount/start-without #'product-management.core/repl-server))

(defn stop []
  (mount/stop-except #'product-management.core/repl-server))

(defn restart []
  (stop)
  (start))


