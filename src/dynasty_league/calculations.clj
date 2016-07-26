(ns dynasty-league.calculations
  (:require [dynasty-league.config :as config]
            [dynasty-league.data :refer :all]
            [dynasty-league.extraction :as extract]
            [dynasty-league.utility :as util]))

;;; Application of draft moves to all athletes.

(defn apply-moves-made
  "Uses moves-made to create a current pool of athletes." [athletes]
  (remove #(some (fn [move] (= (.toLowerCase (util/trimmer (:name %))) move)) moves-made) athletes))

(defn ignore-athletes
  "Removes athletes from the overall pool of athletes and does not
  effect moves-made." [athletes]
  (remove #(some (fn [ath] (= (.toLowerCase (util/trimmer (:name %))) ath)) ignored) athletes))

;;; VOR calculations

(defn gen-qb-vor
  "Determines how much better a QB is than the baseline QB." [base qbs]
    (map #(assoc % :vor (- (:proj-pts %) (:proj-pts base))) qbs))

(defn gen-rb-vor
  "Determines how much better a RB is than the baseline RB." [base rbs]
    (map #(assoc % :vor (- (:proj-pts %) (:proj-pts base))) rbs))

(defn gen-wr-vor
  "Determines how much better a WR is than the baseline WR." [base wrs]
    (map #(assoc % :vor (- (:proj-pts %) (:proj-pts base))) wrs))

(defn gen-te-vor
  "Determines how much better a TE is than the baseline TE." [base tes]
    (map #(assoc % :vor (- (:proj-pts %) (:proj-pts base))) tes))

(defn gen-pk-vor
  "Determines how much better a PK is than the baseline K." [base pks]
    (map #(assoc % :vor (- (:proj-pts %) (:proj-pts base))) pks))

;;; Athlete ratings

(defn gen-qb-ratings
  "Generates points and ratings for all QBs." [base athletes]
  (as-> (get athletes "QB") $
        (reverse (sort-by :proj-pts $))
        (gen-qb-vor base $)
        (assoc athletes "QB" (vec $))))

(defn gen-rb-ratings
  "Generates points and ratings for all RBs." [base athletes]
  (as-> (get athletes "RB") $
        (reverse (sort-by :proj-pts $))
        (gen-rb-vor base $)
        (assoc athletes "RB" (vec $))))

(defn gen-wr-ratings
  "Generates points and ratings for all WRs." [base athletes]
  (as-> (get athletes "WR") $
        (reverse (sort-by :proj-pts $))
        (gen-wr-vor base $)
        (assoc athletes "WR" (vec $))))

(defn gen-te-ratings
  "Generates points and ratings for all TEs." [base athletes]
  (as-> (get athletes "TE") $
        (reverse (sort-by :proj-pts $))
        (gen-te-vor base $)
        (assoc athletes "TE" (vec $))))

(defn gen-k-ratings
  "Generates points and ratings for all Ks." [base athletes]
  (as-> (get athletes "K") $
        (reverse (sort-by :proj-pts $))
        (gen-pk-vor base $)
        (assoc athletes "K" (vec $))))

(defn gen-all-ratings
  "Creates all ratings for each position."
  [base-ath athletes]
  (->> (gen-qb-ratings (:qb base-ath) athletes)
       (gen-rb-ratings (:rb base-ath))
       (gen-wr-ratings (:wr base-ath))
       (gen-te-ratings (:te base-ath))
       (gen-k-ratings (:k base-ath))
       (vals)
       (flatten)))

;;; Changes the rankings of :vor for all athletes

(defn modify-vor
  "Modifies vor values based on how many of each position is already on my team."
  [athletes]
  (let [stngs (@config/cur-settings :general-settings)
        ath-by-pos (group-by :position athletes)]
    (flatten (for [[pos aths] ath-by-pos]
               (if (> (stngs (keyword (str "start-" (.toLowerCase pos))))
                      (my-team (keyword (.toLowerCase pos))))
                 ;; # on my team ok, so return unmodified athletes at the position.
                 aths
                 ;; Over the starting limit, so modify vor and return athletes at positon.
                 (map #(assoc % :vor (* (:vor %)
                                        (- 1 (* 0.2 (- (my-team (keyword (.toLowerCase pos)))
                                                   (stngs (keyword (str "start-" (.toLowerCase pos))))))))) aths))))))

(defn rank-by-vor
  "Ranks all athletes by :vor value." [athletes]
  (reverse (sort-by :vor athletes)))

;;; Check the value of the pick -- meaning if we can wait to take the player, then wait.

(defn adp-vs-spot
  "Calculates whether or not to take the best athlete available. If he will likely
  still be around for my next pick, I want to wait to take him and instead get the
  second best athlete available." [aths]
  (loop [athletes aths]
    (let [top-athlete (first athletes)
          moves (count moves-made)]
      (if (pos? (- (:adp top-athlete) moves))
        ;; If positive, then we can possibly wait to draft based on how many moves
        ;; until our next pick.
        (if (<= (util/gen-next-draft-spot) (:adp top-athlete))
          ;; We can wait, so look for next best athlete (unless it is last athlete).
          (if (= (count athletes) 1) (take 3 aths) (recur (rest athletes)))
          ;; We can't wait, so return the top 3 athletes.
          (take 3 athletes))
        ;; If negative, then this player should have been drafted already. TAKE HIM!!
        (take 3 athletes)))))
