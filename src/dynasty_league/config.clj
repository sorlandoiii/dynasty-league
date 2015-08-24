(ns dynasty-league.config
  (:require [dynasty-league.data :as data]))

;;; Draft specific starting configs

(def draft-spot 6)

;;; Codecs

(def qb-codec
  {:A :name :B :team :C :pass-atts :D :comps :E :pass-yds :F :pass-tds :G :ints
   :I :rush-yds :J :rush-tds :K :fumbs})

(def rb-codec
  {:A :name :B :team :D :rush-yds :E :rush-tds :F :recs :G :rec-yds
   :H :rec-tds :I :fumbs})

(def wr-codec
  {:A :name :B :team :D :rush-yds :E :rush-tds :F :recs :G :rec-yds
   :H :rec-tds :I :fumbs})

(def te-codec
  {:A :name :B :team :C :recs :D :rec-yds :E :rec-tds :F :fumbs})

(def pk-codec
  {:A :name :B :team :C :fgm :D :fga :E :xpm})

(def df-codec
  {:A :init-rank :B :team :C :bye-week :D :sacks :E :fumb-recs :F :ints
   :G :def-td :H :pts-allowed :I :payds-game :J :ruyds-game :K :safeties
   :L :kr-td :M :proj-pts})

(def master-athlete-codec
  {"QB" qb-codec
   "RB" rb-codec
   "WR" wr-codec
   "TE" te-codec
   "K" pk-codec})
   ;; "DF" df-codec})

;;; Settings

(def cur-settings (atom nil))

(defn update-cur-settings
  "Updates the cur-settings object with the config data from the
  persistence tier." [config]
  (reset! cur-settings config))

(def dynasty-settings
  {:general-settings {:teams 10
                      :roster-spots 25
                      :ir-spots 6
                      :taxi-spots 10
                      :salaries? true
                      :salary-cap 10000
                      :contracts? true
                      :start-qb 2
                      :start-rb 3
                      :start-wr 4
                      :start-te 2
                      :start-flex 0
                      :start-k 1
                      :start-df 1}
   :qb-scoring {:pass-td 3
                :pass-yd 25
                :comp 0.1
                :incomp -0.15
                :int -2
                :2pt 1
                :rush-td 3
                :rush-yd 10
                :fumb -1}
   :rb-scoring {:rush-yd 10
                :rush-td 3
                :reception 0.75
                :rec-yd 10
                :rec-td 3
                :fumb -1
                :2pt 1}
   :wr-scoring {:rush-yd 10
                :rush-td 3
                :reception 0.75
                :rec-yd 10
                :rec-td 3
                :fumb -1
                :2pt 1}
   :te-scoring {:rush-yd 10
                :rush-td 3
                :reception 0.75
                :rec-yd 10
                :rec-td 3
                :fumb -1
                :2pt 1}
   :pk-scoring {:30-39 3
                :40-49 4
                :50-plus 5
                :fg-made 3
                :xp-made 1
                :xp-missed -3}
   :df-scoring {:int 1
                :fumb 1
                :kick-ret-td 3
                :punt-ret-td 3
                :turnover-td 3
                :blocked-punt 2
                :blocked-fg 2
                :sack 1
                :safety 5}
   :draft-per-100 {:qb 12
                   :rb 35
                   :wr 41
                   :te 10
                   :k 1}
                   ;:def 1
   :adps data/tppr-adps})

(def twelve-ppr-settings
  {:general-settings {:teams 12
                      :roster-spots 16
                      :ir-spots 0
                      :taxi-spots 0
                      :salaries? false
                      :salary-cap 0
                      :contracts? false
                      :start-qb 1
                      :start-rb 3
                      :start-wr 3
                      :start-te 1
                      :start-flex 0
                      :start-k 1
                      :start-df 1}
   :qb-scoring {:pass-td 4
                :pass-yd 25
                :comp 0
                :incomp 0
                :int -1
                :2pt 2
                :rush-td 6
                :rush-yd 10
                :fumb -2}
   :rb-scoring {:rush-yd 10
                :rush-td 6
                :reception 1
                :rec-yd 10
                :rec-td 6
                :fumb -2
                :2pt 2}
   :wr-scoring {:rush-yd 10
                :rush-td 6
                :reception 1
                :rec-yd 10
                :rec-td 6
                :fumb -2
                :2pt 2}
   :te-scoring {:rush-yd 10
                :rush-td 6
                :reception 1
                :rec-yd 10
                :rec-td 6
                :fumb -2
                :2pt 2}
   :pk-scoring {:30-39 3
                :40-49 4
                :50-plus 5
                :fg-made 3
                :xp-made 1
                :xp-missed 0}
   :pn-scoring {:yds 0
                :inside-20 0}
   :df-scoring {:sack 1
                :int 2
                :fumb 2
                :safety 2
                :blocked-fg 2
                :kick-ret-td 6
                :punt-ret-td 6
                :turnover-td 6
                :blocked-punt 2
                :pts-allowed-0 10
                :pts-allowed-1-6 7
                :pts-allowed-7-13 4
                :pts-allowed-14-20 1
                :pts-allowed-21-27 0
                :pts-allowed-28-34 -1
                :pts-allowed-35 -4}
   :draft-per-100 {:qb 12
                   :rb 35
                   :wr 41
                   :te 10
                   :k 1}
                   ;:def 1
   :adps data/tppr-adps})
