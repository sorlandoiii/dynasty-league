(ns dynasty-league.calculations
  (:require [dynasty-league.config :as config]
            [dynasty-league.extraction :as extract]))

;;; Creation of all baseline athlete data.

(declare all-athletes)

(def master-athlete-codec
  {"QB" extract/qb-codec
   "RB" extract/rb-codec
   "WR" extract/wr-codec
   "TE" extract/te-codec
   "K" extract/pk-codec})
   ;; "DF" extract/df-codec})

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

;;; Application of draft moves to baseline athletes.
;;; TODO: Automate - my-team, moves-made, picks-til-my-next-pick

;; Format: {:position ["fname lname" "fname lname" ...]}
(def my-team
  {:qb []
   :rb []
   :wr []
   :te []
   :k []
   :def []})

;; Format: ["Joe Flacco" "BAL"]
(def moves-made
  [])

(def draft-spot 1)

(def picks-til-my-next-pick 1)

;; (defn next-avail-pick
;;   "Determines how many draft picks until your next available draft pick. This
;;   is currently based off a 12 man draft." []
;;   (case draft-spot
;;     1 (if (< (count moves-made) draft-spot) draft-spot
;;     2
;;     3
;;     4
;;     5
;;     6
;;     7
;;     8
;;     9
;;     10
;;     11
;;     12))

(defn apply-moves-made
  "Uses moves-made to create a current pool of athletes." [athletes]
  (remove #(some (fn [move] (and (= (trimmer (:name %)) (first move))
                                 (= (trimmer (:team %)) (second move)))) moves-made) athletes))

;;; Final rankings of athletes and best player selection.

(defn modify-vor
  "Modifies vor values based on how many of each position is already on my team."
  [athletes]
  (let [stngs (config/twelve-ppr-settings :general-settings)]
  ;; If # of position already on my team is less than how many I need to start,
  ;; values stay the same. Else, values drop 20% per # of position on my team.
    ))

(defn rank-by-vor
  "Ranks all athletes by :vor value." [athletes]
  (reverse (sort-by :vor athletes)))

(defn adp-vs-pos
  "" [athletes]
    (if (pos? (- (:adp (first athletes)) (count moves-made)))
      ;; If positive, then we can possibly wait to draft based on how many moves
      ;; until our next pick. NOTE: next-avail-pick is how long until your FOLLOWING pick.
      (if (<= (+ (count moves-made) picks-til-my-next-pick) (:adp (first athletes)))
        ;; We can wait, so look for next best athlete (unless it is last athlete).
        (if (= (count athletes) 1) (first athletes) (recur (rest athletes)))
        ;; We can't wait, so return the athlete.
        (first athletes))
      ;; If negative, then this player should have been drafted already. TAKE HIM!!
      (first athletes)))
