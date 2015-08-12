(ns dynasty-league.core)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def all-players {:a 1 :b 2 :c 3})

(defn remove-players
  "Removes players from the original list of players." [players]
  (apply dissoc all-players players))

(defn calculate-best-player
  "Chooses the most optimal player to be drafted from the remaining player.
  Use the remove-players as the argument for this function."
  [players])
