(ns clucifer.field)

(defn create-field
  []
  (let [field-attributes (atom {:store false :type String :analyzer :standard})]
    field-attributes))

(defn update-attribute [field attribute value]
  (swap! field assoc attribute value))