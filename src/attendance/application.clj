(ns attendance.application
  (:require
   [environ.core :refer [env]]
   [attendance.domain :as domain]
   [attendance.application.persistence :as p]
   [attendance.infrastructure.persistence-sqlite :refer [->PersistenceSQLite]]))

; (env :db-name)
(def conn
  (->PersistenceSQLite {:classname "org.sqlite.JDBC" :subprotocol "sqlite" :subname "attendance.db"}))

(defn- set-status [attendance]
  (assoc attendance :status (case (:status attendance)
                              1 "attended"
                              0 "excused"
                              "unattended")))

(defn- calc-percentage [calc overall] (-> calc (/ overall) (* 100.0)))

(defn create-attendant [attendant-form]
  (->>
   attendant-form
   domain/save-attendant
   (p/create-attendant conn)
   (assoc attendant-form :id)))

(defn list-attendances [day] (map set-status (p/list-attendances conn day)))

(defn list-attendants [] (p/list-attendants conn))

(defn list-attendances-days [] (map :day (p/list-attendances-days conn)))

(defn get-attendant [id]
  (let [attendant (p/get-attendant conn id)
        ; attendances (->> attendant :id (list-attendances-by-attendant conn))
        days-count (comp list-attendances-days count)]
    (assoc attendant :attendancePercentage (calc-percentage 0 days-count))))
    ; (assoc attendant :attendancePercentage (calc-percentage (count attendances) days-count))))

(defn delete-attendant [id]
  (let [attendant (get-attendant conn id)] (p/delete-attendant conn id) attendant))

(defn attend [attendant-id attendance-form]
  (let [attendance (assoc attendance-form :attendantId attendant-id)]
    (assoc attendance :id
           (-> attendance domain/attend (p/create-attendance conn)))))

(defn unattend [attendant-id day]
  (let [attendance (p/get-attendance-by-day conn attendant-id day)]
    (p/delete-attendance conn attendance) attendance))
