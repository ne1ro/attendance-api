(ns attendance.infrastructure.persistence
  (:refer-clojure :exclude [update])
  (:require
   [clojure.java.jdbc :as jdbc]
   [honeysql.core :as sql]
   [honeysql.helpers :refer :all :as helpers]))

(def conn {:classname "org.sqlite.JDBC" :subprotocol "sqlite" :subname "attendance.db"})

(defn- query [q] (-> q sql/format (->> (jdbc/query conn))))

(defn- insert! [table, data]
  "Inserts data and returns ID"
  (-> conn (jdbc/insert! table data) first vals first))

(defn list-attendants [] (-> (select :*) (from :attendants) query))

(defn get-attendant [id]
  (-> (select :*) (from :attendants) (where [:= :attendants.id id]) (limit 1) query first))

(defn create-attendant [attendant-form] (insert! :attendants attendant-form))

(defn delete-attendant [id] (jdbc/delete! conn :attendants ["id = ?" id]))

(defn list-attendancies-days []
  (->
   (select :day)
   (modifiers :distinct)
   (from :attendancies)
   (order-by [:day :desc])
   query))
