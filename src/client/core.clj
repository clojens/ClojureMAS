(ns client.core
  (:gen-class)
  (:require base.serialization)
  (:require [clj-http.client :as client]))

(defn runr[addr expr]
  (let [expr-str     (clj-http.util/url-encode (base.serialization/seri expr))
        encoded-dots (clojure.string/replace expr-str "." "%2E")
        request-str  (str addr "/repl/" encoded-dots)]
    ;; (print request-str)
    (read-string (:body (client/get request-str)))))

(defn prompt-read[]
  (print ">")
  (flush)
  (read))

(defn replr[& [addr]]
  (println "Read eval print loop(REPL). Type :exit :quit or :q to exit")
  (loop [r (prompt-read)]
    (if (some #{:q :quit :exit} [r])
      "exiting"
      (do (println (runr (or addr "http://localhost:3000") r))
          (recur (prompt-read))))))
