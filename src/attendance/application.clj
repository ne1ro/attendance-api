(ns attendance.application
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.test.alpha :as stest]
   [environ.core :refer [env]]
   [attendance.domain :as domain]
   [attendance.application.persistence :as p]
   [camel-snake-kebab.core :refer :all]
   [attendance.infrastructure.persistence-sqlite :refer [->PersistenceSQLite]]))

(s/def ::token string?)

; (env :db-name)
(def conn
  (->PersistenceSQLite {:classname "org.sqlite.JDBC" :subprotocol "sqlite" :subname "attendance.db"}))

(defn- set-status [{status :status :as item}]
  (let [str-status (case status
                     1 "attended"
                     0 "excused"
                     "unattended")]
    (assoc item :status str-status)))

(defn- calc-percentage [calc overall]
  (if (pos? overall) (-> calc (/ overall) (* 100.0)) 0))

(def token-exists? (partial p/token-exists? conn))
(s/fdef token-exists? :args (s/cat :token ::token) :ret [::status])

(defn create-attendant [attendant-form]
  (->>
   attendant-form
   domain/save-attendant
   (p/create-attendant conn)
   (assoc attendant-form :id)))
(s/fdef create-attendant :args [::attendant] :ret [::attendant])

(defn list-attendances [day] (map set-status (p/list-attendances conn day)))
(s/fdef create-attendant :args [::day] :ret [(s/coll-of :attendance)])

(defn list-attendances-days [] (map :day (p/list-attendances-days conn)))
(s/fdef list-attendances-days :args [] :ret [(s/coll-of :day)])

(defn get-attendant [id]
  (let [attendant (p/get-attendant conn id)
        attendances-count (->> attendant :id (p/list-attendances-by-attendant conn) count)
        days-count (count (p/list-attendances-days conn))]
    (if attendant
      (assoc attendant
             :attendance-percentage (calc-percentage attendances-count days-count)
             :attendances-count attendances-count
             :days-count days-count))))
(s/fdef get-attendant :args [::attendant-id] :ret [:attendant])

(defn delete-attendant [id]
  (let [attendant (p/get-attendant conn id)] (p/delete-attendant conn id) attendant))

(defn attend [attendant-id attendance-form]
  (let [attendance (assoc attendance-form :attendant-id attendant-id)]
    (assoc attendance :id (->> attendance domain/attend (p/create-attendance conn)))))

(defn unattend [attendant-id day]
  (let [attendance (p/get-attendance-by-day conn attendant-id day)]
    (p/delete-attendance conn attendance) attendance))

(stest/instrument [`token-exists? `create-attendant `get-attendant])
