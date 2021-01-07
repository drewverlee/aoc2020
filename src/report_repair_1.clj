(ns report-repair-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))



;; part 1
;; find the two entries that sum to 2020 and then multiply those two numbers together.
(let [n (->> "one" io/resource slurp str/split-lines (map #(Integer/parseInt %)))]
  (first
    (set
      (for [a     n
            b     n
            :when (= 2020 (+ b a))]
        (* a b)))))
;; => 145875
