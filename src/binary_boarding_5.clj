(ns binary-boarding-5
  (:require [clojure.math.numeric-tower :refer [ceil floor]]
            [clojure.string :as str]
            [clojure.java.io :as io]))

;; --- Day 5: Binary Boarding ---

(->> "input-5"
  io/resource
  slurp
  str/split-lines
  (map #(str/split % #""))
  (map (fn [i]
         (loop [[rs re] [0 127]
                [cs ce] [0 7]
                d       i]
           (if (empty? d)
             {:row rs :column cs :seat-ID (+ cs (* rs 8)) :input i}
             (let [f #(+ %1 (int (%3 (/ (- %2 %1) 2))))]
               (recur
                 (case (first d)
                   "F" [rs (f rs re floor)]
                   "B" [(f rs re ceil) re]
                   [rs re])
                 (case (first d)
                   "L" [cs (f cs ce floor)]
                   "R" [(f cs ce ceil) ce]
                   [cs ce])
                 (rest d)))))))
  (sort-by :seat-ID)
  last
  :seat-ID)
;; => 878

;; --- Part Two ---

(->> "input-5"
  io/resource
  slurp
  str/split-lines
  (map #(str/split % #""))
  (map (fn [i]
         (loop [[rs re] [0 127]
                [cs ce] [0 7]
                d       i]
           (if (empty? d)
             (+ cs (* rs 8))
             (let [f #(+ %1 (int (%3 (/ (- %2 %1) 2))))]
               (recur
                 (case (first d)
                   "F" [rs (f rs re floor)]
                   "B" [(f rs re ceil) re]
                   [rs re])
                 (case (first d)
                   "L" [cs (f cs ce floor)]
                   "R" [(f cs ce ceil) ce]
                   [cs ce])
                 (rest d)))))))
  sort
  (reduce  #(if (= (inc %1) %2) %2 (reduced (inc %1)))))
;; => 504
