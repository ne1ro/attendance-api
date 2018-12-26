(ns attendance.domain (:require [schema.core :as s]))

(s/defschema AttendanceForm
  {:firstName s/Str :lastName s/Str})

(defn save-attendant [attendance-form]  (s/validate AttendanceForm attendance-form))
