(ns attendance.core
  (:require [compojure.api.sweet :refer :all]
            [attendance.application :as application]
            [schema.core :as s]
            [ring.util.http-response :refer :all]))

(def app
  (api
   (GET "/attendants" []
     (ok (application/list-attendants)))

   (GET "/attendants/:id" []
     :path-params [id :- s/Int]
     (ok (application/get-attendant id)))))
