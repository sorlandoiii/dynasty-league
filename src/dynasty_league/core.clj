(ns dynasty-league.core
  (:require [dynasty-league.calculations :as calc]
            [dynasty-league.extraction :as extract]))

(defn full-process
  "Runs the process e2e and produces the best player at the end." []
  (let [athletes (calc/create-all-athlete-data calc/master-athlete-codec)]
    (as-> (calc/apply-moves-made calc/all-athletes) $
          ;; Check my team, see if :vor values need to be changed
          ;; (calc/modify-vor $)
          (calc/rank-by-vor $)
          (calc/adp-vs-pos $))))
