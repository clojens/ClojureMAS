(defproject clj-mas "0.0.2"
  :description "REST datastore interface. Server and a client"
  :url "http://github.com/FIXME"
  :main main
  :dependencies
    [;[org.clojure/clojure "1.3.0"]
     [android/clojure "1.4.0"]
     [ring/ring-jetty-adapter "0.2.5"]
     [ring-json-params "0.1.0"]
     [compojure "0.4.0"]
     [clj-json "0.2.0"]
     [clj-http "0.1.3"]]
  :plugins [[lein-ring "0.8.2"]]
  :profiles {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :ring {:handler service.web/app}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dev-dependencies
  [[lein-run "1.0.0-SNAPSHOT"]])
