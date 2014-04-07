(ns clucifer.field
  (:import (org.apache.lucene.document Document Field Field$Index Field$Store)))

(defn create-field
  [data]
  (let [field-attributes (atom {:data data :store false :type String :analyzer :standard :analyzed false :norms false})]
    field-attributes))

(defn update-attribute 
  [field attribute value]
  (swap! field assoc attribute value))

(def field-store-map {false Field$Store/NO
                      true  Field$Store/YES})

(def meta-map-pair {[false false] Field$Index/ANALYZED
                    [true false]  Field$Index/NOT_ANALYZED
                    [false true]  Field$Index/ANALYZED_NO_NORMS
                    [true true]   Field$Index/NOT_ANALYZED_NO_NORMS})

(defn as-str ^String [x]
  (if (keyword? x)
    (name x)
    (str x)))

(defn to-lucene-field 
  [field]
  (let [lucene-field (Field. 
                       (as-str (keys (:data @field))) (as-str (vals (:data @field))) 
                       (field-store-map (:store @field)) (meta-map-pair [(:analyzed @field) (:norms @field)]))]
    lucene-field))