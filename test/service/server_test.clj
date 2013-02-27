(ns service.server-test
  (:use clojure.test
        client.core
        service.server))

(deftest my-test
  (testing "Testing"
    (is (let [port    1337
              test-fn (fn[] (runr ("http://localhost:" port) '(clojure.core/inc 1)))]
          (or
           (= '(:done 2)
              (do (start-server port)
                  (let [r (test-fn)]
                    (shutdown-server port)
                    r)))))
        "")

))