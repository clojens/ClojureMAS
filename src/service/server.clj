(ns service.server
  "This namespace for functions on server: starting closing, starting in new thread etc"
  (:require [ring.adapter.jetty :as jetty])
  (:require service.web)
  (:gen-class))

(let [local-servers (atom {})] ; { port server }
  (defn server-start [ port ]
    "starts a server on port. If no association with a server creates new one"
    (println "Starting server")
    (if (= (@local-servers port) nil)
      (let [server (jetty/run-jetty service.web/handler
                                    {:port port :join? false})]
        (swap! local-servers assoc port server)
        server)
      (let [server (@local-servers port)]
        (.start server)
        server)))

  (defn server-shutdown [ port ]
    "tries to find a server by port and stop it"
    (let [ server (@local-servers port) ]
      (when server
        (println "Stopping server")
        (.stop server))))

  (defn server-running? [ port ]
    (let [server (@local-servers port)]
      (and server
           (.isRunning server)))))
