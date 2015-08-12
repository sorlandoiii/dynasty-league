(ns dynasty-league.core)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;; Generated from the extraction of excel files. Vector keys help to avoid
;; cases of players with the same name (on different teams).
(def all-players {["Player Name1" "Team Name1"] {:stat1 "a"
                                                 :stat2 "b"
                                                 :stat3 "c"}
                  ["Player Name3" "Team Name2"] {:stat1 "a"
                                                 :stat2 "b"
                                                 :stat3 "c"}
                  ["Player Name3" "Team Name3"] {:stat1 "a"
                                                 :stat2 "b"
                                                 :stat3 "c"}})

;; List of players taken, ordered by how they were selected in draft.
(def moves-made
  [["Player Name3" "Team Name3"]
   ["Player Name1" "Team Name1"]])

(defn apply-moves-made
  "Uses moves-made to create a current pool of players." []
  (for [move moves-made]
    (dissoc all-players move)))

;; Remove later.
(defn select-random-player
  "Selects random player."
  (rand-nth (keys apply-moves-made)))

(defn calculate-best-player
  "Chooses the most optimal player to be drafted from the remaining player.
  Use the remove-players as the argument for this function." []
  )
