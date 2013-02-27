(ns main
  "Entry point of a program"
  (:use client.core)
  (:use [service.server :only [server-start]])
  (:gen-class))

(def usage "Usage: app client | app server [port]")

(defn main-client[ ]
  (println "Starting client")
  (println "Here is a simple repl. To enter remote repl type 'replr[& [addr]]', where addr = localhost:3000 by default")
  (binding [*ns* (find-ns 'main)]
    (client.core/repl)))

(defn main-server [ [port] ]
  (server-start (read-string (or port "3000"))))

(defn -main [ & args ]
  (if (empty? args)
    (println usage)
    (let [[dispatch-arg & other] args]
      (case dispatch-arg
        "server" (main-server other)
        "client" (main-client)
        (println usage)))))

