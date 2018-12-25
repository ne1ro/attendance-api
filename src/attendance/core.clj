(ns attendance.core
  (:require [compojure.api.sweet :refer :all]
            [attendance.application :as application]
            [ring.util.http-response :refer :all]))

(def app
  (api
   (GET "/attendants" []
     (ok (application/list-attendants))))

   (GET "/attendant/:id" []
     (ok (application/get-attendant 1))))
