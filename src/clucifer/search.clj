(ns clucifer.search
  (:use [clucifer.core
         :only [index-reader *index* *version* *analyzer*]])
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.index IndexReader DirectoryReader)
           (org.apache.lucene.queryparser.classic QueryParser)
           (org.apache.lucene.search BooleanClause BooleanClause$Occur
                                     BooleanQuery IndexSearcher Query ScoreDoc
                                     Scorer TermQuery)
           (org.apache.lucene.store NIOFSDirectory RAMDirectory Directory)))

(defmacro search-> 
  [^String field ^String query & body]
  {:pre (string? query)}
  `(with-open [reader# (index-reader *index*)]
    (let [searcher# (IndexSearcher. reader#)
          parser#   (QueryParser. *version* ~field *analyzer*)
          term#     (.parse parser# ~query)
          ~'hits    (.search searcher# term# 10)]
      (or ~@body
          ~'hits))))