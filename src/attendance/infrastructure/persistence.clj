(ns attendance.infrastructure.persistence
  (:require [honeysql.core :as sql]
           [honeysql.helpers :refer :all :as helpers]))

(def db {:classname   "org.sqlite.JDBC", :subprotocol "sqlite", :subname "attendance.db"})

(defn list-attendants []
  (-> (select :*) (from :attendants)))

(defn get-attendant [id]
  (-> (select :*) (from :attendants) (where [:= :attendants.id id]))
