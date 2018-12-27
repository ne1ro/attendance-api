(ns attendance.core
  (:require [compojure.api.sweet :refer :all]
            [attendance.application :as application]
            [schema.core :as s]
            [ring.util.http-response :refer :all]))

(s/defschema Attendant {:firstName s/Str :lastName s/Str})
(s/defschema Attendance {:day s/Str :status s/Bool})

(def app
  (api
   (GET "/attendants" []
     (ok (application/list-attendants)))

   (GET "/attendants/:id" []
     :path-params [id :- s/Int]
     (ok (application/get-attendant id)))

   (POST "/attendants" []
     :body [attendant-form Attendant]
     (created "/attendants" (application/create-attendant attendant-form)))

   (DELETE "/attendants/:id" []
     :path-params [id :- s/Int]
     (ok (application/delete-attendant id)))

   (GET "/attendancies_days" []
     (ok (application/list-attendancies-days)))

   (POST "/attendants/:attendantId/attendancies" []
     :path-params [attendantId :- s/Int]
     :body [attendance-form Attendance]
     (created "/attendants/:id/attendances"
              (application/attend attendantId attendance-form)))

   (DELETE "/attendants/:attendantId/attendancies/:day" []
     :path-params [attendantId :- s/Int day :- s/Str]
     (ok (application/unattend attendantId day)))))
