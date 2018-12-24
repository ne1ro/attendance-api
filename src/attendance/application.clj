(ns attendance.application)

(defn create-attendant [attendant-form] attendant-form)

(defn list-attendants [] [{ "first-name" "Vasya" "last-name" "Pupkin" "id" 1}])

(defn get-attendant [id] { "id" 1 })

(defn delete-attendant [attendant] (attendant))

; Private functions
; (defn- )
