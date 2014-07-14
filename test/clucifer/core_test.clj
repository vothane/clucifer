(ns clucifer.core-test
  (:require [clojure.test :refer :all]
            [clucifer.core :refer :all]))

(testing "lucene macro"
  (let [_ (deflucence *instance*)]
    (is (= (.toString (class *instance*)) "class org.apache.lucene.store.RAMDirectory"))))

