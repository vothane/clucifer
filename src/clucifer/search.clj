(ns clucifer.search
  (:use [clucifer.core
         :only [index-reader *index* *version* *analyzer*]])
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.index IndexReader DirectoryReader)
           (org.apache.lucene.queryparser.classic QueryParser)
           (org.apache.lucene.search BooleanClause BooleanClause$Occur
                                     BooleanQuery IndexSearcher Query TopDocs ScoreDoc
                                     Scorer TermQuery)
           (org.apache.lucene.document Document Field Field$Index Field$Store)
           (org.apache.lucene.store NIOFSDirectory RAMDirectory Directory)))

(defmacro search-> 
  [^String field ^String query & body]
  {:pre (string? query)}
  `(with-open [reader# (index-reader *index*)]
    (let [searcher# (IndexSearcher. reader#)
          parser#   (QueryParser. *version* ~field *analyzer*)
          term#     (.parse parser# ~query)
          ~'hits    (.search searcher# term# 10)
          ~'results (documents->maps searcher# ~'hits)]
      (or ~@body
          ~'results))))

(defn- field->key [field]
  (keyword (.name field)))

(defn- field->value [field]
  (or (.numericValue field) (.stringValue field)))

(defn document->map [^IndexSearcher searcher ^ScoreDoc score-doc]
  (let [id         (.doc score-doc)
        document   (.doc searcher id)
        fields     (.getFields document)]
    (zipmap (map field->key fields) (map field->value fields))))
      
(defn documents->maps [^IndexSearcher searcher ^TopDocs hits]
  (with-meta
    (map #(document->map searcher %) (.scoreDocs hits))
      {:totalHits (.totalHits hits)
       :maxScore (.getMaxScore hits)}))
