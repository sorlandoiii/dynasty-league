(defproject dynasty-league "0.1.0-SNAPSHOT"
  :description "Fantasy Football app for dynasty league."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot dynasty-league.core
  :repl-options {:init-ns user}
  :target-path "target/%s"
  :plugins [[cider/cider-nrepl "0.9.1"]]
  :profiles {:dev {:resource-paths ["dev"]
                   :dependencies [[javax.servlet/servlet-api "2.5"]
                                  [org.clojure/tools.namespace "0.2.10"]
                                  [ring-mock "0.1.5"]]}
             :uberjar {:aot :all}})

