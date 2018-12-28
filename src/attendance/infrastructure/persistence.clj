(ns attendance.infrastructure.persistence
  (:refer-clojure :exclude [update])
  (:require
   [clojure.java.jdbc :as jdbc]
   [honeysql.core :as sql]
   [honeysql.helpers :refer :all :as helpers]))

(def conn {:classname "org.sqlite.JDBC" :subprotocol "sqlite" :subname "attendance.db"})

(defn- query [q] (-> q sql/format (->> (jdbc/query conn))))
(def delete! #(jdbc/delete! conn %1 ["id = ?" %2]))

(defn- insert! [table, data]
  "Inserts data and returns ID"
  (-> conn (jdbc/insert! table data) first vals first))

(defn list-attendants []
  (-> (select :*) (from :attendants) (order-by [:lastName :asc]) query))

(defn get-attendant [id]
  (-> (select :*) (from :attendants) (where [:= :attendants.id id]) (limit 1) query first))

(defn create-attendant [attendant-form] (insert! :attendants attendant-form))

(defn delete-attendant [id] (delete! :attendants id))

(defn list-attendancies-days []
  (->
   (select :day)
   (modifiers :distinct)
   (from :attendancies)
   (order-by [:day :desc])
   query))

(defn list-attendancies [day]
  (->
   (select :attendants.* :attendancies.status)
   (from :attendants)
   (left-join :attendancies
              [:and
               [:= :attendancies.attendantId :attendants.id]
               [:= :attendancies.day day]])
   (order-by [:attendants.lastName :asc])
   query))

(defn get-attendance-by-day [attendant-id day]
  (->
   (select :*)
   (from :attendancies)
   (where [:= :attendancies.attendantId attendant-id] [:= :attendancies.day day])
   (limit 1)
   query first))

(defn create-attendance [attendance-form] (insert! :attendancies attendance-form))
(defn delete-attendance [{id :id}] (delete! :attendancies id))
