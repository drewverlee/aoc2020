(ns handy-haversacks-7
  (:require [clojure.java.io :as io]
            [instaparse.core :as insta]
            [loom.graph :refer [weighted-digraph]]
            [loom.alg :as alg ]
            [loom.derived :as l.d]
            [clojure.string :as str]
            [clojure.set :as set]))

;; --- Day 7: Handy Haversacks ---

;; --- part 1 --------
(->> "input-7" 
  io/resource
  slurp
  str/split-lines
  (mapcat (fn [l]
            (insta/transform
              {:COLOR    identity
               :S        (fn [c & r] (for [[cbc n] r] [cbc c n]))
               :CBAG     (fn [n c] [c n])
               :BAGCOUNT #(Integer/parseInt %)
               :BAG      (fn [a b] (str/join " " [a b]))}
              ((insta/parser (io/resource "bag")) l))))
  (apply weighted-digraph)
  (#(l.d/subgraph-reachable-from % "shiny gold"))
  l.g/nodes
  count
  dec)
;; => 242

;; The first half of this puzzle is complete! It provides one gold star: *
;; --- Part Two ---

;; It's getting pretty expensive to fly these days - not because of ticket prices, but because of the ridiculous number of bags you need to buy!

;; Consider again your shiny gold bag and the rules from the above example:

;; faded blue bags contain 0 other bags.
;; dotted black bags contain 0 other bags.
;; vibrant plum bags contain 11 other bags: 5 faded blue bags and 6 dotted black bags.
;; dark olive bags contain 7 other bags: 3 faded blue bags and 4 dotted black bags.

;; So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) plus 2 vibrant plum bags (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!

;; Of course, the actual rules have a small chance of going several levels deeper than this example; be sure to count all of the bags, even if the nesting becomes topologically impractical!
