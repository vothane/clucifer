(ns clucifer.core-test
  (:require [clojure.test :refer :all]
            [clucifer.core :refer :all]))

(testing "lucene macro"
  (let [index (deflucence *instance*)]
    (is (= index "org.apache.lucene.store.RAMDirectory"))))

