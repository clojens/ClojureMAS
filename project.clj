(defproject cabinet "0.0.1"
  :description "REST datastore interface."
  :dependencies
    [[org.clojure/clojure "1.3.0"]
     [ring/ring-jetty-adapter "0.2.5"]
     [ring-json-params "0.1.0"]
     [compojure "0.4.0"]
     [clj-json "0.2.0"]]
  :plugins [[lein-ring "0.8.2"]]
  :profiles {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :ring {:handler cabinet.web/app}
  :dev-dependencies
  [[lein-run "1.0.0-SNAPSHOT"]])