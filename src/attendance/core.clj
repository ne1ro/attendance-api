(ns attendance.core
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]))

(def app
  (api
   (GET "/" []
     :query-params [name :- String]
     (ok {:message (str "Hello, " name)}))))
