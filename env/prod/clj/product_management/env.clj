(ns product-management.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[product_management started successfully]=-"))
   :middleware identity})
