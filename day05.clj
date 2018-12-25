(ns day05
  (:require [clojure.string :as str]
            [clojure.core.async :as a]))


(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
    (catch Exception e nil)))


(def input (slurp "day05.txt"))

; (def input (str/split rawinput #"\n"))
(def smallinput "dabAcCaCBAcCcaDA")


(defn isOpposite [a b]
  (and (and (not (nil? a)) (not (nil? b)))
       (not (= a b))
       (= (clojure.string/upper-case a) (clojure.string/upper-case b)))) 

(defn onepass [s]
(reduce (fn [result next]
          (let [prev (last result)]
            (if (isOpposite next prev)
              (subvec result 0 (dec (count result)))
              ;;(drop-last result)
              (conj result next)
              )
            ))
        [] s
        ))

(count (onepass (drop-last input)))



(defn removePolymer [l s]
  (let [u (first (clojure.string/upper-case l))
        l (first (clojure.string/lower-case l))
        f (fn [x] (or (= x l) (= x u)))
        ]
    (remove f s)))

(defn char-range [start end]
  (map char (range (int start) (inc (int end)))))

(defn part2 [input]
  (for [c (char-range \a \z)
        :let [s (removePolymer c input)
              size (count (onepass s))
              ]]
     size)
  )

(def sizes (part2 (drop-last input)))
(def part2 (first (sort sizes)))
