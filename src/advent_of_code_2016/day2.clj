(ns advent-of-code-2016.day2
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def data
  (map str (read-string
            (str "[" (slurp (io/resource "day2-input1")) "]"))))

(def keypad
  {:1 {\R :2 \L :1 \U :1 \D :4}
   :2 {\R :3 \L :1 \U :2 \D :5}
   :3 {\R :3 \L :2 \U :3 \D :6}
   :4 {\R :5 \L :4 \U :1 \D :7}
   :5 {\R :6 \L :4 \U :2 \D :8}
   :6 {\R :6 \L :5 \U :3 \D :9}
   :7 {\R :8 \L :7 \U :4 \D :7}
   :8 {\R :9 \L :7 \U :5 \D :8}
   :9 {\R :9 \L :8 \U :6 \D :9}})

(def keypad2
  {:1 {\R :1 \L :1 \U :1 \D :3}
   :2 {\R :3 \L :2 \U :2 \D :6}
   :3 {\R :4 \L :2 \U :1 \D :7}
   :4 {\R :4 \L :3 \U :4 \D :8}
   :5 {\R :6 \L :5 \U :5 \D :5}
   :6 {\R :7 \L :5 \U :2 \D :A}
   :7 {\R :8 \L :6 \U :3 \D :B}
   :8 {\R :9 \L :7 \U :4 \D :C}
   :9 {\R :9 \L :8 \U :9 \D :9}
   :A {\R :B \L :A \U :6 \D :A}
   :B {\R :C \L :A \U :7 \D :D}
   :C {\R :C \L :B \U :8 \D :C}
   :D {\R :D \L :D \U :B \D :D}})

(defn make-moves
  [keypad]
  (fn [start directions]
    (reduce (fn [k d]
              ((k keypad) d))
            start
            directions)))

(defn run
  [keypad start data]
  (apply str
         (map name
              (rest
               (reductions (make-moves keypad) start data)))))

(pprint (run keypad :5 data))

(pprint (run keypad2 :5 data))
