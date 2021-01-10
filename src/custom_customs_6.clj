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
