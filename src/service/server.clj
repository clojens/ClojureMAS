(ns service.server
  "This namespace for functions on server: starting closing, starting in new thread etc"
  (:require [ring.adapter.jetty :as jetty])
  (:require service.web)
  (:gen-class))

(let [local-servers (atom {})] ; { port thread }
  (defn start-server [ port ]
    (when (= (@local-servers port) nil)
      (let [server (atom nil)
            thread (Thread.
                    (fn[]
                      (println "Starting server")
                      (swap! server
                             (fn[x]
                               (jetty/run-jetty service.web/handler
                                               {:port port :join? false})))))]
        (swap! local-servers assoc port [server thread])
        (.start thread)
        )))

  (defn shutdown-server [ port ]
    "return nil if no such server found and Exception raised and returns t otherwise"
    (let [ thread (@local-servers port) ]
      (when thread
        (try (do (.interrupt thread)
                 (swap! local-servers dissoc port)
                 true)
             (catch Exception ex      nil)))))

  (defn debug-local-servers[] local-servers))
      