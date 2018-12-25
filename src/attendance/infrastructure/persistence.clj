(ns attendance.infrastructure.persistence)

(defn list-attendants []
  (-> (select :*) (from :attendants)))

(defn get-attendant [id]
  (-> (select :*) (from :attendants) (where [:= :attendants.id id]))
