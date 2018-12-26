(ns attendance.infrastructure.persistence
  (:refer-clojure :exclude [update])
  (:require
   [clojure.java.jdbc :as jdbc]
   [honeysql.core :as sql]
   [honeysql.helpers :refer :all :as helpers]))

(def conn {:classname "org.sqlite.JDBC" :subprotocol "sqlite" :subname "attendance.db"})

(defn- query [q] (-> q sql/format (->> (jdbc/query conn))))
(defn- exec! [q] (-> q sql/format (->> (jdbc/execute! conn))))

(defn list-attendants [] (-> (select :*) (from :attendants) query))

(defn get-attendant [id]
  (-> (select :*) (from :attendants) (where [:= :attendants.id id]) (limit 1) query first))

(defn create-attendant [attendant-form]
  (let [first-name (:firstName attendant-form) last-name (:lastName attendant-form)]
    (-> (insert-into :attendants) (values [attendant-form]) exec!)))
