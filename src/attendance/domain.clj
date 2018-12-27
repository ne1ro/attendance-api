(ns attendance.domain (:require [schema.core :as s]))

(def day #"(\d{4})-(\d{2})-(\d{2})")

(s/defschema Attendant {:firstName s/Str :lastName s/Str})
(s/defschema Attendance {:day day :attendantId s/Int :status s/Bool})

(defn save-attendant [attendant-form] (s/validate Attendant attendant-form))
(defn attend [attendance-form] (s/validate Attendance attendance-form))
