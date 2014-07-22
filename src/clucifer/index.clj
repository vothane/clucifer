(ns clucifer.index
  (:use [clucifer.core
         :only [index-writer *index*]])
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis Analyzer TokenStream)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.document Document Field Field$Index Field$Store)
           (org.apache.lucene.index IndexWriter IndexWriterConfig FieldInfo)))

(defmacro index-> 
  [document field-key field-value store? indexed? & body]
  `(let [~'_      (.add ^Document ~document (Field. ~field-key ~field-value ~store? ~indexed?))
         ~'writer (index-writer *index*)
         ~'added  (.addDocument ~'writer ~document)
         ~'_      (.close ~'writer)]
     (or ~@body
         ~'added)))
