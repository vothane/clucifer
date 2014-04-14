(ns clucifer.field
  (:import (org.apache.lucene.document Document Field Field$Index Field$Store)))

(def defaults 
  {:store true :indexed true :analyzer :standard :analyzed false :norms false})

(defn create-field
  [field-name & opts]
  (let [field-name (with-meta data (merge defaults))]
    field))


(defn update-meta-data 
  [field attribute value]
  (vary-meta field assoc attribute value))

(def field-store-map {false Field$Store/NO
                      true  Field$Store/YES})

(def meta-map {[false false false] Field$Index/NO
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
                       (as-str (keys field)) (as-str (vals field)) 
                       (field-store-map (:store (meta field)))                       
                       (meta-map (into [] (map false? [(:indexed (meta field)) (:analyzed (meta field)) (:norms (meta field))])) 
                         Field$Index/ANALYZED))]
    lucene-field))