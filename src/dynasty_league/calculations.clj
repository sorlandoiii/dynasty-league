(ns dynasty-league.calculations
  (:require [dynasty-league.core :as core]
            [dynasty-league.config :as config]
            [dynasty-league.moves :as moves]))

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
                                 (= (trimmer (:name %)) (second move)))) moves-made) core/all-athletes))

(defn calculate-best-player
  "Chooses the most optimal player to be drafted from the remaining player.
  Use the remove-players as the argument for this function." []
  (first (sort-by val (into {} (for [ath (apply-moves-made)]
                                 {(:name ath) (+ (:init-rank ath) (:worth ath)
                                                 (if (nil? (get config/adps (:name ath))) (+ 10 (:init-rank ath))
                                                     (get config/adps (:name ath))))})))))
