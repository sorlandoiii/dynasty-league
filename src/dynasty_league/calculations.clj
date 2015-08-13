(ns dynasty-league.calculations
  (:require [dynasty-league.core :as core]
            [dynasty-league.moves :as moves]))

(def current-athletes (moves/apply-moves-made))

(defn calculate-best-player
  "Chooses the most optimal player to be drafted from the remaining player.
  Use the remove-players as the argument for this function." []
  )
