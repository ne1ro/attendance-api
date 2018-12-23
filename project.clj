(defproject attendance "0.1.0-SNAPSHOT"
  :description "Attendance API built in Clojure"
  :url "https://github.com/ne1ro"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[cheshire "5.8.1"]
								 [org.clojure/tools.namespace "0.2.11"]
                 [funcool/cats "2.3.2"]
                 [honeysql "0.9.4"]
                 [metosin/compojure-api "1.1.11"]
                 [mount "0.1.15"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/core.match "0.3.0-alpha5"]
                 [prismatic/schema "1.1.9"]
                 [cljfmt "0.5.1"]]
  :ring  {:handler attendance.core/app}
  :uberjar-name "attendance.jar"
  :repl-options {:init-ns attendance.core}
	:profiles {
		:dev {:plugins [
		[cider/cider-nrepl "0.18.0"] [lein-autoreload "0.1.1"] [jonase/eastwood "0.3.3"]
    [lein-ring "0.12.4"]
	] } }
  :main attendance.core)
