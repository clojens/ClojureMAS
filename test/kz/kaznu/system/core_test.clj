(ns kz.kaznu.system.core-test
  (:use clojure.test
        kz.kaznu.service.server
        kz.kaznu.system.core))

(def x (atom 1))

(deftest kz.kaznu.system.core-test-main
  (testing "Testing kz.kaznu.system.core namespace"
    (let [port 3002
          an-agent (agent-run port)]
      (when an-agent
        (is (server-running? port))
        (is (not= (@available-agents an-agent) nil))
        
        (swap! kz.kaznu.system.core-test/x (fn[_]1))
        (agent-eval an-agent '(swap! kz.kaznu.system.core-test/x inc))
        (. Thread (sleep 300))
        (is (= @kz.kaznu.system.core-test/x 2))

        (let [old-cnt (count @available-agents)]
          (agent-kill an-agent)
          (is (not (server-running? port)))
          (is (= old-cnt (inc (count @available-agents)))))))))
          
