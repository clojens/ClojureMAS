(ns service.server-test
  (:use clojure.test
        base.client
        service.server))

(defn create-eval-stop-testfun [ port expr ]
  (when (not (server-running? port))
    (server-start port))
  (let [r (runr (str "http://localhost:" port) expr)]
    (server-stop port)
    r))

(defn start-stop-testfun [ port ]
  (or (and (server-running? port)
           (do (server-stop port)
               (not (server-running? port))))
      (and (not (server-running? port))
           (do (server-start port)
               (server-running? port)))))

(deftest my-test
  (testing "Simple server test: create, eval, stop"
    (is (= '(:done 2)
           (create-eval-stop-testfun 1337 '(inc 1))))
    (is (= '(:done 2)
           (create-eval-stop-testfun 1338 '(clojure.core/inc 1))))
    (is (= '(:done "asd, qwe")
           (create-eval-stop-testfun 1339 '"asd, qwe")))
    (is (start-stop-testfun 1337))
    (is (and (start-stop-testfun 1347)
             (start-stop-testfun 1347)))
    (is (and (start-stop-testfun 1338)
             (start-stop-testfun 13389)
             (start-stop-testfun 1338)
             (start-stop-testfun 13389))))

    (testing "client testing."
      (is (= '(:done 2)
             (create-eval-stop-testfun 1339 '(clojure.core/inc 1))))
      (is (= '(:done "1.2, 1/2")
             (create-eval-stop-testfun 1339 "1.2, 1/2")))
     (is (= '(:done (:done 4))
            (create-eval-stop-testfun 3000 '(base.client/runr "http://localhost:3000" '(+ 2 2)))))
      (is (= '(:done 13)
             (create-eval-stop-testfun 3000
                   '(let [[done-or-error res & _]
                          (base.client/runr "http://localhost:3000" '(+ 2 2))]
                      (when (= done-or-error :done)
                        (+ res (* 3 3))))))
          "complicated runr-request in runr-request"))
)