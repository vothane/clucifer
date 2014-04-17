(ns clucifer.index-test
  (:require [clojure.test :refer :all]
            [clucifer.core :refer :all]
            [clucifer.index :refer :all]))

(deftest index

  (testing "should be empty after clear"
    (def lucene-index (memory-index))
    (def test-index (create-index)) 
    (add test-index {"id" 7, "team" "Red Sox"})
    (clear test-index)      
    (is (= (uncommitted test-index) {})))

  (testing "should be empty after commit"
    (def lucene-index (memory-index))
    (def test-index (create-index)) 
    (add test-index {"id" 7, "team" "Red Sox"})
    (commit lucene-index test-index)
    (is (uncommitted test-index) {}))

  (testing "contains one uncommited document"
    (def lucene-index (memory-index))
    (def test-index (create-index)) 
    (add test-index {"id" 7, "team" "Red Sox"})
    (is (= (uncommitted test-index) {"id" 7, "team" "Red Sox"})))
  
  (testing "should have successfully indexed uncommitted data"
    (def lucene-index (memory-index))
    (def test-index (create-index)) 
    (add test-index {"id" 7, "team" "Red Sox"})
    (commit lucene-index test-index)
    (let [hits (search lucene-index "team" "Red Sox" 10)]
    (is (= (.totalHits hits) 1))))   
)  