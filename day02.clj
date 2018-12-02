(ns day02
  (:require [clojure.string :as str]))


(def rawinput (slurp "day02.txt"))
(def input (str/split rawinput #"\n" ))
(def small [
"abcdef"
"bababc"
"abbcde"
"abcccd"
"aabcdd"
"abcdee"
"ababab"])

(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
       (catch Exception e nil)))



(defn part1 [input]
  (let [pp  (->> input
                 (map frequencies)
                 (map vals))
        twos (count (filter true? (map #(hasnumber 2 %) pp)))
        threes (count (filter true? (map #(hasnumber 3 %) pp)))
        ]
    (* twos threes)
    ))

(defn toword [l]
  (map char l))

(defn part2 [input]
  (let [sorted (sort input)
        asInts (map #(map int %) sorted)
        ]
    (reduce
     (fn [current next]
       (let [diff (map - current next)
             wordLength (count next)
             diffLength (count (filter #(= 0 %) diff))
             changes (Math/abs (- diffLength wordLength))
             ]
         
         (if (= changes 1)
           (reduced [(apply str (toword current))
                     (apply str (toword next))])
           next)
         ) 
       ) asInts)
    )
  )



