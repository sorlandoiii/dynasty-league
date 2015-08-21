(ns dynasty-league.extraction
  (:require [dk.ative.docjure.spreadsheet :as xls]
            [dynasty-league.config :as config]))

(defn load-spreadsheet
  "Loads excel spreadsheets using docjure."
  [file codec]
  (->> ["/Users/sorlandoiii/dynasty-league/resources/ingest-files/" file ".xlsx"]
       (clojure.string/join)
       (xls/load-workbook)
       (xls/select-sheet "Sheet1")
       (xls/select-columns codec)
       (drop 4)))

(defn str->double
  "Transforms a string to a double, parsing first." [val]
  (Double/parseDouble (clojure.string/replace val #"," "")))

(defn convert-vals
  "Translates strings to doubles for each field (in fields) of the collection."
  [coll fields]
  (for [m coll] (into {} (for [[k v] m]
                           (if (contains? fields k)
                             {k (str->double v)}
                             {k v})))))

(defn add-ppr-adp
  "Inserts adp from ppr config table to each athlete, and if no adp then a value
  of 0 is assigned for adp." [athletes]
  (map #(assoc % :adp (if-let [adp (get config/tppr-adps (:name %))] adp 300)) athletes))

(defmulti clean-athlete-data
  "Extract and filter athlete data from excel spreadsheets."
  (fn [file codec] file))

;;; QBs

(def qb-codec
  {:A :name :B :team :E :pass-yds :F :pass-tds :G :ints
   :I :rush-yds :J :rush-tds :K :fumbs})

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

(defn gen-ppr-qb-vor
  "Determines how much better a QB is than the baseline QB." [qbs]
  (let [base-qb (:proj-pts (nth qbs (dec (config/tppr-draft-per-100 :qb))))]
    (map #(assoc % :vor (- (:proj-pts %) base-qb)) qbs)))

(defmethod clean-athlete-data "QB" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-ppr-adp $)
        (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})
        (gen-qb-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-ppr-qb-vor $)))

;;; RBs

(def rb-codec
  {:A :name :B :team :D :rush-yds :E :rush-tds :F :recs :G :rec-yds
   :H :rec-tds :I :fumbs})

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

(defn gen-ppr-rb-vor
  "Determines how much better a RB is than the baseline RB." [rbs]
  (let [base-rb (:proj-pts (nth rbs (dec (config/tppr-draft-per-100 :rb))))]
    (map #(assoc % :vor (- (:proj-pts %) base-rb)) rbs)))

(defmethod clean-athlete-data "RB" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-ppr-adp $)
        (convert-vals $ #{:rush-yds :rush-tds :fumbs :rec-yds :rec-tds :recs})
        (gen-rb-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-ppr-rb-vor $)))

;;; WRs

(def wr-codec
  {:A :name :B :team :D :rush-yds :E :rush-tds :F :recs :G :rec-yds
   :H :rec-tds :I :fumbs})

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

(defn gen-ppr-wr-vor
  "Determines how much better a WR is than the baseline WR." [wrs]
  (let [base-wr (:proj-pts (nth wrs (dec (config/tppr-draft-per-100 :wr))))]
    (map #(assoc % :vor (- (:proj-pts %) base-wr)) wrs)))

(defmethod clean-athlete-data "WR" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-ppr-adp $)
        (convert-vals $ #{:rush-yds :rush-tds :fumbs :rec-yds :rec-tds :recs})
        (gen-wr-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-ppr-wr-vor $)))

;;; TEs

(def te-codec
  {:A :name :B :team :C :recs :D :rec-yds :E :rec-tds :F :fumbs})

(defn gen-te-proj-pts
  "Generates projected fantasy points for all TEs based on league settings."
  [tes]
  (let [stngs (@config/cur-settings :te-scoring)]
    (for [te tes]
      (assoc te :proj-pts (+ (* (:recs te) (:reception stngs))
                             (/ (:rec-yds te) (:rec-yd stngs))
                             (* (:rec-tds te) (:rec-td stngs))
                             (* (:fumbs te) (:fumb stngs)))))))

(defn gen-ppr-te-vor
  "Determines how much better a TE is than the baseline TE." [tes]
  (let [base-te (:proj-pts (nth tes (dec (config/tppr-draft-per-100 :te))))]
    (map #(assoc % :vor (- (:proj-pts %) base-te)) tes)))

(defmethod clean-athlete-data "TE" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-ppr-adp $)
        (convert-vals $ #{:fumbs :rec-yds :rec-tds :recs})
        (gen-te-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-ppr-te-vor $)))

;;; PKs

(def pk-codec
  {:A :name :B :team :C :fgm :D :fga :E :xpm})

;; Can get more in depth here if need be.
(defn gen-pk-proj-pts
  "Generates projected fantasy points for all PK based on league settings."
  [pks]
  (let [stngs (@config/cur-settings :pk-scoring)]
    (for [pk pks]
      (assoc pk :proj-pts (+ (* (:fgm pk) (:fg-made stngs))
                             (* (:xpm pk) (:xp-made stngs)))))))

(defn gen-ppr-pk-vor
  "Determines how much better a PK is than the baseline PK." [pks]
  (let [base-pk (:proj-pts (nth pks (dec (config/tppr-draft-per-100 :pk))))]
    (map #(assoc % :vor (- (:proj-pts %) base-pk)) pks)))

(defmethod clean-athlete-data "K" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)
        (add-ppr-adp $)
        (convert-vals $ #{:fgm :fga :xpm})
        (gen-pk-proj-pts $)
        (reverse (sort-by :proj-pts $))
        (gen-ppr-pk-vor $)))

;;; PNs

;;; DEFs

(def df-codec
  {:A :init-rank :B :team :C :bye-week :D :sacks :E :fumb-recs :F :ints
   :G :def-td :H :pts-allowed :I :payds-game :J :ruyds-game :K :safeties
   :L :kr-td :M :proj-pts})

(defmethod clean-athlete-data "DF" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
        ;; (gen-qb-proj-pts $)))
