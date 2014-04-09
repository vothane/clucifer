(ns clucifer.field
  (:import (org.apache.lucene.document Document Field Field$Index Field$Store)))

(defn create-field
  [data]
  (let [field-attributes (atom {:data data :store true :indexed true :type String :analyzer :standard :analyzed false :norms false})]
    field-attributes))

(defn update-attribute 
  [field attribute value]
  (swap! field assoc attribute value))

(def field-store-map {false Field$Store/NO
                      true  Field$Store/YES})

(def meta-map-pair {[false false false] Field$Index/NO
                    [true false false]  Field$Index/ANALYZED
                    [true true false]   Field$Index/NOT_ANALYZED
                    [true false true]   Field$Index/ANALYZED_NO_NORMS
                    [true true true]    Field$Index/NOT_ANALYZED_NO_NORMS})

(defn as-str ^String [x]
  (if (keyword? x)
    (name x)
    (str x)))

(defn to-lucene-field 
  [field]
  (let [lucene-field (Field. 
                       (as-str (keys (:data @field))) (as-str (vals (:data @field))) 
                       (field-store-map (:store @field))                       
                       (meta-map-pair [(:indexed @field) (:analyzed @field) (:norms @field)] Field$Index/ANALYZED))]
    lucene-field))