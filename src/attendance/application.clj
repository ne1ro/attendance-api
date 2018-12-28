(ns attendance.application
  (:require [attendance.domain :as domain])
  (:require [attendance.infrastructure.persistence :as persistence]))

(defn- set-status [attendance]
  (assoc attendance :status (case (:status attendance)
                              1 "attended"
                              0 "excused"
                              "unattended")))

(defn create-attendant [attendant-form]
  (->
   attendant-form
   (domain/save-attendant)
   (persistence/create-attendant)
   (->> (assoc attendant-form :id))))

(defn list-attendancies [day] (map set-status (persistence/list-attendancies day)))
(def list-attendants persistence/list-attendants)
(def get-attendant persistence/get-attendant)

(defn delete-attendant [id]
  (let [attendant (get-attendant id)] (persistence/delete-attendant id) attendant))

(defn list-attendancies-days [] (map :day (persistence/list-attendancies-days)))

(defn attend [attendant-id attendance-form]
  (let [attendance (assoc attendance-form :attendantId attendant-id)]
    (assoc attendance :id
           (-> attendance (domain/attend) (persistence/create-attendance)))))

(defn unattend [attendant-id day]
  (let [attendance (persistence/get-attendance-by-day attendant-id day)]
    (persistence/delete-attendance attendance) attendance))
