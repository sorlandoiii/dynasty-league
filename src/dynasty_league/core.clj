(ns dynasty-league.core
  (:require [dynasty-league.calculations :as calc]
            [dynasty-league.config :as config]
            [dynasty-league.extraction :as extract]))

(defn update-cur-settings
  "Updates the cur-settings object with the config data from the
  persistence tier." [config]
  (reset! config/cur-settings config))

(defn dynasty-pick
  "Runs the process e2e and produces the best player at the end." []
  (let [athletes (calc/create-all-athlete-data calc/master-athlete-codec)
        config (update-cur-settings config/dynasty-settings)]
    (as-> (calc/apply-moves-made calc/all-athletes) $
          (calc/ignore-athletes $)
          (calc/modify-vor $)
          (calc/rank-by-vor $)
          (calc/adp-vs-spot $))))

(defn tppr-pick
  "Runs the process e2e and produces the best player at the end." []
  (let [athletes (calc/create-all-athlete-data calc/master-athlete-codec)
        config (update-cur-settings config/twelve-ppr-settings)]
    (as-> (calc/apply-moves-made calc/all-athletes) $
          (calc/ignore-athletes $)
          (calc/modify-vor $)
          (calc/rank-by-vor $)
          (calc/adp-vs-spot $))))

(defn full-process2
  "Runs the process e2e and produces the best player at the end." []
  (let [athletes (calc/create-all-athlete-data calc/master-athlete-codec)]
    (as-> (calc/apply-moves-made calc/all-athletes) $
          (calc/ignore-athletes $)
          (calc/modify-vor $)
          (calc/rank-by-vor $))))
