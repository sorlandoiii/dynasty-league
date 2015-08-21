(ns dynasty-league.calculations
  (:require [dynasty-league.config :as config]
            [dynasty-league.data :refer :all]
            [dynasty-league.extraction :as extract]
            [dynasty-league.utility :as util]))

;;; Application of draft moves to baseline athletes.

(defn apply-moves-made
  "Uses moves-made to create a current pool of athletes." [athletes]
  (remove #(some (fn [move] (= (.toLowerCase (util/trimmer (:name %))) move)) moves-made) athletes))

(defn ignore-athletes
  "Removes athletes from the overall pool of athletes and does not
  effect moves-made." [athletes]
  (remove #(some (fn [ath] (= (.toLowerCase (util/trimmer (:name %))) ath)) ignored) athletes))

;;; Projected points for athletes

(defn gen-qb-proj-pts
  "Generates projected fantasy points for all QBs based on league settings."
  [qbs]
  (let [stngs (@config/cur-settings :qb-scoring)]
    (for [qb qbs]
      (assoc qb :proj-pts (+ (/ (:pass-yds qb) (:pass-yd stngs))
                             (* (:pass-tds qb) (:pass-td stngs))
                             (/ (:rush-yds qb) (:rush-yd stngs))
                             (* (:rush-tds qb) (:rush-td stngs))
                             (* (:ints qb) (:int stngs))
                             (* (:fumbs qb) (:fumb stngs)))))))

(defn gen-rb-proj-pts
  "Generates projected fantasy points for all RBs based on league settings."
  [rbs]
  (let [stngs (@config/cur-settings :rb-scoring)]
    (for [rb rbs]
      (assoc rb :proj-pts (+ (/ (:rush-yds rb) (:rush-yd stngs))
                             (* (:rush-tds rb) (:rush-td stngs))
                             (* (:recs rb) (:reception stngs))
                             (/ (:rec-yds rb) (:rec-yd stngs))
                             (* (:rec-tds rb) (:rec-td stngs))
                             (* (:fumbs rb) (:fumb stngs)))))))

(defn gen-wr-proj-pts
  "Generates projected fantasy points for all WRs based on league settings."
  [wrs]
  (let [stngs (@config/cur-settings :wr-scoring)]
    (for [wr wrs]
      (assoc wr :proj-pts (+ (/ (:rush-yds wr) (:rush-yd stngs))
                             (* (:rush-tds wr) (:rush-td stngs))
                             (* (:recs wr) (:reception stngs))
                             (/ (:rec-yds wr) (:rec-yd stngs))
                             (* (:rec-tds wr) (:rec-td stngs))
                             (* (:fumbs wr) (:fumb stngs)))))))

(defn gen-te-proj-pts
  "Generates projected fantasy points for all TEs based on league settings."
  [tes]
  (let [stngs (@config/cur-settings :te-scoring)]
    (for [te tes]
      (assoc te :proj-pts (+ (* (:recs te) (:reception stngs))
                             (/ (:rec-yds te) (:rec-yd stngs))
                             (* (:rec-tds te) (:rec-td stngs))
                             (* (:fumbs te) (:fumb stngs)))))))

;; Can get more in depth here if need be.
(defn gen-pk-proj-pts
  "Generates projected fantasy points for all K based on league settings."
  [pks]
  (let [stngs (@config/cur-settings :pk-scoring)]
    (for [pk pks]
      (assoc pk :proj-pts (+ (* (:fgm pk) (:fg-made stngs))
                             (* (:xpm pk) (:xp-made stngs)))))))

;;; VOR calculations

(defn gen-qb-vor
  "Determines how much better a QB is than the baseline QB." [qbs]
  (let [base-qb (:proj-pts (nth qbs (dec ((@config/cur-settings :draft-per-100) :qb))))]
    (map #(assoc % :vor (- (:proj-pts %) base-qb)) qbs)))

(defn gen-rb-vor
  "Determines how much better a RB is than the baseline RB." [rbs]
  (let [base-rb (:proj-pts (nth rbs (dec ((@config/cur-settings :draft-per-100) :rb))))]
    (map #(assoc % :vor (- (:proj-pts %) base-rb)) rbs)))

(defn gen-wr-vor
  "Determines how much better a WR is than the baseline WR." [wrs]
  (let [base-wr (:proj-pts (nth wrs (dec ((@config/cur-settings :draft-per-100) :wr))))]
    (map #(assoc % :vor (- (:proj-pts %) base-wr)) wrs)))

(defn gen-te-vor
  "Determines how much better a TE is than the baseline TE." [tes]
  (let [base-te (:proj-pts (nth tes (dec ((@config/cur-settings :draft-per-100) :te))))]
    (map #(assoc % :vor (- (:proj-pts %) base-te)) tes)))

(defn gen-pk-vor
  "Determines how much better a PK is than the baseline K." [pks]
  (let [base-pk (:proj-pts (nth pks (dec ((@config/cur-settings :draft-per-100) :pk))))]
    (map #(assoc % :vor (- (:proj-pts %) base-pk)) pks)))

;;; Athlete ratings

(defn gen-qb-ratings
  "Generates points and ratings for all QBs." [athletes]
  (as-> (get athletes "QB") $
        (gen-qb-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-qb-vor $)
        (assoc athletes "QB" (vec $))))

(defn gen-rb-ratings
  "Generates points and ratings for all RBs." [athletes]
  (as-> (get athletes "RB") $
        (gen-rb-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-rb-vor $)
        (assoc athletes "RB" (vec $))))

(defn gen-wr-ratings
  "Generates points and ratings for all WRs." [athletes]
  (as-> (get athletes "WR") $
        (gen-wr-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-wr-vor $)
        (assoc athletes "WR" (vec $))))

(defn gen-te-ratings
  "Generates points and ratings for all TEs." [athletes]
  (as-> (get athletes "TE") $
        (gen-te-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-te-vor $)
        (assoc athletes "TE" (vec $))))

(defn gen-k-ratings
  "Generates points and ratings for all Ks." [athletes]
  (as-> (get athletes "K") $
        (gen-pk-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-pk-vor $)
        (assoc athletes "K" (vec $))))

(defn gen-all-ratings
  "Creates all ratings for each position."
  [athletes]
  (->> (gen-qb-ratings athletes)
       (gen-rb-ratings)
       (gen-wr-ratings)
       (gen-te-ratings)
       (gen-k-ratings)
       (vals)
       (flatten)))

;;; Changes and rankings of :vor for all athletes

(defn modify-vor
  "Modifies vor values based on how many of each position is already on my team."
  [athletes]
  (let [stngs (@config/cur-settings :general-settings)
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

;;; Check the value of the pick -- meaning if we can wait to take the player, then wait.

(defn adp-vs-spot
  "Calculates whether or not to take the best athlete available. If he will likely
  still be around for my next pick, I want to wait to take him and instead get the
  second best athlete available." [athletes]
  (let [top-athlete (first athletes)
        moves (count moves-made)]
    (if (pos? (- (:adp top-athlete) moves))
      ;; If positive, then we can possibly wait to draft based on how many moves
      ;; until our next pick.
      (if (<= (- (util/gen-next-draft-spot) moves) (:adp top-athlete))
        ;; We can wait, so look for next best athlete (unless it is last athlete).
        (if (= (count athletes) 1) top-athlete (recur (rest athletes)))
        ;; We can't wait, so return the athlete.
        top-athlete)
      ;; If negative, then this player should have been drafted already. TAKE HIM!!
      top-athlete)))
