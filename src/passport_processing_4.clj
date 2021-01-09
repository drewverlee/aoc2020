(ns passport-processing-4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [instaparse.core :as i]
            [clojure.set :as set]
            [instaparse.core :as insta]))

;; --- Day 4: Passport Processing ---

;; ----------- example -----------

(->>
  (-> "input-4-example"  io/resource  slurp (str/split #"\n\n"))
  (map #(str/split % #":|\n| "))
  (map #(apply hash-map %))
  (map keys)
  (map set)
  (filter #(set/superset? % #{"hgt" "pid" "byr" "eyr" "iyr" "hcl" "ecl"} ))
  count)
;; => 2

;; ------------- part 1 -----------------------


(->>
  (-> "input-4"  io/resource  slurp (str/split #"\n\n"))
  (map #(str/split % #":|\n| "))
  (map #(apply hash-map %))
  (map keys)
  (map set)
  (filter #(set/superset? % #{"hgt" "pid" "byr" "eyr" "iyr" "hcl" "ecl"} ))

  count)
;; => 226

;; ------------- part 2 -----------------------

(let [->c          (fn [[p t]] (fn [i] (i/transform t ((i/parser p) i))))
      ->year       (->c ["NUMBER = #'[0-9]+'" {:NUMBER #(Integer/parseInt %) }])
      ->hgt        (->c ["<HEIGHT> = NUMBER UNIT
                          NUMBER  = #'[0-9]+'
                          UNIT    = #'[a-zA-Z]+'"
                         {:NUMBER #(Integer/parseInt %)
                          :UNIT   identity}])
      ->hcl        (->c ["<HCL> = '#' A A A A A A
                          <A>   = #'[a-zA-Z0-9]'"])
      ->pid        (->c ["<PID> =  N N N N N N N N N
                          <N>   = #'[0-9]'"])
      unit->parse  {"byr" ->year
                    "iyr" ->year
                    "eyr" ->year
                    "hgt" ->hgt
                    "hcl" ->hcl
                    "pid" ->pid}
      unit->valid? {"byr" #(<= 1920 % 2002)
                    "iyr" #(<= 2010 % 2020)
                    "eyr" #(<= 2010 % 2030)
                    "hgt" (fn [[height unit]]
                            (case unit
                              "cm" (<= 150 height  193)
                              "in" (<= 59 height 76)
                              false))
                    "ecl" #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}}]
  (->>
    (-> "input-4"  io/resource  slurp (str/split #"\n\n"))
    (map #(str/split % #":|\n| "))
    (map #(apply hash-map %))
    ;; only keep parsed key-value pairs
    (map #(reduce-kv
            (fn [nm unit v]
              (let [parsed ((get unit->parse unit identity) v) ]
                (if-not (insta/failure? parsed)
                  (assoc nm unit parsed)
                  nm))
              )
            {}
            %))
    ;; remove invalid values
    (map #(reduce-kv
            (fn [nm unit v]
              (if ((get unit->valid? unit identity) (% unit))
                (assoc nm unit v)
                nm))
            {}
            %))

    ;;at which point we can just check our remaining valid keys are there.
    (map keys)
    (map set)
    (filter #(set/superset? % #{"hgt" "pid" "byr" "eyr" "iyr" "hcl" "ecl"} ))
    count
    ))
;; => 160
