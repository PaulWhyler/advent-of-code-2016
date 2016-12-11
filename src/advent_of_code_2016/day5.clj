(ns advent-of-code-2016.day5
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :refer [split join replace]])
  (:import [java.security MessageDigest]))

(def md5 (MessageDigest/getInstance "MD5"))

(def start "abbhdwsy")

(defn get-n-chars
  [bytes num]
  (let [result
        (->> num
             Integer/toString
             (str start)
             .getBytes
             (.digest md5)
             (take bytes))]
    result))

(defn decode-char
  [[a b c d]]
  (when (every? zero? [a b (bit-and 0xF0 c)])
    (if d
      (do (println [a b c (format "%x" (bit-shift-right (bit-and 0xF0 d) 4))])
          (let [sixth (bit-and 0x0F c)]
            (when (< sixth 8)
              {:pos sixth :char (format "%x" (bit-shift-right (bit-and 0xF0 d) 4))})))
      (format "%x" (bit-and 0x0F c)))))

(pprint
 (apply str
        (take 8
              (filter (complement nil?)
                      (map
                       (comp decode-char #(get-n-chars 3 %))
                       (range))))))
(defn candidates []
 (filter (complement nil?)
         (map
          (comp decode-char #(get-n-chars 4 %))
          (range))))

(defn filter-valid
  [candidates]
  (reduce (fn [{:keys [positions-seen results] :as all} candidate]
            (let [position (:pos candidate)]
              (if (some #{position} positions-seen)
                all
                (let [new-positions-seen (conj positions-seen position)
                      new-results (conj results candidate)]
                  (if (= 8 (count new-positions-seen))
                    (reduced (conj results candidate))
                    {:positions-seen new-positions-seen
                     :results new-results})))))
          {:positions-seen #{}
           :results []}
          candidates))

(println
 (apply str
        (map :char
             (sort-by :pos
                      (filter-valid (candidates))))))
