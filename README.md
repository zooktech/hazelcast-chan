# hazelcast-chan

A Clojure library for creating core.async channels which put onto and take from Hazelcast distributed queues.


## Usage

In one REPL do...
```
(require '[hazelcast-chan.core :refer [new-hazelcast-instance hazelcast-chan]])
(require '[clojure.core.async :refer [<!!]])
(def hc (new-hazelcast-instance))
;; Lots of logs as Hazelcast starts a node.
(def chan-a (hazelcast-chan hc "spinal"))
(loop []
  (println (<!! chan-a) " Rock & Roll!)
  (recur))
```
Then in another...
```
(require '[hazelcast-chan.core :refer [new-hazelcast-instance hazelcast-chan]])
(require '[clojure.core.async :refer [>!!]])
(def hc (new-hazelcast-instance))
;; Lots of logs as Hazelcast joins the cluster.
(def chan-b (hazelcast-chan hc "spinal"))
(>!! chan-b "Hello Cleveland!")
```

## Copyright & License

The MIT License (MIT)

Copyright © 2015 Zook Technology Ltd.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
