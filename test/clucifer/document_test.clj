(ns clucifer.document-test
  (:require [clojure.test :refer :all]
            [clucifer.document :refer :all]))

(deftest document

  (testing "should create a Lucene document"
    (let [a-doc (create-document)]
      (is (= (type @a-doc) org.apache.lucene.document.Document)))) 

)                         
