(ns service.web
  (:use compojure.core)
  (:use ring.middleware.json-params)
  (:require client.core)
  (:require [clj-json.core :as json]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn print-out[& xs]
  (doall (map #(.print System/out %) xs))
  (.println System/out ""))

(defn reply-repl[expr]
  (try
    (print-out (str (java.util.Date.)))
    (print-out "expr          = " expr)
    (let [r (eval (read-string expr))]
      (print-out "evaled value  = " r)
      (print-out "evaled type   = " (type r))
      (str "(:done " r ")"))
    (catch Exception e
      (print-out "error occured = " e)
      (str "(:error \"" e "\")"))
    (finally (print-out "----------------------"))))

(defroutes handler
  (GET "/" []
       "(:error \"try /repl/expr instead\")")
  (GET "/repl/:expr" [expr]
       (reply-repl expr)))
  
(def app
  (-> handler
      wrap-json-params))
