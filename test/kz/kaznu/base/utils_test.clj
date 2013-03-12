(ns kz.kaznu.base.utils-test
  (:use clojure.test
        kz.kaznu.base.utils))

(deftest baseutils
  (testing "base.utils"
    (is (get-ips))))
