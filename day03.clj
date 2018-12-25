(ns day03
  (:require [clojure.string :as str]
            [clojure.core.async :as a]))


(defn parse-int [number-string]
  (try (Integer/parseInt number-string)
    (catch Exception e nil)))


(def rawinput (slurp "day03.txt"))

(def rawsmall 
  "#1 @ 1,3: 4x4
  #2 @ 3,1: 4x4
  #3 @ 5,5: 2x2
")


(def line "#3 @ 5,5: 2x2")


(def exp #"#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)")



(def smallinput (str/split rawsmall #"\n" ))
(def input (str/split rawinput #"\n"))


  
(defn make-data [input]
  (reduce (fn [result next]
          (let [ [_,id,col,row,width,height] (re-find exp next)
                ]
            (conj result {:id id
                          :col (parse-int col)
                          :row (parse-int row)
                          :width (parse-int width)
                          :height (parse-int height)})
               )
          ) [] input))

(def data (make-data input))
(def smalldata (make-data smallinput))


(defn expand [col row width height]
  "Loop over rect and return a list of keys in the form :col:row"
  (for [x (range 0 width)
         y (range 0 height)
         :let [k (keyword (format "%d:%d" (+ col x) (+ row y)))]
         ]
         k
  ))


(defn make-all-keys [data]
  "epand all the lines into keys"
  (reduce (fn [result next]
          (let [{:keys [id col row width height]} next]
            (conj result (expand col row width height))
            )) []  data))

(def smallkeys (make-all-keys smalldata))
(def keys (make-all-keys data))

;;; store a 1 in the key first time you see it  a 2 the next time when
;; there's an ovelap
(def d(reduce (fn [result next]
                (if (contains? result next)
                  (assoc result next 2)
                  (assoc result next 1)
                  )
                )
              {} (flatten keys)))

;(count d)

; The number of entries with a 2 are the number of overlap squares
;(count (filter #(= % 2) (vals d)))


; Atom to store ids that have overlap
(def ans (atom #{}))


(defn expand-kv [col row width height id] 
  "Like expand above but also return the id for the key"
  (for [x (range 0 width)
         y (range 0 height)
         :let [k (keyword (format "%d:%d" (+ col x) (+ row y)))]
         ]
         [k id]
  ))


(defn make-all-kv [data]
  "Similar to make-all-keys but stores doubles with Key and ID for eaach patch"
  (reduce (fn [result next]
          (let [{:keys [id col row width height]} next]
            (conj result (expand-kv col row width height (parse-int id)))
            )) []  data))

; convert them to flattened pairs
(def smallkv (partition 2(flatten (make-all-kv smalldata))))
(def datakv (partition 2 (flatten (make-all-kv data))))
;; then put them into a map
;; where you put a -1 in the map after it's seen once
;; the importatnt part is the swap which makes a list of all ids of squares that have an overlap
(reduce (fn [result [k v]] 
          (assoc result k (if (k result)
                            (do
                              (swap! ans conj (k result)) ; this is needed for the first id only
                              (swap! ans conj v)
                            -1)
                            v)))
        {} datakv)

;; this makes a list of ids
(def ids (reduce (fn [result [k v]]
                   (conj result v))
                 #{}
                 datakv))

;; The answer is the difference between our set of ALL ids and our set of the ones with the overlaps
(clojure.set/difference ids @ans)


