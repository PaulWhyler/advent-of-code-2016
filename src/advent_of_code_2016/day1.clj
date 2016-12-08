(ns advent-of-code-2016.day1
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def directions
  (.split (.trim (slurp (io/resource "day1-input1"))) ",\\s?"))

(defn expand-directions
  [dirs]
  (reduce (fn [a [d & n]]
            (let [d (keyword (str d))
                  n (Integer/parseInt (apply str n))]
              (apply conj a d (repeat (dec n) :F))))
          []
          dirs))

(comment "Down/South is positive, Right/East is positive")

(def moves {:N [ 0 -1]
            :S [ 0  1]
            :E [ 1  0]
            :W [-1  0]})

(def turn
  {:L {:N :W :S :E :E :N :W :S}
   :R {:N :E :S :W :E :S :W :N}
   :F {:N :N :S :S :E :E :W :W}})

(def init
  {:dir :N
   :xy [0 0]})

(defn move
  [{:keys [dir xy]} direction]
  (let [next-dir ((turn direction) dir)]
    {:dir next-dir
     :xy (map + xy (next-dir moves))}))

(def positions
  (reductions move init (expand-directions directions)))

(defn distance [pos]
  (reduce + (map #(Math/abs %) pos)))

(println  (distance (:xy (last positions))))

(defn first-duplicate-position
  [positions]
  (reduce (fn [acc i]
            (if (some #{(:xy i)} acc)
              (reduced (:xy i))
              (conj acc (:xy i))))
          #{}
          positions))

(println (distance (first-duplicate-position positions)))
