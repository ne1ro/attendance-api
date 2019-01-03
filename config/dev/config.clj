(def config
  {:db
   {:adapter 'PersistenceSQLite
    :conn    {:classname "org.sqlite.JDBC" :subprotocol "sqlite" :subname "attendance.db"}}})