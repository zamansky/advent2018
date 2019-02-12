(ns day10
  (:require [clojure.string :as str]))


(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
       (catch Exception e nil)))

(def rawinput (slurp "day10.txt"))
(def input (str/split rawinput #"\n"))
(def exp #"position=<\s*(\-?\d+),\s*(\-?\d+)>\s*velocity=<\s*(\-?\d+),\s*(\-?\d)>")
(def inputs
  (->> (map (partial re-find exp) input)
       (map (partial drop 1))
       (map #(map parse-int %))))

(defn bounding-box [inputs ](let [maxx (->> (map first inputs) (apply max))
                                  minx (->> (map second inputs) (apply min))
                                  maxy (->> (map first inputs) (apply max))
                                  miny (->> (map second inputs) (apply min))]
                              (* (Math/abs (- maxx minx)) (Math/abs (- maxy miny)))))

(defn transform [inputs i](map (fn [[x y dx dy]]
                                 (let [newx (+ x (* dx i))
                                       newy (+ y (* dy i))]
                                   [newx newy dx dy])) inputs))

(defn findboxes [inputs start stop step]
  (let [boxes (for [ i (range start stop step)
                    :let [transformed_pts (transform inputs i)
                          bb (bounding-box transformed_pts)]]
                [i bb])]
    (sort-by second boxes)))

(defn plot [inputs]
  (let [minx (->> (map first inputs)
                  (apply min))
        miny (->> (map second inputs)
                  (apply min))
        pts (->> (map (fn [[x y _ _]] [ (- x minx) (- y miny)]) inputs)
                 (sort-by second))
        board (vec (for [i (range 80)]
                     (vec (repeat 80 " "))))
        newboard (reduce (fn [board [x y]]
                           (assoc-in board [x y] "*")
                           ) board pts)]
    (reduce (fn [s next]
              (println(apply str next)) )"" newboard)))

;; find the minimum bounding box
;;(findboxes inputs 10000 11000 1)
;; that generates the message
;;(plot (transform inputs 10086))

