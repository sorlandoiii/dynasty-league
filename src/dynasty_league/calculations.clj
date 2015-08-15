(ns dynasty-league.calculations
  (:require [dynasty-league.config :as config]
            [dynasty-league.extraction :as extract]))

;;; Extraction of all positions
(declare all-athletes)

(def master-athlete-codec
  {"QB" extract/qb-codec
   "RB" extract/rb-codec
   "WR" extract/wr-codec
   "TE" extract/te-codec
   "PK" extract/pk-codec
   "DF" extract/df-codec})

(defn create-all-athlete-data
  "Extract all athlete files and combine into one datastructure. Takes
  in a map with filename-codec key-value pairs."
  [file-codec]
  (def all-athletes
    (flatten (for [[file codec] file-codec]
               (extract/clean-athlete-data file codec)))))

(defn trimmer
  "This function is called by default for every field. This checks to make sure
  that the value is a string before attempting to trim." [val]
  (if (= String (type val)) (clojure.string/trim val) val))

(def moves-made
  [["JOE FLACCO" "RAVENS"]
   ["PEYTON MANNING" "BRONCOS"]])

(defn apply-moves-made
  "Uses moves-made to create a current pool of players." []
  (remove #(some (fn [move] (and (= (trimmer (:name %)) (first move))
                                 (= (trimmer (:name %)) (second move)))) moves-made) all-athletes))



(defn calculate-best-player
  "Chooses the most optimal player to be drafted from the remaining player.
  Use the remove-players as the argument for this function." []
  (first (sort-by val (into {} (for [ath (apply-moves-made)]
                                 {(:name ath) (+ (:init-rank ath) (:worth ath)
                                                 (if (nil? (get config/dynasty-adps (:name ath))) (+ 30 (:init-rank ath))
                                                     (get config/dynasty-adps (:name ath))))})))))
