(ns dynasty-league.extraction
  (:require [dk.ative.docjure.spreadsheet :as xls]
            [dynasty-league.config :as config]))

(defn load-spreadsheet
  "Loads excel spreadsheets using docjure."
  [file codec]
  (->> ["/Users/sorlandoiii/dynasty-league/resources/ingest-files/" file ".xls"]
       (clojure.string/join)
       (xls/load-workbook)
       (xls/select-sheet "Sheet1")
       (xls/select-columns codec)
       (drop 4)))

(defmulti clean-athlete-data
  "Extract and filter athlete data from excel spreadsheets."
  (fn [file codec] file))

;;; QBs

(def qb-codec
  {:A :name :B :team :E :pass-yds :F :pass-tds :G :ints
   :I :rush-yds :J :rush-tds :K :fumbs})

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

(defn gen-qb-proj-pts
  "Generates projected fantasy points for all QBs based on league settings."
  [qbs]
  (let [stngs (config/dynasty-settings :qb-scoring)]
    (for [qb qbs]
      (assoc qb :proj-pts (+ (/ (:pass-yds qb) (:pass-yd stngs))
                             (* (:pass-tds qb) (:pass-td stngs))
                             (/ (:rush-yds qb) (:rush-yd stngs))
                             (* (:rush-tds qb) (:rush-yd stngs))
                             (* (:ints qb) (:int stngs))
                             (* (:fumbs qb) (:fumb stngs)))))))

(defmethod clean-athlete-data "QB" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
        ;; (gen-qb-proj-pts $)))

;;; RBs

(def rb-codec
  {:A :name :B :team :D :rush-yds :E :rush-tds :G :rec-yds
   :H :rec-tds :I :fumbs})

(defmethod clean-athlete-data "RB" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
        ;; (gen-qb-proj-pts $)))

;;; WRs

(def wr-codec
  {:A :name :B :team :D :rush-yds :E :rush-tds :G :rec-yds
   :H :rec-tds :I :fumbs})

(defmethod clean-athlete-data "WR" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
        ;; (gen-qb-proj-pts $)))

;;; TEs

(def te-codec
  {:A :name :B :team :D :rec-yds :E :rec-tds :F :fumbs})

(defmethod clean-athlete-data "TE" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
        ;; (gen-qb-proj-pts $)))

;;; PKs

(def pk-codec
  {:A :init-rank :B :name :C :team :D :bye-week
   :E :fga :F :fgm :G :fgp :H :epa :I :exm :J :proj-pts})

;; Remove
(defn pk-threshold
  "Create tiers for pk based on :init-rank."[pks]
        (map #(if (<= (:init-rank %) 20) (assoc % :worth 8) (assoc % :worth 9)) pks))

(defmethod clean-athlete-data "PK" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
        ;; (gen-qb-proj-pts $)))

;;; PNs

;;; DEFs

(def df-codec
  {:A :init-rank :B :team :C :bye-week :D :sacks :E :fumb-recs :F :ints
   :G :def-td :H :pts-allowed :I :payds-game :J :ruyds-game :K :safeties
   :L :kr-td :M :proj-pts})

;; Remove
(defn df-threshold
  "Create tiers for def based on :init-rank."[dfs]
        (map #(if (<= (:init-rank %) 15) (assoc % :worth 8) (assoc % :worth 9)) dfs))

(defmethod clean-athlete-data "DEF" [file codec]
  (as-> ["FantasyPros_Fantasy_Football_Rankings_" file] $
        (clojure.string/join $)
        (load-spreadsheet $ codec)
        (map #(assoc % :position file) $)))
        ;; (convert-vals $ #{:pass-yds :pass-tds :ints :rush-yds :rush-tds :fumbs})))
        ;; (gen-qb-proj-pts $)))
