(ns clucifer.core
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis Analyzer TokenStream)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.document Document Field Field$Index Field$Store)
           (org.apache.lucene.index IndexWriter IndexReader Term
                                    IndexWriterConfig DirectoryReader FieldInfo)
           (org.apache.lucene.queryparser.classic QueryParser)
           (org.apache.lucene.search BooleanClause BooleanClause$Occur
                                     BooleanQuery IndexSearcher Query ScoreDoc
                                     Scorer TermQuery)
           (org.apache.lucene.search.highlight Highlighter QueryScorer
                                               SimpleHTMLFormatter)
           (org.apache.lucene.util Version AttributeSource)
           (org.apache.lucene.store NIOFSDirectory RAMDirectory Directory)))

(def ^{:dynamic true} *version* Version/LUCENE_CURRENT)
(def ^{:dynamic true} *analyzer* (StandardAnalyzer. *version*))

;; flag to indicate a default "_content" field should be maintained
(def ^{:dynamic true} *content* true)

(def ^:dynamic *index*)
(def ^:dynamic *indices*)

(def defaults {})

(defn memory-index
  "Create a new index in RAM."
  []
  (RAMDirectory.))

(defn disk-index
  "Create a new index in a directory on disk."
  [^String dir-path]
  (NIOFSDirectory. (File. dir-path)))

(defn index-reader
  "Create an IndexReader."
  ^IndexReader
  [index]
  (DirectoryReader/open ^Directory index))

(defn index-writer
  "Create an IndexWriter."
  ^IndexWriter
  [index]
  (IndexWriter. index (IndexWriterConfig. *version* *analyzer*)))

(defmacro lucene-> [deflucene-instance & body]
  `(binding [*index* ~deflucene-instance] ~@body))

(defmacro deflucene [-symbol & [configs]]
  (let [config (merge defaults configs)]
    `(def ~(with-meta -symbol {:dynamic true})
      (memory-index))))
