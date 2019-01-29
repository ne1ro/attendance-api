(ns attendance.domain-test
  (:require [clojure.test :refer :all]
            [attendance.domain :refer :all])
  (:import (com.github.javafaker Faker)))

(def faker (Faker.))
(def valid-attendance {:day "2018-12-12" :status true :attendant-id 0})
(def valid-attendant
  {:first-name (-> faker .witcher .monster) :last-name (-> faker .witcher .school)})

(deftest attend-test
  (is (thrown? Exception (attend {})))
  (is (thrown? Exception (attend {:day "17-17-17"})))
  (is (= "2018-12-12" (-> valid-attendance attend :day))))

(deftest save-attend-test
  (is (thrown? Exception (save-attendant nil)))
  (is (thrown? Exception (save-attendant {})))
  (is (thrown? Exception (save-attendant {:first-name nil :last-name nil})))
  (is (= (:first-name valid-attendant) (-> valid-attendant save-attendant :first-name))))
