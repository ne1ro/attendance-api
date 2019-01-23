(ns attendance.infrastructure.persistence-sqlite
  (:refer-clojure :exclude [update])
  (:require
   [attendance.application.persistence :refer [Persistence]]
   [camel-snake-kebab.core :refer :all]
   [camel-snake-kebab.extras :refer [transform-keys]]
   [clojure.java.jdbc :as jdbc]
   [clojure.string :as str]
   [honeysql.core :as sql]
   [honeysql.helpers :refer :all :as helpers]))

(defn- query [q conn]
  (-> conn :conn (jdbc/query (sql/format q))))

(defn- delete! [conn table id]
  (jdbc/delete! (:conn conn) table ["id = ?" id]))

(defn- insert! [{conn :conn} table data]
  (-> conn (jdbc/insert! table (transform-keys ->snake_case_keyword data)) first vals first))

(defrecord PersistenceSQLite [conn]
  Persistence

  (get-attendant [conn id]
    (->
     (select :attendants.* :attendancies.day)
     (from :attendants)
     (left-join :attendancies [:= :attendancies.attendant-id :attendants.id])
     (where [:= :attendants.id id])
     (query conn)
     first))

  (create-attendant [conn attendant-form] (insert! conn :attendants attendant-form))

  (delete-attendant [conn id] (delete! conn :attendants id))

  (list-attendances-days [conn]
    (->
     (select :day)
     (modifiers :distinct)
     (from :attendancies)
     (order-by [:day :desc])
     (query conn)))

  (list-attendances-by-attendant [conn id]
    (->
     (select :*)
     (from :attendancies)
     (where [:and [:= :attendancies.attendant-id id] [:= :attendancies.status true]])
     (query conn)))

  (list-attendances [conn day]
    (->
     (select :attendants.* :attendancies.status)
     (from :attendants)
     (left-join :attendancies
                [:and
                 [:= :attendancies.attendant-id :attendants.id]
                 [:= :attendancies.day day]])
     (order-by [:attendants.last-name :asc])
     (query conn)))

  (get-attendance-by-day [conn attendant-id day]
    (->
     (select :*)
     (from :attendancies)
     (where [:= :attendancies.attendant-id attendant-id] [:= :attendancies.day day])
     (limit 1)
     (query conn)
     first))

  (create-attendance [conn attendance-form] (insert! conn :attendancies attendance-form))

  (delete-attendance [conn {id :id}] (delete! conn :attendancies id)))
