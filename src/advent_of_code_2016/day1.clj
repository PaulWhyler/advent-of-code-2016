(ns advent-of-code-2016.day1
  (:require [clojure.java.io :as io]
            ))

(def directions
  (.split (.trim (slurp (io/resource "day1-input1"))) ",\\s?"))

(comment "Down/South is positive, Right/East is positive")

(def moves {:N [ 0 -1]
            :S [ 0  1]
            :E [ 1  0]
            :W [-1  0]})

(def turn
  {:L {:N :W :S :E :E :N :W :S}
   :R {:N :E :S :W :E :S :W :N}})

(def init
  {:dir :N
   :xy [0 0]})

(defn move
  [{:keys [dir xy]} direction]
  (let [next-dir ((turn (keyword (str (first direction)))) (keyword dir))]
    {:dir next-dir
     :xy (map + xy (map #(* (Integer/parseInt (apply str (rest direction))) %) (next-dir moves)))}))

(def final-pos
  (reduce move init directions))

(println (+ (first (:xy final-pos)) (second (:xy final-pos))))
