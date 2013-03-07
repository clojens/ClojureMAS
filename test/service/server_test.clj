(ns service.server-test
  (:use clojure.test
        client.core
        service.server))

(defn create-eval-shutdown-testfun [ port expr ]
  (when (not (server-running? port))
    (server-start port))
  (let [r (runr (str "http://localhost:" port) expr)]
    (server-shutdown port)
    r))

(defn start-shutdown-testfun [ port ]
  (or (and (server-running? port)
           (do (server-shutdown port)
               (not (server-running? port))))
      (and (not (server-running? port))
           (do (server-start port)
               (server-running? port)))))

(deftest my-test
  (testing "Simple server test: create, eval, shutdown"
    (is (= '(:done 2)
           (create-eval-shutdown-testfun 1337 '(inc 1))))
    (is (= '(:done 2)
           (create-eval-shutdown-testfun 1338 '(clojure.core/inc 1))))
    (is (= '(:done "asd, qwe")
           (create-eval-shutdown-testfun 1339 '"asd, qwe")))
    (is (start-shutdown-testfun 1337))
    (is (and (start-shutdown-testfun 1347)
             (start-shutdown-testfun 1347)))
    (is (and (start-shutdown-testfun 1338)
             (start-shutdown-testfun 13389)
             (start-shutdown-testfun 1338)
             (start-shutdown-testfun 13389))))

    (testing "client testing."
      (is (= '(:done 2)
             (create-eval-shutdown-testfun 1339 '(clojure.core/inc 1))))
      (is (= '(:done "1.2, 1/2")
             (create-eval-shutdown-testfun 1339 "1.2, 1/2")))
     (is (= '(:done (:done 4))
            (create-eval-shutdown-testfun 3000 '(client.core/runr "http://localhost:3000" '(+ 2 2)))))
      (is (= '(:done 13)
             (create-eval-shutdown-testfun 3000
                   '(let [[done-or-error res & _]
                          (client.core/runr "http://localhost:3000" '(+ 2 2))]
                      (when (= done-or-error :done)
                        (+ res (* 3 3))))))
          "complicated runr-request in runr-request"))
)