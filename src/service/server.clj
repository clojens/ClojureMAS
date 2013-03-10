(ns service.server
  "This namespace for functions on server: starting closing, starting in new thread etc"
  (:require [ring.adapter.jetty :as jetty])
  (:require service.web)
  (:gen-class))

(defonce local-servers (atom {})) ; { port server }

;; public API

(defn server-start 
  "Starts a server on port. If no association with a server creates new one"
  [ port ]
  (println "Starting server")
  (if (= (@local-servers port) nil)
    (let [server (jetty/run-jetty service.web/handler
                                  {:port port :join? false})]
      (swap! local-servers assoc port server)
      server)
    (let [server (@local-servers port)]
      (.start server)
      server)))

(defn server-stop
  "Tries to find a server by port and stop it"
   [ port ]
  (let [ server (@local-servers port) ]
    (when server
      (println "Stopping server")
      (.stop server))))

(defn server-running?
  "Checks if server is running by given port in frames of this application"
  [ port ]
  (let [server (@local-servers port)]
    (and server
         (.isRunning server))))

(defn server-available-servers
  "returns a list of ports of the servers"
  [ ]
  (filter server-running? (map (fn[[port _]]port) @local-servers)))
