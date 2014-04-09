(ns clucifer.field-test
  (:require [clojure.test :refer :all]
            [clucifer.field :refer :all]))

(deftest field

  (testing "attributes should have default values"
    (let [a-field (create-field {:name "clucifer"})]
      (is (= (:store @a-field) true))
      (is (= (:type @a-field) String))
      (is (= (:analyzer @a-field) :standard))))

  (testing "attributes should be updated"
    (let [a-field (create-field {:name "clucifer"})]
      (update-attribute a-field :store false)
      (is (= (:store @a-field) false))))

  (testing "should convert to Lucene field"
    (let [a-field      (create-field {:name "clucifer"})
          lucene-field (to-lucene-field a-field)]
      (is (= (type lucene-field) org.apache.lucene.document.Field))))  

)                         
