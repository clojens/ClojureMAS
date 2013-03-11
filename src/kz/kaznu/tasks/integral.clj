(ns kz.kaznu.tasks.integral
  (:gen-class))

(defn fun ^double []
  1.0)

;; pmap

(defn integral-rectangles 
  "calculates integral using rectangle method. FIXME reducing must be in parallel too"
  ^double
  [ f #^double start-x #^double end-x #^long num-points ]
  (let [step       (/ (- end-x start-x) (double num-points))
        rect-area  (fn[x] (* step (f x))) ]
    (reduce + 0 (pmap rect-area (range start-x end-x step)))))
