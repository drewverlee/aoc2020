(ns password-philosophy-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [instaparse.core :as insta]))

;; --- Day 2: Password Philosophy --- For example, suppose you have the
;; following list:

;; 1-3 a: abcde 1-3 b: cdefg 2-9 c: ccccccccc

;; Each line gives the password policy and then the password. The password
;; policy indicates the lowest and highest number of times a given letter must
;; appear for the password to be valid. For example, 1-3 a means that the
;; password must contain a at least 1 time and at most 3 times.

;; In the above example, 2 passwords are valid. The middle password, cdefg, is
;; not; it contains no instances of b, but needs at least 1. The first and third
;; passwords are valid: they contain one a or nine c, both within the limits of
;; their respective policies.

;; How many passwords are valid according to their policies?


;; --- part 1 - solution ---

;; demonstrate grammar
(insta/transform
  {:NUMBER   #(Integer/parseInt %)
   :PASSWORD identity
   ;; to transform our string to a char
   :LETTER   first}
  ((-> "passwords"
     io/resource
     insta/parser)
   "1-2 a: aa"
   ))
;; => (1 2 \a "aa")

;; find valid passwords
(->> "two"
  io/resource
  slurp
  str/split-lines
  (map (fn [file] (insta/transform {:NUMBER   #(Integer/parseInt %)
                                   :PASSWORD identity
                                   :LETTER   first}
                   ((insta/parser (io/resource "passwords")) file))))
  (keep (fn [[low high letter password :as entry ]]
          (when (<= low ((frequencies password) letter 0) high) entry)) )
  count)
;; => 515
