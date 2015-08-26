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

;;; Generate projected points for each position

(defn gen-qb-proj-pts
  "Generates projected fantasy points for all QBs based on league settings."
  [qbs]
  (let [stngs (@config/cur-settings :qb-scoring)]
    (for [qb qbs]
      (assoc qb :proj-pts (+ (/ (:pass-yds qb) (:pass-yd stngs))
                             (* (:pass-tds qb) (:pass-td stngs))
                             (* (:comps qb) (:comp stngs))
                             (* (- (:pass-atts qb) (:comps qb)) (:incomp stngs))
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

;;; File type load functions

(defn load-spreadsheet
  "Loads excel spreadsheets using docjure."
  [file codec]
  (->> [config/ingest-files file ".xls"]
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
        (util/convert-vals $ #{:pass-yds :pass-tds :pass-atts :comps
                               :ints :rush-yds :rush-tds :fumbs})
        (gen-qb-proj-pts $)))

(defmethod clean-athlete-data "RB" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:rush-yds :rush-tds :fumbs :rec-yds :rec-tds :recs})
        (gen-rb-proj-pts $)))

(defmethod clean-athlete-data "WR" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:rush-yds :rush-tds :fumbs :rec-yds :rec-tds :recs})
        (gen-wr-proj-pts $)))

(defmethod clean-athlete-data "TE" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:fumbs :rec-yds :rec-tds :recs})
        (gen-te-proj-pts $)))

(defmethod clean-athlete-data "K" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-adp $)
        (util/convert-vals $ #{:fgm :fga :xpm})
        (gen-pk-proj-pts $)))

(defmethod clean-athlete-data "DF" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
