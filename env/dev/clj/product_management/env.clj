(ns product-management.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [product-management.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[product_management started successfully using the development profile]=-"))
   :middleware wrap-dev})
