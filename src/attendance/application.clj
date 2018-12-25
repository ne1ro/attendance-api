(ns attendance.application
  (:require [attendance.infrastructure.persistence :as persistence]
            [honeysql.core :as sql]
            [honeysql.helpers :refer :all :as helpers]
            )
  )

(defn create-attendant [attendant-form] attendant-form)

(defn list-attendants [] (persistence/list-attendants []))

(defn get-attendant [id] (persistence/get-attendant id))

(defn delete-attendant [attendant] (attendant))

; Private functions
; (defn- )
