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
  (defonce all-athletes
    (flatten (for [[file codec] file-codec]
               (extract/clean-athlete-data file codec)))))

(defn trimmer
  "This function is called by default for every field. This checks to make sure
  that the value is a string before attempting to trim." [val]
  (if (= String (type val)) (clojure.string/trim val) val))

;;; Application of draft moves to baseline athletes.
;;; TODO: Automate - my-team and moves-made

(def my-team
  {:qb 0
   :rb 0
   :wr 0
   :te 0
   :flex 0
   :k 0
   :df 0})

;; Format: ["joe flacco" "dez bryant" ....]
(def moves-made
  [])

(def draft-spot 8)

(defn gen-next-draft-spot
  "Determines what number pick will be next for me in the draft based on
  round (r), number of teams in the draft (N), and starting draft spot (n).

  Formula: when r is odd: (r - 1)N + n
           when r is even: rN - n + 1 " []
  (let [teams ((config/twelve-ppr-settings :general-settings) :teams)
        r (int (inc (/ (count moves-made) teams)))] ; inc r to make it the current round.
  (if (even? r)
    (+ (* r teams) draft-spot)
    (+ (- (* (inc r) teams) draft-spot) 1))))

(defn apply-moves-made
  "Uses moves-made to create a current pool of athletes." [athletes]
  (remove #(some (fn [move] (= (.toLowerCase (trimmer (:name %))) move)) moves-made) athletes))

;;; Final rankings of athletes and best player selection.

(defn modify-vor
  "Modifies vor values based on how many of each position is already on my team."
  [athletes]
  (let [stngs (config/twelve-ppr-settings :general-settings)
        ath-by-pos (group-by :position athletes)]
    (flatten (for [[pos num] my-team]
               (if (> (stngs (keyword (str "start-" (name pos)))) (my-team pos))
                 ;; # on my team ok, so return unmodified athletes at the position.
                 (get ath-by-pos (.toUpperCase (name pos)))
                 ;; Over the starting limit, so modify vor and return athletes at positon.
                 (map #(assoc % :vor (* (:vor %) (- 1 (* 0.2 (my-team pos)))))
                      (get ath-by-pos (.toUpperCase (name pos)))))))))

(defn rank-by-vor
  "Ranks all athletes by :vor value." [athletes]
  (reverse (sort-by :vor athletes)))

(defn adp-vs-spot
  "Calculates whether or not to take the best athlete available. If he will likely
  still be around for my next pick, I want to wait to take him and instead get the
  second best athlete available." [athletes]
  (let [top-athlete (first athletes)
        moves (count moves-made)]
    (if (pos? (- (:adp top-athlete) moves))
      ;; If positive, then we can possibly wait to draft based on how many moves
      ;; until our next pick.
      (if (<= (- (gen-next-draft-spot) moves) (:adp top-athlete))
        ;; We can wait, so look for next best athlete (unless it is last athlete).
        (if (= (count athletes) 1) top-athlete (recur (rest athletes)))
        ;; We can't wait, so return the athlete.
        top-athlete)
      ;; If negative, then this player should have been drafted already. TAKE HIM!!
      top-athlete)))
