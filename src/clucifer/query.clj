(ns clucifer.query
  (:require [clojure.string :as str]))

(defn- spaces
  [operator]
  (if (or (= (name operator) "AND") (= (name operator) "OR"))
    (str " ")
    (str "")))

(defn expand-expr 
  [expr]
  (if (coll? expr)
    (if (= (first expr) `unquote)
      "?"
      (let [[op & args] expr]
        (str (str/join (str (spaces op) op (spaces op)) (map expand-expr args)))))
    expr))

(declare expand-clause)

(def clause-map
  {'SEARCH (fn [expr] (str (expand-expr expr)))})

(defn expand-clause 
  [[op & args]]
  (apply (clause-map op) args))

(defmacro SEARCH 
  [& args]
  (let [expression [(expand-clause (cons 'SEARCH args))
                   (vec (for [n (tree-seq coll? seq args) 
                   :when (and (coll? n) (= (first n) `unquote))] (second n)))]]
    (str/replace (first expression) "FIELD:VAL" ":")))