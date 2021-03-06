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

(defn base-aths
  "Chooses the baseline athlete for each position based on config."
  [athletes]
  (into {} (for [[pos val] (@config/cur-settings :draft-per-100)]
             {pos (nth (reverse (sort-by :proj-pts (get athletes (.toUpperCase (name pos))))) (dec val))})))

;;; League specific functions for picking best player

(defn dynasty-pick
  "Runs the process e2e and produces the top 3 best players based on a dynasty
   format league. Passing a position to the function will return solely that
   position."
  [& pos]
  (let [_ (config/update-cur-settings config/dynasty-settings)
        athletes (create-all-athlete-data config/master-athlete-codec)
        bases (base-aths (group-by :position all-athletes))]
    (as-> (apply-moves-made all-athletes) $
          (ignore-athletes $)
          (group-by :position $)
          (gen-all-ratings bases $)
          (modify-vor $)
          (rank-by-vor $)
          (if (nil? pos) $ (filter #(= (:position %) (first pos)) $))
          (adp-vs-spot $)
          (pprint/pprint $))))

(defn tppr-pick
  "Runs the process e2e and produces the top 3 best players based on a 12 man
   ppr format league. Passing a position to the function will return solely
   that position."
  [& pos]
  (let [_ (config/update-cur-settings config/twelve-ppr-settings)
        athletes (create-all-athlete-data config/master-athlete-codec)
        bases (base-aths (group-by :position all-athletes))]
    (as-> (apply-moves-made all-athletes) $
          (ignore-athletes $)
          (group-by :position $)
          (gen-all-ratings bases $)
          (modify-vor $)
          (rank-by-vor $)
          (if (nil? pos) $ (filter #(= (:position %) (first pos)) $))
          (adp-vs-spot $)
          (pprint/pprint $))))

(defn dynasty-overall
  "Runs the process e2e and produces the top 10 players sorted by points based
   on a dynasty format league. Passing a position to the function will return
   solely that position."
  [& pos]
  (let [_ (config/update-cur-settings config/dynasty-settings)
        athletes (create-all-athlete-data config/master-athlete-codec)
        bases (base-aths (group-by :position all-athletes))]
    (as-> (apply-moves-made all-athletes) $
          (ignore-athletes $)
          (group-by :position $)
          (gen-all-ratings bases $)
          (if (nil? pos) $ (filter #(= (:position %) (first pos)) $))
          (reverse (sort-by :proj-pts $))
          (pprint/pprint (take 10 $)))))
