(defproject attendance "0.1.0-SNAPSHOT"
  :description "Attendance API built in Clojure"
  :url "https://github.com/ne1ro/attendance-api"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[com.github.javafaker/javafaker "0.16"]
                 [environ "1.1.0"]
                 [honeysql "0.9.4"]
                 [metosin/compojure-api "1.1.11"]
                 [mount "0.1.15"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [org.flatland/ordered "1.5.7"]
                 [org.xerial/sqlite-jdbc "3.25.2"]
                 [prismatic/schema "1.1.9"]]
  :ring {:handler attendance.core/app :nrepl {:start? true :port 9998}}
  :uberjar-name "attendance.jar"
  :plugins [[lein-environ "1.1.0"]]
  :middleware [whidbey.plugin/repl-pprint]
  :main attendance.core
  :aot [attendance.core]
  :repl-options {:init-ns attendance.core
                 :prompt  (fn [ns] (str "\u001B[35m[\u001B[34m" ns "\u001B[35m]\u001B[33mλ:\u001B[m "))
                 :welcome ()})
