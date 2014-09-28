(ns clucifer.index
  (:use [clucifer.core
         :only [index-writer *index*]])
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis Analyzer TokenStream)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.document Document Field Field$Index Field$Store)
           (org.apache.lucene.index IndexWriter IndexWriterConfig FieldInfo)))

(defmacro index-> 
  [document field-key field-value & body]
  `(with-open [writer# (index-writer *index*)]
    (let [_#      (.add ^Document ~document (make-field ~field-key ~field-value))
          ~'added (.addDocument writer# ~document)]
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