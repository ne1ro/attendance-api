(ns attendance.core
  (:require [compojure.api.sweet :refer :all]
            [attendance.application :as application]
            [schema.core :as s]
            [ring.util.http-response :refer :all]))

(s/defschema Attendant {:firstName s/Str :lastName s/Str (s/optional-key :id) s/Int})

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
     (ok (application/list-attendancies-days)))))
