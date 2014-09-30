(ns clucifer.index
  (:use [clucifer.core
         :only [index-writer *index*]])
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis Analyzer TokenStream)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.document Document Field Field$Index Field$Store)
           (org.apache.lucene.index IndexWriter IndexWriterConfig FieldInfo)))

(defmacro index-> 
  [field-key field-value & body]
  `(with-open [writer# (index-writer *index*)]
    (let [~'added (add writer# ~field-key ~field-value)]
      (or ~@body
          ~'added))))

(def configs {:stored true :indexed true :analyzed false :norms false})

(def meta-map-pair {[false false] Field$Index/ANALYZED
                    [true false] Field$Index/NOT_ANALYZED
                    [false true] Field$Index/ANALYZED_NO_NORMS
                    [true true] Field$Index/NOT_ANALYZED_NO_NORMS})

(defn make-field [field-key field-value]
  (let [meta-map (merge configs (meta field-key))]
    (Field. field-key field-value
      (if (false? (:stored meta-map))
        Field$Store/NO
        Field$Store/YES)
      (if (false? (:indexed meta-map))
        Field$Index/NO
        (meta-map-pair [(false? (:analyzed meta-map)) (false? (:norms meta-map))])))))

(defn add-by-string [writer field-key field-value]
  (let [document (Document.)]
    (.add ^Document document (make-field field-key field-value))
    (.addDocument writer document)))

(defn param-types [param1 param2] 
  (cond 
    (and (string? param1) (string? param2)) :strings
    :else :later))

(defmulti add
  (fn [writer param1 param2] (param-types param1 param2)))
(defmethod add :strings [writer param1 param2]
  (add-by-string writer param1 param2))
