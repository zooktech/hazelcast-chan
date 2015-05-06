(ns hazelcast-chan.core-test
  (:require [midje.sweet :refer :all]
            [clojure.core.async :refer [go <! >! thread <!! >!!]]
            [hazelcast-chan.core :refer :all]))

(against-background [(around :contents
                       (let [hc-out (new-hazelcast-instance)
                             hc-in (new-hazelcast-instance)]
                         ?form
                         (.shutdown hc-in)
                         (.shutdown hc-out)
                         ;; Let the Hazelcast logs roll, so the midje results are at the end
                         (Thread/sleep 1000)))]

  (fact "Put value into a Hazelcast queue and take it from a hazelcast-chan"
    (let [in-chan (hazelcast-chan hc-in "mychan")
          out-queue (.getQueue hc-out "mychan")
          result (future (<!! in-chan))]
      (future (.put out-queue 123))
      (deref result 1000 :timeout) => 123))

  (fact "Put value onto a hazelcast-chan and take it from a Hazelcast queue"
    (let [out-chan (hazelcast-chan hc-out "mychan-two")
          in-queue (.getQueue hc-in "mychan-two")
          result (future (.take in-queue))]
      (future (>!! out-chan 123))
      (deref result 1000 :timeout) => 123))

  (fact "Put value into a hazelcast-chan and take it from another"
    (let [in-chan (hazelcast-chan hc-in "mychan-three")
          out-chan (hazelcast-chan hc-out "mychan-three")
          result (future (<!! in-chan))]
      (future (>!! out-chan 445))
      (deref result 1000 :timeout) => 445)))
