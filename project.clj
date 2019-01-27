(defproject attendance "0.1.0-SNAPSHOT"
  :description "Attendance API built in Clojure"
  :url "https://github.com/ne1ro/attendance-api"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[ring-middleware-format "0.7.2"]
                 [camel-snake-kebab "0.4.0"]
                 [com.github.javafaker/javafaker "0.16"]
                 [environ "1.1.0"]
                 [honeysql "0.9.4"]
                 [metosin/compojure-api "1.1.11"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [org.flatland/ordered "1.5.7"]
                 [org.xerial/sqlite-jdbc "3.25.2"]
                 [prismatic/schema "1.1.9"]
                 [ring-logger "1.0.1"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]]
  :ring {:handler attendance.core/app :nrepl {:start? true :port 9998}}
  :uberjar-name "attendance.jar"
  :middleware [whidbey.plugin/repl-pprint]
  :plugins [[lein-environ "1.1.0"] [lein-kibit "0.1.6"] [mvxcvi/whidbey "2.0.0"] [jonase/eastwood "0.3.4"]]
  :main attendance.core
  :aot [attendance.core]
  :profiles {:uberjar {:aot :all :main attendance.server}}
  :repl-options {:init-ns attendance.core
                 :prompt  (fn [ns] (str "\u001B[35m[\u001B[34m" ns "\u001B[35m]\u001B[33mλ:\u001B[m "))
                 :welcome ()})
