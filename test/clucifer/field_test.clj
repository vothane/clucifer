(ns clucifer.field-test
  (:require [clojure.test :refer :all]
            [clucifer.field :refer :all]))

(deftest field

  (testing "meta attributes should have default values"
    (let [a-field (create-field {:name "clucifer"})]
      (is (= (:store (meta a-field)) true))
      (is (= (:type (meta a-field)) String))
      (is (= (:analyzer (meta a-field)) :standard)))))

  (testing "meta attributes should be updated"
    (let [a-field (create-field {:name "clucifer"})]
      (update-meta-data a-field :store false)
      (is (= (:store (meta a-field) false))))

  (testing "should convert to Lucene field"
    (let [a-field      (create-field {:name "clucifer"})
          lucene-field (to-lucene-field a-field)]
      (is (= (type lucene-field) org.apache.lucene.document.Field))))  

)                         
