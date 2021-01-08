(ns toboggan-trajectory-3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;; --- Day 3: Toboggan Trajectory ---
;; ---- part 1

(let [grid (->> "three"
             io/resource
             slurp
             str/split-lines
             (map #(str/split % #"")))]
  (loop [c 0
         r 0
         s 0]
    (if-let [row (nth grid r nil)]
      (let [current (nth row (mod s (count row)))]
        (recur
          (if (= "#" current) (inc c) c)
          (inc r)
          (+ s 3)))
      c)))
;; => 164


;; --- Part Two ---

(let [grid (->> "three"
             io/resource
             slurp
             str/split-lines
             (map #(str/split % #"")))]

  (reduce
    (fn [t {:keys [right down]}]
      (* t (loop [c 0
                  r 0
                  s 0]
             (if-let [row (nth grid r nil)]
               (let [current (nth row (mod s (count row)))]
                 (recur
                   (if (= "#" current) (inc c) c)
                   (+ r down)
                   (+ s right)))
               c))))
    1
    [{:right 1 :down 1}
     {:right 3 :down 1}
     {:right 5 :down 1}
     {:right 7 :down 1}
     {:right 1 :down 2}
     ]))
;; => 5007658656




;; ------------- EXTRA -------------------
;; ------- simplified version --------------

;; I feel it's worth including a simplified version, where i unify the solutions.
;; In terms of solving the problems, this isn't useful. But the process is enlighten and fun.

(def grid  (->> "three"
             io/resource
             slurp
             str/split-lines
             (map #(str/split % #""))))

(defn trees-hit
  [{:keys [grid right down]}]
  (loop [c 0
         r 0
         s 0]
    (if-let [row (nth grid r nil)]
      (let [current (nth row (mod s (count row)))]
        (recur
          (if (= "#" current) (inc c) c)
          (+ r down)
          (+ s right)))
      c)))

;; ------------- part 1 -----------------

(trees-hit {:grid grid :right 3 :down 1})
;; => 164

;; ------------- part 2 -----------------

(reduce
  #(* %1 (trees-hit (assoc %2 :grid grid)))
  1
  [{:right 1 :down 1}
   {:right 3 :down 1}
   {:right 5 :down 1}
   {:right 7 :down 1}
   {:right 1 :down 2}])
;; => 5007658656
