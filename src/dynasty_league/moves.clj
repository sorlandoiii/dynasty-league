(ns dynasty-league.moves
  (:require [dynasty-league.core :as core]))

;; List of players taken, ordered by how they were selected in draft.
(def moves-made
  [["JOE FLACCO" "RAVENS"]
   ["PEYTON MANNING" "BRONCOS"]])

(defn apply-moves-made
  "Uses moves-made to create a current pool of players." []
  (remove #(some (fn [move] (and (= (:name %) (first move))
                                 (= (:team %) (second move)))) moves-made) core/all-athletes))
