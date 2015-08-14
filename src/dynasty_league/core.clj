(ns dynasty-league.core
  (:require [dk.ative.docjure.spreadsheet :as xls]))

(declare all-athletes)

(defn load-spreadsheet
  "Loads excel spreadsheets using docjure."
  [file codec]
  (->> ["/Users/sorlandoiii/dynasty-league/resources/ingest-files/" file ".xlsx"]
       (clojure.string/join)
       (xls/load-workbook)
       (xls/select-sheet "Sheet1")
       (xls/select-columns codec)
       (rest)))

(defmulti clean-athlete-data
  "Filter extracted spreadsheet data."
  (fn [file codec] file))

;;; QBs

(def qb-codec
  {:A :init-rank :B :name :C :team :D :bye-week :E :sos
   :F :exp :G :gp-2014 :H :comps :I :atts :J :comp-pct :K :yds
   :L :tds :M :int})

(defn qb-threshold
  "Create tiers for qb based on :init-rank."[qbs]
        (map #(cond (<= (:init-rank %) 5) (assoc % :worth 1)
                    (<= (:init-rank %) 10) (assoc % :worth 2)
                    (<= (:init-rank %) 20) (assoc % :worth 3)
                    (<= (:init-rank %) 30) (assoc % :worth 4)
                    :else (assoc % :worth 5)) qbs))

(defmethod clean-athlete-data "QB" [file codec]
  (as-> (load-spreadsheet file codec) $
        (filter #(not= nil (:bye-week %)) $)
        (map #(assoc % :position file) $)
        (qb-threshold $)))

;;; RBs

;;; WRs

(def wr-codec
  {:A :init-rank :B :name :C :team :D :bye-week :E :sos
   :F :gp-2014 :G :exp :H :targets :I :recs :J :rec-yds :K :rec-td
   :L :return-yds :M :return-tds :N :qb-rank})

(defn wr-threshold
  "Create tiers for wr based on :init-rank."[wrs]
        (map #(cond (<= (:init-rank %) 11) (assoc % :worth 2)
                    (<= (:init-rank %) 20) (assoc % :worth 3)
                    (<= (:init-rank %) 40) (assoc % :worth 4)
                    (<= (:init-rank %) 60) (assoc % :worth 5)
                    (<= (:init-rank %) 80) (assoc % :worth 6)
                    :else (assoc % :worth 7)) wrs))

(defmethod clean-athlete-data "WR" [file codec]
  (as-> (load-spreadsheet file codec) $
        (filter #(not= nil (:bye-week %)) $)
        (map #(assoc % :position file) $)
        (wr-threshold $)))

;;; TEs

;;; PNs

;;; Ks

;;; DEFs

;;; Extraction of all positions

(def master-athlete-codec
  {"QB" qb-codec
   "WR" wr-codec})

;; TODO: Put sequence of maps into one map with [:name :team] as vector key.
(defn create-all-athlete-data
  "Extract all athlete files and combine into one datastructure. Takes
  in a map with filename-codec key-value pairs."
  [file-codec]
  (def all-athletes
    (flatten (for [[file codec] file-codec]
               (clean-athlete-data file codec)))))
