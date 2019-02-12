(ns attendance.application-test
  (:require [clojure.test :refer :all]
            [attendance.application :refer :all])
  (:import (com.github.javafaker Faker)))

(def faker (Faker.))

(deftest token-exists?-test
  (with-redefs [->PersistenceSQLite ->PersistenceTest]
    (is (thrown? Exception (token-exists? nil)))
    (is (thrown? Exception (token-exists? 0)))
    (is (token-exists? "test"))))
