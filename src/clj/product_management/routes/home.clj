(ns product-management.routes.home
  (:require [product-management.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn product-list-page []
  (layout/render
    "product-list.html"))

(defroutes home-routes
  (GET "/" [] (product-list-page)))
