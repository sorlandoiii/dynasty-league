(ns dynasty-league.core)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def my-map {:a 1 :b 2 :c 3})

(defn mytest [a]
    (def my-map (dissoc my-map a)))
