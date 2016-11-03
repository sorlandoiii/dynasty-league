(ns dynasty-league.utility
  (:require [dynasty-league.config :as config]
            [dynasty-league.data :as data]))

(defn str->double
  "Transforms a string to a double, parsing first." [val]
  (let [val (if (= "" val) "0" (str val))]
    (if (every? #(re-find #"\d" (str %)) val)
      (Double/parseDouble (clojure.string/replace val #"," "")) 0.0)))

(defn convert-vals
  "Translates strings to doubles for each field (in fields) of the collection."
  [coll fields]
  (for [m coll] (into {} (for [[k v] m]
                           (if (contains? fields k)
                             {k (str->double v)}
                             {k v})))))

(defn trimmer
  "This function is called by default for every field. This checks to make sure
  that the value is a string before attempting to trim." [val]
  (if (= String (type val)) (clojure.string/trim val) (or val "")))

(defn gen-next-draft-spot
  "Determines what number pick will be next for me in the draft based on
  round (r), number of teams in the draft (N), and starting draft spot (n).

  Formula: when r is odd: (r - 1)N + n
           when r is even: rN - n + 1 " [teams]
  (let [r (int (inc (/ (count data/moves-made) teams)))]
    (if (even? r)
      (+ (* r teams) config/draft-spot)
      (+ (- (* (inc r) teams) config/draft-spot) 1))))
