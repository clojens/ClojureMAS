(ns kz.kaznu.tasks.integral-test
  (:use clojure.test
        kz.kaznu.tasks.integral))

(defn almost-eq[ eps x1 x2 ]
  (< (Math/abs (- x1 x2)) eps))

(deftest integral-tests
  (testing "integral"
    (is (almost-eq 0.0001 1       (integral-rectangles (fn[_]1) 0 1 1000)))
    (is (almost-eq 0.0001 0.45927 (integral-rectangles #(Math/sin %) 0 1 1000)))
    (is (= 1 1) "arithmetics")))
