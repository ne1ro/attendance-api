(ns attendance.application
  (:require [attendance.domain :as domain])
  (:require [attendance.infrastructure.persistence-sqlite :as persistence]))

(defn- set-status [attendance]
  (assoc attendance :status (case (:status attendance)
                              1 "attended"
                              0 "excused"
                              "unattended")))

(defn- calc-percentage [calc overall] (-> calc (/ overall) (* 100.0)))

(defn create-attendant [attendant-form]
  (->
    attendant-form
    (domain/save-attendant)
    (persistence/create-attendant)
    (->> (assoc attendant-form :id))))

(defn list-attendances [day] (map set-status (persistence/list-attendances day)))

(def list-attendants persistence/list-attendants)

(defn get-attendant [id]
  (let [attendant (persistence/get-attendant id)
        attendances (-> attendant (get :id) persistence/list-attendances-by-attendant)
        days-count (count (persistence/list-attendances-days))]
    (assoc attendant :attendancePercentage (calc-percentage (count attendances) days-count))))

(defn delete-attendant [id]
  (let [attendant (persistence/get-attendant id)] (persistence/delete-attendant id) attendant))

(defn list-attendances-days [] (map :day (persistence/list-attendances-days)))

(defn attend [attendant-id attendance-form]
  (let [attendance (assoc attendance-form :attendantId attendant-id)]
    (assoc attendance :id
                      (-> attendance (domain/attend) (persistence/create-attendance)))))

(defn unattend [attendant-id day]
  (let [attendance (persistence/get-attendance-by-day attendant-id day)]
    (persistence/delete-attendance attendance) attendance))
