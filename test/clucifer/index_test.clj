(ns clucifer.index-test
  (:require [clojure.test :refer :all]
            [clucifer.index :refer :all]))

(deftest index
   
  (def test-index (create-index)) 
  (add test-index {"id" 7, "team" "Red Sox"})

  (testing "should be empty after clear"
    (is (= (clear test-index) 0)))

  (testing "should be empty after commit"
    (is (= (commit test-index) 0)))

  (testing "contains one uncommited document"
    (is (= (count (uncommitted test-index)) 1))
    (is (= (:id (uncommitted test-index)) 7))
    (is (= (:team (uncommitted test-index)) "Red Sox")))

  (commit test-index)
  
  (testing "should have successfully indexed uncommitted data"
    (let [hits (search test-index "team" "Red Sox")]
    (is (= (count (hits)) 1))))
)             