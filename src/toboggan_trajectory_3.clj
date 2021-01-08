(ns toboggan-trajectory-3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;; --- Day 3: Toboggan Trajectory ---

(let [grid (->> "three"
             io/resource
             slurp
             str/split-lines
             (map #(str/split % #"")))]
  (loop [c 0
         r 0
         s 0]
    (if-let [row (nth grid r nil)]
      (let [current (nth row (mod s (count row)))]
        (recur
          (if (= "#" current) (inc c) c)
          (inc r)
          (+ s 3)))
      c)))
;; => 164
