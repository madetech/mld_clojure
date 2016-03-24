(ns product-management.routes.product
  (:require [product-management.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [product-management.db.core :as db]
            [bouncer.core :as b :only [defvalidator]]
            [bouncer.validators :as v]
            [ring.util.response :refer [redirect]]))

;
; ACTIONS
;

(defn product-list-page []
  (layout/render
    "product/list.html"
    (merge {:products (db/get-products)})))

(defn product-list-inactive-page []
  (layout/render
    "product/inactive.html"
    (merge {:products (db/get-inactive-products)})))

(defn product-create-page []
  (layout/render
    "product/create.html"))

(defn product-show-page [slug]
  (layout/render
    "product/show.html"
    (merge {:product (db/get-product {:slug slug})})))

(defn product-edit-page [id]
  (layout/render
    "product/edit.html"
    (merge {:product (db/get-product-by-id {:id id})})))

;
; UTILITY
;

(defn validate-product [params]
  (first
    (b/validate
      params
      :name [v/required [v/min-count 2]])))

(defn create-slug-from-name [name]
  (clojure.string/lower-case (clojure.string/replace name #"\s+" "-")))

(defn create-product! [{:keys [params]}]
  (if-let [errors (validate-product params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/create-product!
        (assoc params :is_active true :slug (create-slug-from-name (params :name))))
      (response/found "/"))))

(defn update-product! [{:keys [params]}]
  (if-let [errors (validate-product params)]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/update-product!
        (assoc params :is_active true :slug (params :slug)))
      (response/found "/"))))


;
; ROUTES
;

(defroutes product-routes
  (GET "/" [] (product-list-page))
  (GET "/product/inactive" [] (product-list-inactive-page))
  (GET "/product/create" [] (product-create-page))
  (POST "/product/create" request (create-product! request))
  (GET "/product/edit/:id" [id] (product-edit-page id))
  (POST "/product/update" request (update-product! request))
  (POST "/" request (create-product! request))
  (GET "/product/:slug" [slug] (product-show-page slug)))
