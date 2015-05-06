(ns hazelcast-chan.core
  (:require [clojure.core.async :refer [chan <! >! <!! >!! thread go close!]]
            [clojure.tools.logging :as log])
  (:import (com.hazelcast.core Hazelcast HazelcastInstanceNotActiveException)))


(defn new-hazelcast-instance
  []
  (Hazelcast/newHazelcastInstance))

(defn- handle-exception [msg c]
  (do
    (log/info msg)
    (close! c)
    nil))

(defn hazelcast-chan
  [hazelcast-instance name]
  (let [c (chan)
        hazelcast-queue (.getQueue hazelcast-instance name)]
    (thread
      (try
        (loop []
          (let [v (.take hazelcast-queue)]
            (>!! c v))
          (recur))
        (catch InterruptedException e
          (handle-exception
            (str "Thread interrupted waiting to take from Hazelcast queue '"
              name "'. Closing channel.") c))
        (catch HazelcastInstanceNotActiveException e
          (handle-exception
            (str "HazelcastInstanceNotActiveException while waiting to take from queue '"
              name "'. Closing channel.") c))))
    (thread
      (try
        (loop []
          (let [v (<!! c)]
            (.put hazelcast-queue v))
          (recur))
        (catch InterruptedException e
          (handle-exception (str "Thread interrupted putting onto Hazelcast queue '"
                              name "'. Closing channel.") c))
        (catch HazelcastInstanceNotActiveException e
          (handle-exception (str "HazelcastInstanceNotActiveException while putting onto queue '"
                              name "'. Closing channel.") c))))
    c))