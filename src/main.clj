(ns main
  (:use client.core)
  (:use ring.adapter.jetty)
  (:require service.web)
  (:gen-class))

(defn main-client[ ]
  (println "Starting client")
  (println "Here is a simple repl. To enter remote repl type 'replr[& [addr]]', where addr = localhost:3000 by default")
  (clojure.main/main))

(defn start-web []
  )

(defn main-server [& [addr]]
  (println "Starting server"))

(def usage "Usage: app client | app server [address]")

(defn -main [ & args ]
  (if (empty? args)
    (println usage)
    (let [[dispatch-arg & other] args]
      (case dispatch-arg
        "server" (main-server other)
        "client" (main-client)
        (println usage)))))

