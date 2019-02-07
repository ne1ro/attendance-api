(ns attendance.infrastructure.persistence-test
  (:require [attendance.application.persistence :refer [Persistence]]))

(defrecord PersistenceTest [conn]
  Persistence

  (token-exists? [_conn token] (when (= "test" token) true))

  (get-attendant [_conn id] {:id id :first-name "Test" :last-name "User"})

  (create-attendant [_conn attendant-form] attendant-form)

  (delete-attendant [_conn id] id)

  (list-attendances-days [_conn] ["2019-02-05"])

  (list-attendances-by-attendant [_conn id] [{:id id :first-name "Test" :last-name "User" :status 1 :day "2019-02-05"}])

  (list-attendances [_conn day] [{:id 1 :first-name "Test" :last-name "User" :status 1 :day day}])

  (get-attendance-by-day [_conn attendant-id day] {:id attendant-id :day day})

  (create-attendance [_conn attendance-form] attendance-form)

  (delete-attendance [conn {id :id}] id))
