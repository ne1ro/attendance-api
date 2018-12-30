(ns attendance.domain-test
  (:require [clojure.test :refer :all]
            [attendance.domain :refer :all]))

(deftest attend-test (testing "Throw error on nil" ((
                                                      is (thrown? (Exception (attend (nil)))))
                                                     )))
