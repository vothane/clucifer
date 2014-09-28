(ns clucifer.delete
  (:use [clucifer.core
         :only [index-writer *index* *version* *analyzer*]])
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.index IndexWriter IndexReader DirectoryReader Term)
           (org.apache.lucene.queryparser.classic QueryParser)
           (org.apache.lucene.search BooleanClause BooleanClause$Occur
                                     BooleanQuery IndexSearcher Query ScoreDoc
                                     Scorer TermQuery)
           (org.apache.lucene.store NIOFSDirectory RAMDirectory Directory)))

(defmacro delete-> [query field & body]
  `(with-open [writer# (index-writer *index*)]
    (let [parser#   (QueryParser. *version* ~field *analyzer*)
          query#    (.parse parser# ~query)
          ~'deleted (.deleteDocuments writer# query#)]
      (or ~@body
          ~'deleted))))
