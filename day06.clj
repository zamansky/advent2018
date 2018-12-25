(ns day06
  (:require [clojure.string :as str]
            [clojure.core.async :as a]))


(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
       (catch Exception e nil)))



(def rawinput (slurp "day06.txt"))
(def input (str/split rawinput #"\n"))

(def rawsmallinput (slurp "day06-small.txt"))
(def smallinput (str/split rawsmallinput #"\n"))

(defn input_to_coords [input]
  (map #(map parse-int (str/split % #", ")) input)
  )



(def coords (input_to_coords input))
(def xs (sort (map first coords)))
(def ys (sort (map second coords)))
(def minx (first xs))
(def maxx (last xs))
(def miny (first ys))
(def maxy (last ys))

(defn make-state [input]
  (let [coords (input_to_coords input)
        xs (sort (map first coords))
        ys (sort (map second coords))
        minx (first xs)
        maxx (last xs)
        miny (first ys)
        maxy (last ys)
        ]
    {:coords coords :xs xs :ys ys :minx minx :maxx maxx :miny miny :maxy maxy}))



(defn distance [[x1 y1] [x2 y2]]
  {:coord [x2 y2]
   :dist (+ (Math/abs (- x2 x1))
            (Math/abs (- y2 y1))
            )})



(def board (for [x (range minx (inc maxx))
                 y (range miny (inc maxy))
                 :let [distances (map (partial distance [x y]) coords)
                       mind (apply min-key :dist distances)
                       mincount (count (filter #(= (:dist mind) (:dist %)) distances))]]
             (if (= mincount 1)
               {:coord [x y] :closest (:coord mind) :count mincount}
               ;;{:coord [x y] :closest nil :count mincount}
               )))

;; find the coords on the edges
(def edges (set (map (fn [loc]
                       (let [coord (:coord loc)
                             x (first coord)
                             y (second coord)
                             ]
                         (if (or (= x minx) (= x maxx)
                                 (= y miny) (= y maxy))
                           (:closest loc)
                           ))) board)))

(def candidates (clojure.set/difference  (set coords) edges))


;; 3,4 5,5

;; now count the spaces
(count (filter (fn [x] (= (:closest x) [3 4])) board))
(count (filter (fn [x] (= (:closest x) [5 5])) board))
;; part 1 answer:
(apply max (map (fn [point]
                  (count (filter (fn [x] (= (:closest x) point)) board))) candidates))

;; part 2 begins


(def state (make-state input))

(def board2
  (for [x (range (:minx state) (inc (:maxx state)))
        y (range (:miny state) (inc (:maxy state)))
        :let [dtmp (map (partial distance [x y]) (:coords state))
              distances (map :dist dtmp)
              total (reduce + distances)
              ]]
    (if (< total 10000)
      total)
    
    ))

(def part2-ans (count (filter #(not (nil? %)) board2)))
