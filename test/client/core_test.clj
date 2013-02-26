(ns client.core-test
  (:use clojure.test
        client.core))

(deftest run-r-test
  (testing "remote run testing."
    (is (= '(:done 2)
           (runr "http://localhost:3000" '(clojure.core/inc 1))))
    (is (= '(:done "1.2, 1/2")
           (runr "http://localhost:3000" "1.2, 1/2")))
    (is (= '(:done (:done 4))
           (runr "http://localhost:3000" '(client.core/runr "http://localhost:3000" '(+ 2 2)))))
    (is (= '(:done 13)
           (runr "http://localhost:3000"
                 '(let [[done-or-error res & _]
                        (client.core/runr "http://localhost:3000" '(+ 2 2))]
                    (when (= done-or-error :done)
                      (+ res (* 3 3))))))
        "complicated runr-request in runr-request")
    (is (= 1 1))))
