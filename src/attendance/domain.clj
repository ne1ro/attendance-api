(ns attendance.domain
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(def day #"(\d{4})-(\d{2})-(\d{2})")

(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::day (s/and string? #(re-matches day %)))
(s/def ::attendant-id string?)
(s/def ::status boolean?)

(s/def ::attendant (s/keys :req [::first-name ::last-name]))
(s/def ::attendance (s/keys :req [::day ::attendant-id] :opt [::status]))

(defn save-attendant [attendant-form] attendant-form)
(defn attend [attendance-form] attendance-form)

(s/fdef save-attendant :args (s/cat :attendant-form ::attendant))
(s/fdef attend :args (s/cat :attendance-form ::attendance))
(stest/instrument `save-attendant)
(stest/instrument `attend)
