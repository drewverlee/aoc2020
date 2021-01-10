(ns custom-customs-6
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

;; --- Day 6: Custom Customs ---

;; ---- part 1 --------

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

;; ---------- part 2 ----------------------

(->> "input-6"
  io/resource
  slurp
  str/split-lines
  (partition-by #(str/blank? %))
  (remove #(= [""] %))
  (map #(hash-map :a (str/join "" %) :g (count %)))
  (map #(assoc % :d (-> % :a frequencies)))
  (map (fn [{:keys [d g] :as m}] (assoc m :yes (->> g ((group-by val d)) count))))
  (map :yes)
  (apply +))
;; => 3117
