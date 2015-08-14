(ns dynasty-league.moves
  (:require [dynasty-league.core :as core]))

(defn trimmer
  "This function is called by default for every field. This checks to make sure
  that the value is a string before attempting to trim." [val]
  (if (= String (type val)) (clojure.string/trim val) val))

;; List of players taken, ordered by how they were selected in draft.
(def moves-made
  [["JOE FLACCO" "RAVENS"]
   ["PEYTON MANNING" "BRONCOS"]])

(defn apply-moves-made
  "Uses moves-made to create a current pool of players." []
  (remove #(some (fn [move] (and (= (trimmer (:name %)) (first move))
                                 (= (trimmer (:team %)) (second move)))) moves-made) core/all-athletes))
