(ns ws.repl.utils
  )

(defmacro defn-core[name args & body]
  "defines a function and interns in to clojure core namespace"
  `(intern 'clojure.core '~name
           (fn ~args
             ~@body)))

(defn-core plus[& xs]
  (reduce + xs))
