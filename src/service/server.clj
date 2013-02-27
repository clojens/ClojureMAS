(ns service.server
  "This namespace for functions on server: starting closing, starting in new thread etc"
  (:require [ring.adapter.jetty :as jetty])
  (:require service.web)
  (:gen-class))

(defn start-server [ port ]
  (println "Starting server")
  (jetty/run-jetty service.web/handler {:port port :join? false}))
