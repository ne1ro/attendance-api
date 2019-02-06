(ns attendance.application-test
  (:require [clojure.test :refer :all] [attendance.application :refer :all])
  (:import (com.github.javafaker Faker)))

(def faker (Faker.))

(deftest token-exists?-test
  (is (thrown-with-msg? Exception #error (token-exists? nil)))
  (is (thrown? Exception (token-exists? "")))
  (is (token-exists? 1)))
