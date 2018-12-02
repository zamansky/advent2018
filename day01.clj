(ns day01
  (:require [clojure.string :as str]))


(def rawinput (slurp "day01.txt"))
(def input (str/split rawinput #"\n" ))
(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
    (catch Exception e nil)))



; part 1
(def part1 (->>
 input
 (map parse-int)
 (reduce +)))


;; part 2

(def nums (map parse-int input))
(def n '(-6 3 8 5 -6))

(defn part2 [nums]
  (loop [ [car & cdr ] (flatten (repeat nums))
         freq 0
         s #{}
         ]
    (if (contains? s freq)
      freq
      (recur cdr (+ freq car) (conj s freq )))
    ))




(defn part2-reduce [nums]
  (reduce (fn [d num]
          (cond
            (empty? d) (assoc d :set #{num} :sum num)
            :else (let [newfreq (+ num (:sum d))
                        s (:set d)
                        sum (:sum d)
                        ]
                    
                    (if (contains? s newfreq)
                      (reduced newfreq)
                      (assoc d :set (conj s newfreq) :sum  newfreq)
                      )
                    )
            )
            )
            {} (cycle nums)
          ))

