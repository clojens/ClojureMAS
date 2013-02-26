(ns cabinet.web
  (:use compojure.core)
  (:use ring.middleware.json-params)
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
    (print-out "expr = " expr)
    (print-out "type of expr = " (type expr))
    (print-out "read-str expr = " (read-string expr))
    (print-out "type read-str expr = " (type (read-string expr)))
    (print-out "evaled = " (eval (read-string expr)))
    (print-out "type of evaled = " (type (eval (read-string expr))))
    (str "(done " (eval (read-string expr)) ")")
    (catch Exception e
      (print-out "error occured : " e)
      (str "(error \"" e "\")"))
    (finally (print-out "----------------------"))))

(defroutes handler
  (GET "/" []
       "try /repl/expr instead")
  (GET "/repl/:expr" [expr]
       (reply-repl expr)))
  
(def app
  (-> handler
      wrap-json-params))

(comment
(defroutes handler
  (GET "/elems/:id" [id]
       (json-response (elem/get id)))

  (PUT "/elems/:id" [id attrs]
       (json-response (elem/put id attrs)))

  (DELETE "/elems/:id" [id]
          (json-response (elem/delete id)))))
