(ns dynasty-league.core
  (:require [dynasty-league.calculations :as calc]
            [dynasty-league.extraction :as extract]))

(defn full-process []
  "Runs the process e2e and produces the best player at the end."
  (do
   (calc/create-all-athlete-data calc/master-athlete-codec)
   (calc/calculate-best-player)))
