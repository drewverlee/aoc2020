(ns custom-customs-6
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

;; --- Day 6: Custom Customs ---

(->> "input-6"
  io/resource
  slurp
  str/split-lines
  (partition-by #(str/blank? %))
  (map #(str/join "" %))
  (map distinct)
  (map count)
  (apply +))
;; => 6680

;; --------- part 2 ------------

(->> "input-6"
  io/resource
  slurp
  str/split-lines
  (partition-by #(str/blank? %))
  (remove #(= [""] %))
  (reduce
    (fn [c n]
      (+ c (->> n
             count
             (get (group-by val (frequencies (str/join "" n ))))
             count) ))
    0))
;; => 3117
