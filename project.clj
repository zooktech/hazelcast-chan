(defproject hazelcast-chan "0.1.0-SNAPSHOT"

  :description "core.async channel which puts onto and takes from a Hazelcast distributed queue."

  :url "https://github.com/zooktech/hazelcast-chan"

  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/tools.logging "0.3.1"]
                 [com.hazelcast/hazelcast "3.4"]]

  :profiles {:dev {:dependencies [[midje "1.6.3"]]}})
