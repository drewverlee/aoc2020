(ns handy-haversacks-7
  (:require [clojure.java.io :as io]
            [instaparse.core :as insta]
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


(let [g    (->> "input-7-example"
             io/resource
             slurp
             str/split-lines
             (reduce
               (fn [m l]
                 (merge m
                   (insta/transform
                     {:COLOR     identity
                      :S         (fn [c & r] {c (apply merge r)})
                      :CBAG      (fn [n c] {c n})
                      :BAG-COUNT #(Integer/parseInt %)
                      :BAG       (fn [a b] (str/join " " [a b]))}
                     ((insta/parser (io/resource "bag")) l))))
               {}))
      goal "shiny gold"
      pop  (fn [l] (-> l vec pop))]
  (loop [c        0
         seen?    #{}
         frontier (-> g (dissoc :a) keys)
         has?     #{}
         parent   nil]
    (let [x (-> frontier vec peek)]
      (cond
        (nil? x)   c
        (= goal x) (recur
                     (inc c)
                     (conj seen? x)
                     (pop frontier)
                     (conj has? parent)
                     x)

        (has? x)  (recur
                    (inc c)
                    seen?
                    (pop frontier)
                    has?
                    x)
        (seen? x) (recur
                    c
                    seen?
                    (pop frontier)
                    has?
                    x)
        :else     (recur
                    c
                    (conj seen? x)
                    (concat (pop frontier) (-> x g keys))
                    has?
                    x)))))
