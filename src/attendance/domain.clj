(ns attendance.domain
  (:require [schema.core :as s]))

(def day #"(\d{4})-(\d{2})-(\d{2})")

; TODO: replace with spec
(s/defschema Attendant {:first-name s/Str :last-name s/Str})
(s/defschema Attendance {:day day :attendant-id s/Int :status s/Bool})

(defn save-attendant [attendant-form] (s/validate Attendant attendant-form))
(defn attend [attendance-form] (s/validate Attendance attendance-form))
