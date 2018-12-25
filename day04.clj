(ns day04
  (:require [clojure.string :as str]
            [clojure.core.async :as a]))


(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
    (catch Exception e nil)))


(def rawinput (slurp "day04.txt"))
(def rawsmall (slurp "day04-small.txt"))

(def smallinput (str/split rawsmall #"\n" ))
(def input (str/split rawinput #"\n"))

(def exp #"\[[0-9]+-([0-9]+)-([0-9]+) ([0-9]+):([0-9]+)\] (\S+) (\S+)")

(defn parse-line [line]
  (let [[mm dd hour minute op id]
        (drop 1 (re-find exp line))]
    {:month (parse-int mm)
     :day (parse-int dd)
     :hour (parse-int hour)
     :minute (parse-int minute)
     :op (keyword (.toLowerCase op))
     :id (.substring id 1)}
     
    ))

(def parsed (map parse-line smallinput))
(def sorted (sort-by (juxt :month :day :hour :minute) parsed))




(defn track-guards [{:keys [guard start] :as acc} {:keys [op id minute]}]
  (case op
    :guard (assoc acc :guard id)
    :falls (assoc acc :start minute)
    :wakes (update-in acc [:output guard] (fnil conj []) [minute start])))


(defn sleepiest [guards]
  (->> (for [[id ms] guards]
  [id (->> (map #(apply - %) ms)
           (reduce +)
           )
   ])
     (sort-by (comp - second))
     (ffirst)
     ))


(defn sleepy-minute [minutes]
(->>
 (mapcat (fn [[end start]] (range start end)) minutes)
 (frequencies)
 (sort-by val)
 (last)
  )
  )

(defn part1 [input]
  (let [guards (->> (map parse-line input)
                    (sort-by (juxt :month :day :hour :minute))
                    (reduce track-guards {:output {}})
                    :output)
        guard (sleepiest guards)
        gmin (guards guard)
        ]
    [guard (sleepy-minute gmin)]
  ))

(defn part2 [input]
  (let [guards (->> (map parse-line input)
                    (sort-by (juxt :month :day :hour :minute))
                    (reduce track-guards {:output {}})
                    :output)
        guard (sleepiest guards)
        gmin (guards guard)
        ]
    (->> (for [[id sleeps]guards]
           [id (sleepy-minute sleeps)]
           )
         (sort-by (comp second second))
         (last)
         )))
