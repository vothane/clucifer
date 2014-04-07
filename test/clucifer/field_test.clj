(ns clucifer.field-test
  (:require [clojure.test :refer :all]
            [clucifer.field :refer :all]))

(deftest field

  (testing "attributes should have default values"
    (let [a-field (create-field)]
      (is (= (:store @a-field) false))
      (is (= (:type @a-field) String))
      (is (= (:analyzer @a-field) :standard))))
                         
)                         
