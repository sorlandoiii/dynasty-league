(ns dynasty-league.extraction
  (:require [dk.ative.docjure.spreadsheet :as xls]))

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
  "Extract and filter athlete data from excel spreadsheets."
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

(def rb-codec
  {:A :init-rank :B :name :C :team :D :bye-week :E :sos :F :gp-2014
   :G :exp :H :atts :I :rush-yds :J :avg-rush :K :rush-tds :L :fumbs
   :M :targets :N :recs :O :rec-yds :P :rec-tds :Q :return-yds :R :return-tds})

(defn rb-threshold
  "Create tiers for rb based on :init-rank."[rbs]
        (map #(cond (<= (:init-rank %) 5) (assoc % :worth 1)
                    (<= (:init-rank %) 10) (assoc % :worth 2)
                    (<= (:init-rank %) 20) (assoc % :worth 4)
                    (<= (:init-rank %) 30) (assoc % :worth 5)
                    :else (assoc % :worth 6)) rbs))

(defmethod clean-athlete-data "RB" [file codec]
  (as-> (load-spreadsheet file codec) $
        (filter #(not= nil (:bye-week %)) $)
        (map #(assoc % :position file) $)
        (rb-threshold $)))

;;; WRs

(def wr-codec
  {:A :init-rank :B :name :C :team :D :bye-week :E :sos
   :F :gp-2014 :G :exp :H :targets :I :recs :J :rec-yds :K :rec-tds
   :L :return-yds :M :return-tds :N :qb-rank})

(defn wr-threshold
  "Create tiers for wr based on :init-rank."[wrs]
        (map #(cond (<= (:init-rank %) 11) (assoc % :worth 1)
                    (<= (:init-rank %) 20) (assoc % :worth 2)
                    (<= (:init-rank %) 40) (assoc % :worth 3)
                    (<= (:init-rank %) 60) (assoc % :worth 4)
                    (<= (:init-rank %) 80) (assoc % :worth 5)
                    :else (assoc % :worth 6)) wrs))

(defmethod clean-athlete-data "WR" [file codec]
  (as-> (load-spreadsheet file codec) $
        (filter #(not= nil (:bye-week %)) $)
        (map #(assoc % :position file) $)
        (wr-threshold $)))

;;; TEs

(def te-codec
  {:A :init-rank :B :name :D :team :E :adp :F :bye-week
   :G :recs :H :yds :I :tds :M :fumbs :N :proj-pts
   :O :price})

(defn te-threshold
  "Create tiers for te based on :init-rank."[tes]
        (map #(cond (<= (:init-rank %) 4) (assoc % :worth 2)
                    (<= (:init-rank %) 10) (assoc % :worth 3)
                    (<= (:init-rank %) 25) (assoc % :worth 4)
                    (<= (:init-rank %) 40) (assoc % :worth 5)
                    :else (assoc % :worth 6)) tes))

(defmethod clean-athlete-data "TE" [file codec]
  (as-> (load-spreadsheet file codec) $
        (map #(assoc % :position file) $)
        (te-threshold $)))

;;; PKs

(def pk-codec
  {:A :init-rank :B :name :C :team :D :bye-week
   :E :fga :F :fgm :G :fgp :H :epa :I :exm :J :proj-pts})

(defn pk-threshold
  "Create tiers for pk based on :init-rank."[pks]
        (map #(if (<= (:init-rank %) 20) (assoc % :worth 8) (assoc % :worth 9)) pks))

(defmethod clean-athlete-data "PK" [file codec]
  (as-> (load-spreadsheet file codec) $
        (map #(assoc % :position file) $)
        (pk-threshold $)))

;;; PNs

;;; DEFs

(def df-codec
  {:A :init-rank :B :team :C :bye-week :D :sacks :E :fumb-recs :F :ints
   :G :def-td :H :pts-allowed :I :payds-game :J :ruyds-game :K :safeties
   :L :kr-td :M :proj-pts})

(defn df-threshold
  "Create tiers for def based on :init-rank."[dfs]
        (map #(if (<= (:init-rank %) 15) (assoc % :worth 8) (assoc % :worth 9)) dfs))

(defmethod clean-athlete-data "DF" [file codec]
  (as-> (load-spreadsheet file codec) $
        (map #(assoc % :position file) $)
        (df-threshold $)))
