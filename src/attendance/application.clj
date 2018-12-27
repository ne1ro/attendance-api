(ns attendance.application
  (:require [attendance.domain :as domain])
  (:require [attendance.infrastructure.persistence :as persistence]))

(defn create-attendant [attendant-form]
  (->
    attendant-form
    (domain/save-attendant)
    (persistence/create-attendant)
    (->> (assoc attendant-form :id))))

(defn list-attendants [] (persistence/list-attendants))

(defn get-attendant [id] (persistence/get-attendant id))

(defn delete-attendant [attendant] (attendant))

; Private functions
; (defn- )
