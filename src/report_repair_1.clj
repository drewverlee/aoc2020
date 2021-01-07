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

;; --- Part Two ---

;; find three numbers in your expense report that meet the same criteria.

;; Using the above example again, the three entries that sum to 2020 are 979, 366, and 675. Multiplying them together produces the answer, 241861950.

;; In your expense report, what is the product of the three entries that sum to 2020?

(let [n (->> "one" io/resource slurp str/split-lines (map #(Integer/parseInt %)))]
  (first
    (set
      (for [a     n
            b     n
            c     n
            :when (= 2020 (+ b a c))]
        (* a b c)))))
;; => 69596112
