(ns day11
  (:require [clojure.string :as str]))


(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
       (catch Exception e nil)))

(defn calc-id [x y serial ]
  (let [rackid (+ x 10)
        tmp (* (+ (* rackid y) serial) rackid)
        power (-  (rem (quot tmp 100) 10) 5)
        ]
    power
    ))


(defn make-grid [serial]
  (let [grid (vec (take (* 301 301) (repeat 0)))
        vals (for [x (range 301)
                   y (range 301)
                   :let [val (calc-id x y serial)]
                   ]
               [x y val]
               )]
    (reduce (fn [grid [x y val]]
              (assoc grid (+ (* 300 y) x ) val))
            grid vals
            )))

(def square_locs[-301 -300 -299 -1 0 1 299  300 301 ])

(defn calc-power-square [grid [x y]]
  (->> (map (fn [z] (grid (+ (+ (* 300 y) x) z)))
            square_locs)
       (apply +)
       ))

(defn make-power-grid [grid]
  (let [r1 (for [x (range 1 300) y (range 1 300)]
             (calc-power-square grid [y x ]))
        
        ]
    r1)
  )

(def grid (make-grid 6548))
(count grid)

(calc-power-square grid [1 8 ])

(def z (make-power-grid grid))

(def z2(reverse  (sort-by second (map-indexed (fn [idx x] [idx x]) z))))

(map (fn [x] (first x)) z2)
(take 5 z2)
(+ (* 300 233) 250)
(drop (- (count z2) 20) z2)

(+ (* 300 250) 233)

(get z 12)

(drop 75000 z)

(quot 70150 300)

;;(1, 8)
;;121 (232, 249, 12)

