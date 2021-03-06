(ns attendance.core
  (:require [compojure.api.sweet :refer :all]
            [compojure.api.api]
            [attendance.application :as application]
            [camel-snake-kebab.core :refer :all]
            [schema.core :as s]
            [ring.logger :as logger]
            [clojure.spec.test.alpha :as stest]
            [ring.util.http-response :refer :all]))

; TODO: replace with clojure.spec?
(s/defschema Attendance {:day s/Str :status s/Bool})
(s/defschema Attendant {:first-name s/Str :last-name s/Str})

(defn- key-json->clj [x] (->kebab-case (if (keyword? x) (name x) x)))

(defn- key-clj->json [x] (->camelCase (if (keyword? x) (name x) x)))

(def clean-app
  (api
   {:format {:formats       [:json]
             :params-opts   {:json {:key-fn key-json->clj}}
             :response-opts {:json {:key-fn key-clj->json}}}}

   (GET "/attendants/:id" []
     :path-params [id :- s/Int]
     (ok (application/get-attendant id)))

   (POST "/attendants" []
     :body [attendant-form Attendant]
     (created "/attendants" (application/create-attendant attendant-form)))

   (DELETE "/attendants/:id" []
     :path-params [id :- s/Int]
     (ok (application/delete-attendant id)))

   (GET "/attendances_days" []
     (ok (application/list-attendances-days)))

   (GET "/attendances/:day" []
     :path-params [day :- s/Str]
     (ok (application/list-attendances day)))

   (POST "/attendants/:attendant-id/attendances" []
     :path-params [attendant-id :- s/Int]
     :body [attendance-form Attendance]
     (created "/attendants/:id/attendances"
              (application/attend attendant-id attendance-form)))

   (DELETE "/attendants/:attendant-id/attendances/:day" []
     :path-params [attendant-id :- s/Int day :- s/Str]
     (ok (application/unattend attendant-id day)))))

(defn- render-auth-err [response] (assoc response :status 401 :body nil))

(defn- authenticated? [{headers :headers}]
  (-> headers (get "x-auth-token") (application/token-exists?)))

(defn wrap-auth [handler]
  (fn
    ([request]
     (if (authenticated? request)
       (handler request)
       (-> request handler render-auth-err)))

    ([request respond raise]
     (if (authenticated? request)
       (handler request respond raise))
     (handler request (comp render-auth-err respond) (raise (Exception. "Unauthenticated"))))))

(def app (-> clean-app (wrap-auth) (logger/wrap-with-logger)))
