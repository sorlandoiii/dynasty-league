(ns dynasty-league.moves
  (:require [dynasty-league.core :as core]))

;; List of players taken, ordered by how they were selected in draft.
(def moves-made
  [["JOE FLACCO" "RAVENS"]
   ["PEYTON MANNING" "BRONCOS"]])

(defn apply-moves-made
  "Uses moves-made to create a current pool of players." []
  (let [move moves-made]
    (apply dissoc core/all-athletes move)))
