(ns advent-of-code-2016.day6
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :refer [split join replace]]))

(defn data
  [file]
  (map str
       (read-string
        (str "[" (slurp (io/resource file)) "]"))))

(defn transpose [data]
  (reduce (fn [[a b c d e f g h] [s1 s2 s3 s4 s5 s6 s7 s8]]
            [(conj a s1)
             (conj b s2)
             (conj c s3)
             (conj d s4)
             (conj e s5)
             (conj f s6)
             (conj g s7)
             (conj h s8)])
          [[] [] [] [] [] [] [] []]
          data))

(defn char-from-line
  [line]
  (->> line
       frequencies
       (sort-by val)
       reverse
       first
       key))


(println
 (apply str
        (map char-from-line (transpose (data "day6-input1")))))

(defn reversed-char-from-line
  [line]
  (->> line
       frequencies
       (sort-by val)
       first
       key))

(println
 (apply str
        (map reversed-char-from-line (transpose (data "day6-input1")))))
