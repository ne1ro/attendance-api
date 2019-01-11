(ns attendance.infrastructure.persistence-sqlite
  (:refer-clojure :exclude [update])
  (:require
   [attendance.application.persistence :refer [Persistence]]
   [clojure.java.jdbc :as jdbc]
   [honeysql.core :as sql]
   [honeysql.helpers :refer :all :as helpers]))

(defn- query [q conn] (-> q sql/format (->> (jdbc/query (:conn conn)))))

(defn- delete! [conn table id] (jdbc/delete! (:conn conn) table ["id = ?" id]))

(defn- insert! [conn table data]
  "Inserts data and returns ID"
  (-> conn :conn (jdbc/insert! table data) first vals first))

(defrecord PersistenceSQLite [conn]
  Persistence

  (list-attendants [conn]
    (-> (select :*) (from :attendants) (order-by [:lastName :asc]) (query conn)))

  (get-attendant [conn id]
    (->
     (select :attendants.* :attendancies.day)
     (from :attendants)
     (left-join :attendancies [:= :attendancies.attendantId :attendants.id])
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
     (where [:and [:= :attendancies.attendantId id] [:= :attendancies.status true]])
     (query conn)))

  (list-attendances [conn day]
    (->
     (select :attendants.* :attendancies.status)
     (from :attendants)
     (left-join :attendancies
                [:and
                 [:= :attendancies.attendantId :attendants.id]
                 [:= :attendancies.day day]])
     (order-by [:attendants.lastName :asc])
     (query conn)))

  (get-attendance-by-day [conn attendant-id day]
    (->
     (select :*)
     (from :attendancies)
     (where [:= :attendancies.attendantId attendant-id] [:= :attendancies.day day])
     (limit 1)
     (query conn)
     first))

  (create-attendance [conn attendance-form] (insert! conn :attendancies attendance-form))

  (delete-attendance [conn {id :id}] (delete! conn :attendancies id)))
