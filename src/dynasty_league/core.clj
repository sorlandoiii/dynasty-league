(ns dynasty-league.core
  (:require [dynasty-league.calculations :refer :all]
            [dynasty-league.config :as config]
            [dynasty-league.extraction :as extract]
            [clojure.pprint :as pprint]))

;;; Create pool of athletes

(defn create-all-athlete-data
  "Extract all athlete files and combine into one datastructure. Takes
  in a map with filename-codec key-value pairs."
  [file-codec]
  (def all-athletes
    (flatten (for [[file codec] file-codec]
               (extract/clean-athlete-data file codec)))))

;;; League specific functions for picking best player

(defn dynasty-pick
  "Runs the process e2e and produces the best player at the end." [& pos]
  (let [athletes (create-all-athlete-data config/master-athlete-codec)
        _ (config/update-cur-settings config/dynasty-settings)]
    (as-> (apply-moves-made all-athletes) $
          (ignore-athletes $)
          (group-by :position $)
          (gen-all-ratings $)
          (modify-vor $)
          (rank-by-vor $)
          (if (nil? pos) $ (filter #(= (:position %) (first pos)) $))
          (adp-vs-spot $)
          (pprint/pprint $))))

(defn tppr-pick
  "Runs the process e2e and produces the best player at the end." [& pos]
  (let [athletes (create-all-athlete-data config/master-athlete-codec)
        _ (config/update-cur-settings config/twelve-ppr-settings)]
    (as-> (apply-moves-made all-athletes) $
          (ignore-athletes $)
          (group-by :position $)
          (gen-all-ratings $)
          (modify-vor $)
          (rank-by-vor $)
          (if (nil? pos) $ (filter #(= (:position %) (first pos)) $))
          (adp-vs-spot $)
          (pprint/pprint $))))
