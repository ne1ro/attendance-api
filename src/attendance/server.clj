(ns attendance.server
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [attendance.core :refer [app]])
  (:gen-class))

(defn -main [& args]
  (run-jetty app {:port (Integer/valueOf (or (System/getenv "port") "8080"))}))
