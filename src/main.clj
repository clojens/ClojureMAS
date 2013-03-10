(ns main
  "Entry point of a program"
  (:use client.core)
  (:use service.server)
  (:use clojure.repl)
  (:gen-class))

(defn main-client[ ]
  (println "Starting client")
  (println "Here is a simple repl. To enter remote repl type 'replr[& [addr]]', where addr = localhost:3000 by default")
  (println "")
  (binding [*ns* (find-ns 'main)]
    (client.core/repl)
    (doall (map server-shutdown (server-available-servers)))))

(defn -main [ & args ]
  (main-client))

