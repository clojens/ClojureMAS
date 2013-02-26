(ns main
  (:use client.core)
  (:use ring.adapter.jetty)
  (:require service.web)
  (:require clojure.main)
  (:gen-class))

(def usage "Usage: app client | app server [port]")

(defn main-client[ ]
  (println "Starting client")
  (println "Here is a simple repl. To enter remote repl type 'replr[& [addr]]', where addr = localhost:3000 by default")
  (binding [*ns* (find-ns 'main)]
    (client.core/repl)))

(defn main-server [ [port] ]
  (println "Starting server")
  (run-jetty service.web/handler {:port (read-string (or port "3000"))
                                  :join? false}))

(defn -main [ & args ]
  (if (empty? args)
    (println usage)
    (let [[dispatch-arg & other] args]
      (case dispatch-arg
        "server" (main-server other)
        "client" (main-client)
        (println usage)))))

