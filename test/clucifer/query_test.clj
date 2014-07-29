(ns clucifer.query-test
  (:require [clojure.test :refer :all]
            [clucifer.query :refer :all]))

(deftest query-test
  (is (= (SEARCH (AND (FIELD:VAL field1 "'test'") (FIELD:VAL field2 111))) "field1:'test' AND field2:111")))
