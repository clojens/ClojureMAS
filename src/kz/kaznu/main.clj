(ns kz.kaznu.main
  "Entry point of a program"
  (:use kz.kaznu.base.client)
  (:use kz.kaznu.base.utils)
  (:use kz.kaznu.service.server)
  (:use clojure.repl)
  (:gen-class))

(defn main-client[ ]
  (println "Starting client")
  (println "Here is a simple repl. To get more information type: (find-doc \"server-\") and (find-doc \"replr\"")
  (binding [*ns* (find-ns 'kz.kaznu.main)]
    (kz.kaznu.base.client/repl)
    (doall (map server-stop (server-available-servers)))))

(defn -main [ & args ]
  (main-client))

