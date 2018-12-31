(ns attendance.domain-test
  (:require [clojure.test :refer :all]
            [attendance.domain :refer :all]))

(def valid-attendant {:day "2018-12-12" :status true :attendantId 0})

(deftest attend-test
  (is (thrown-with-msg? Exception #"Value does not match schema:" (attend nil)))
  (is (thrown? Exception (attend {})))
  (is (thrown? Exception (attend {:day "17-17-17"})))
  (is (= "2018-12-12" (-> valid-attendant attend :day))))
