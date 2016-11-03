(ns dynasty-league.utility-test
  (:require [clojure.test :refer :all]
            [dynasty-league.config :as config]
            [dynasty-league.data :as data]
            [dynasty-league.utility :refer :all]))

(deftest test-str->double
  (testing "Testing str->double."
    (is (= (str->double "33") 33.0))
    (is (= (str->double "04") 4.0))
    (is (= (str->double "a") 0.0))
    (is (= (str->double 1) 1.0))
    (is (= (str->double "") 0.0))))

(deftest test-convert-vals
  (testing "Testing convert-vals."
    (is (= (convert-vals [{:pass-yds "300"}] #{:pass-yds})
           (seq [{:pass-yds 300.0}])))
    (is (= (convert-vals [{:pass-yds "300" :rush-yds "100"}] #{:pass-yds :rush-yds})
           (seq [{:pass-yds 300.0 :rush-yds 100.0}])))
    (is (= (convert-vals [{:pass-yds "300" :rush-yds "100"}
                          {:pass-yds "450" :rush-yds "71"}] #{:pass-yds :rush-yds})
           (seq [{:pass-yds 300.0 :rush-yds 100.0} {:pass-yds 450.0 :rush-yds 71.0}])))
    (is (= (convert-vals [{:pass-yds "300" :rush-yds "100"}
                          {:pass-yds "450" :rec-yds "111"}] #{:pass-yds :rush-yds :rec-yds})
           (seq [{:pass-yds 300.0 :rush-yds 100.0} {:pass-yds 450.0 :rec-yds 111.0}])))))

(deftest test-trimmer
  (testing "Testing trimmer."
    (is (= (trimmer "33") "33"))
    (is (= (trimmer "  11") "11"))
    (is (= (trimmer "22  ") "22"))
    (is (= (trimmer "A") "A"))
    (is (= (trimmer 1) 1))
    (is (= (trimmer nil) ""))))

(deftest test-gen-next-draft-spot
  (testing "Testing gen-next-draft-spot."
    (with-redefs [config/draft-spot 3
                  data/moves-made ["player 1" "player 2" "player 3"]]
      (is (= (gen-next-draft-spot 10) 18)))))
