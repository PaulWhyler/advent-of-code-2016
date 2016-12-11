(ns advent-of-code-2016.day3
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def data
  (partition 3
             (read-string
              (str "[" (slurp (io/resource "day3-input1")) "]"))))

(defn valid-triangle
  [[a b c]]
  (and (< a (+ b c))
       (< b (+ a c))
       (< c (+ a b))))

(pprint
 (count (filter valid-triangle data)))

(defn rearrange-triple
  [[[a-1 a-2 a-3] [b-1 b-2 b-3] [c-1 c-2 c-3]]]
  [[a-1 b-1 c-1][a-2 b-2 c-2][a-3 b-3 c-3]])


(= (rearrange-triple (first (take 1 (partition 3 data))))
   '((785 272 801)(516 511 791)(744 358 693)))

(pprint
 (count
  (filter valid-triangle (mapcat rearrange-triple (partition 3 data)))))
