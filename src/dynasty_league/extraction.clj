(ns dynasty-league.extraction
  (:require [dk.ative.docjure.spreadsheet :as xls]
            [dynasty-league.data :as data]
            [dynasty-league.config :as config]
            [dynasty-league.utility :as util]))

;;; Attach adp to all incoming athletes

(defn add-adp
  "Inserts adp from ppr config table to each athlete, and if no adp then a value
  of 0 is assigned for adp." [athletes]
  (map #(assoc % :adp (if-let [adp (get (@config/cur-settings :adps) (:name %))] adp 300)) athletes))

;;; File type load functions

(defn load-spreadsheet
  "Loads excel spreadsheets using docjure."
  [file codec]
  (->> ["/Users/sorlandoiii/dynasty-league/resources/ingest-files/" file ".xlsx"]
       (clojure.string/join)
       (xls/load-workbook)
       (xls/select-sheet "Sheet1")
       (xls/select-columns codec)
       (drop 4)))

;;; Extractions

(defmulti clean-athlete-data
  "Extract and filter athlete data from excel spreadsheets."
  (fn [file codec] file))

(defmethod clean-athlete-data "QB" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))

(defmethod clean-athlete-data "RB" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:rush-yds :rush-tds :fumbs :rec-yds :rec-tds :recs})))

(defmethod clean-athlete-data "WR" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:rush-yds :rush-tds :fumbs :rec-yds :rec-tds :recs})))

(defmethod clean-athlete-data "TE" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:fumbs :rec-yds :rec-tds :recs})))

(defmethod clean-athlete-data "K" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:fgm :fga :xpm})))

(defmethod clean-athlete-data "DF" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
