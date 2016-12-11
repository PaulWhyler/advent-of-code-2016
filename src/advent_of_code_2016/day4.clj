(ns advent-of-code-2016.day4
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :refer [split join replace]]))

(def data
  (partition 2
             (read-string
              (str "[" (slurp (io/resource "day4-input1")) "]"))))

(defn parse
  [[room [checksum]]]
  (let [room (-> room
                 name
                 (split #"-"))
        r (join "" (butlast room))
        rc (join "-" (butlast room))
        c (last room)]
    {:room r :cipher rc :sector-id c :checksum (name checksum)}))

(defn alphabet-sort
  [[k1 v1] [k2 v2]]
  (let [c (compare v1 v2)]
    (if (zero? c)
      (compare k2 k1)
      c)))

(defn freqs
  [room]
  (->> room
       (join "")
       frequencies
       (sort-by identity alphabet-sort)
       reverse
       (take 5)
       (map first)
       (apply str)))

(defn valid
  [{room :room checksum :checksum}]
  (= (freqs room) checksum))

(defn all-valid
  [data]
  (filter valid (map parse data)))

(defn sum-ids
  [valid-data]
  (apply + (map (comp #(Integer/parseInt %) :sector-id) valid-data)))


(defn decrypt
  [{ct :cipher sector-id :sector-id}]
  (let [c (mod (Integer/parseInt sector-id) 26)
        s (replace ct "-" " ")]
    (apply str (map (fn [ch]
                      (if (= \space ch)
                        ch
                        (let [m (+ c (int ch))]
                          (if (> m (int \z))
                            (char (- m 26))
                            (char m)))))
                    s))))

(pprint (:sector-id
         (first
          (filter #(= "northpole object storage" (decrypt %)) (map parse data)))))
