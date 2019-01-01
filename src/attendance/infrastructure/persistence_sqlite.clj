(ns attendance.infrastructure.persistence-sqlite
  (:refer-clojure :exclude [update])
  (:require
    [attendance.application.persistence :refer [Persistence]]
    [clojure.java.jdbc :as jdbc]
    [honeysql.core :as sql]
    [honeysql.helpers :refer :all :as helpers]))

; (def conn {:classname "org.sqlite.JDBC" :subprotocol "sqlite" :subname "attendance.db"})

(defn- query [conn q] (-> q sql/format (->> (jdbc/query conn))))
(defn- delete! [conn table id] (jdbc/delete! conn table ["id = ?" id]))
(defn- insert! [conn table data]
  "Inserts data and returns ID"
  (-> conn (jdbc/insert! table data) first vals first))

(defrecord PersistenceSQLite [conn] Persistence
  (list-attendants []
    (-> (select :*) (from :attendants) (order-by [:lastName :asc]) query))

  (get-attendant [id]
    (->
      (select :attendants.* :attendancies.day)
      (from :attendants)
      (left-join :attendancies [:= :attendancies.attendantId :attendants.id])
      (where [:= :attendants.id id])
      query first))

  (create-attendant [attendant-form] (insert! :attendants attendant-form))

  (delete-attendant [id] (delete! :attendants id))

  (list-attendances-days []
    (->
      (select :day)
      (modifiers :distinct)
      (from :attendancies)
      (order-by [:day :desc])
      query))

  (list-attendances-by-attendant [id]
    (->
      (select :*)
      (from :attendancies)
      (where [:and [:= :attendancies.attendantId id] [:= :attendancies.status true]])
      query))

  (list-attendances [day]
    (->
      (select :attendants.* :attendancies.status)
      (from :attendants)
      (left-join :attendancies
                 [:and
                  [:= :attendancies.attendantId :attendants.id]
                  [:= :attendancies.day day]])
      (order-by [:attendants.lastName :asc])
      query))

  (get-attendance-by-day [attendant-id day]
    (->
      (select :*)
      (from :attendancies)
      (where [:= :attendancies.attendantId attendant-id] [:= :attendancies.day day])
      (limit 1)
      query first))

  (create-attendance [attendance-form] (insert! :attendancies attendance-form)))
(defn delete-attendance [{id :id}] (delete! :attendancies id))