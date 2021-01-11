(ns handy-haversacks-7
  (:require [clojure.java.io :as io]
            [instaparse.core :as insta]
            [loom.graph :refer [weighted-digraph]]
            [loom.alg :as alg ]
            [loom.io :refer [view]]
            [clojure.string :as str]))


;; --- Day 7: Handy Haversacks ---

;; --- example --------

;; You have a shiny gold bag. If you wanted to carry it in at least one other
;; bag, how many different bag colors would be valid for the outermost bag? (In
;; other words: how many colors can, eventually, contain at least one shiny gold
;; bag?)

;; In the above rules, the following options would be available to you:

;; A bright white bag, which can hold your shiny gold bag directly. A muted
;; yellow bag, which can hold your shiny gold bag directly, plus some other
;; bags. A dark orange bag, which can hold bright white and muted yellow bags,
;; either of which could then hold your shiny gold bag. A light red bag, which
;; can hold bright white and muted yellow bags, either of which could then hold
;; your shiny gold bag.

;; So, in this example, the number of bag colors that can eventually contain at
;; least one shiny gold bag is 4.

(as-> "input-7" x
  (io/resource x)
  (slurp x)
  (str/split-lines x)
  (mapcat (fn [l]
            (insta/transform
              {:COLOR     identity
               :S         (fn [c & r] (for [[cbc n] r] [cbc c n]))
               :CBAG      (fn [n c] [c n])
               :BAG-COUNT #(Integer/parseInt %)
               :BAG       (fn [a b] (str/join " " [a b]))}
              ((insta/parser (io/resource "bag")) l))) x)
  (apply weighted-digraph x)
  (alg/bf-span x "shiny gold")
  (vals x)
  (map count x)
  (apply + x))
