(ns product-management.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [product-management.layout :refer [error-page]]
            [product-management.routes.home :refer [home-routes]]
            [compojure.route :as route]
            [product-management.middleware :as middleware]))

(def app-routes
  (routes
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
