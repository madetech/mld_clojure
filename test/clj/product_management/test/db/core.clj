(ns product-management.test.db.core
  (:require [product-management.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [product-management.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'product-management.config/env
      #'product-management.db.core/*db*)
    (migrations/migrate ["migrate"] (env :database-url))
    (f)))

(deftest test-products
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (is (= 1 (db/create-product!
               t-conn
               {:id        "1"
                :name      "Test Product"
                :slug      "test-product"
                :price     99.00M
                :image_url "test"
                :is_active true
                })))
    (is (= {:id        1
            :name      "Test Product"
            :slug      "test-product"
            :price      99.00M
            :image_url "test"
            :is_active true }
           (db/get-product t-conn {:slug "test-product"})))))
