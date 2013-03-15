(ns kz.kaznu.system.core
  (:require clojure.set)
  (:require (kz.kaznu.base [client :as client]))
  (:require (kz.kaznu.service [server :as s]))
  )

;; host must be ip address as a string or "localhost"
(def available-agents (atom #{}))

(defrecord AgentState[ host port ])

(defn agent-state-to-address[ a-state ]
  (str (:host a-state) ":" (:port a-state)))

(defn agent-run
  "creates a new agent which will be listening the specified port"
  [ port ]
  (let [server (s/server-start port)]
    (when server
      (let [an-agent (agent (AgentState. "localhost" port))]
        (swap! available-agents into [an-agent])
        an-agent))))

(defn agent-kill
  "stops an agent by the specified port"
  [ an-agent ]
  (when (= (:host @an-agent) "localhost")
    (s/server-stop (:port @an-agent)))
  (swap! available-agents clojure.set/difference #{an-agent}))

(defn agent-eval[ agent expression ]
  (send-off agent (fn[ state ]
                    (client/runr (agent-state-to-address state)
                                 expression)
                    state)))

