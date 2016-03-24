(ns product-management.routes.product
  (:require [product-management.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [product-management.db.core :as db]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [ring.util.response :refer [redirect]]))

(defn product-list-page []
  (layout/render
    "product-list.html"
    (merge {:products (db/get-products)})))

(defroutes product-routes
  (GET "/" [] (product-list-page)))

(defn validate-message [params]
  (first
    (b/validate
      params
      :name [v/required [v/min-count 2]])))
