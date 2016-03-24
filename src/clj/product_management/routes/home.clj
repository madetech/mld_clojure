(ns product-management.routes.home
  (:require [product-management.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [product-management.db.core :as db]
            [clojure.java.io :as io]))

(defn product-list-page []
  (layout/render
    "product-list.html"
    (merge {:products (db/get-products)})))

(defroutes home-routes
  (GET "/" [] (product-list-page)))
