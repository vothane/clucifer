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

(defmacro delete-> [param & body]
  `(with-open [writer# (index-writer *index*)]
    (let [~'deleted (delete writer# ~param)]
      (or ~@body
          ~'deleted))))

(defn delete-by-query [writer query]
  (let [parser (QueryParser. *version* "_content" *analyzer*)
        query  (.parse parser query)]
    (.deleteDocuments writer query)))

(defn as-str ^String [x]
  (if (keyword? x)
    (name x)
    (str x)))

(defn delete-by-maps [writer maps]
  (doseq [m maps]
    (let [query (BooleanQuery.)]
      (doseq [[key value] m]
        (.add query
          (BooleanClause. (TermQuery. (Term. 
            (.toLowerCase (as-str key))
            (.toLowerCase (as-str value))))
            BooleanClause$Occur/MUST)))
      (.deleteDocuments writer query))))

(defmulti delete
  (fn [writer param] (class param)))
(defmethod delete clojure.lang.PersistentVector [writer param]
  (delete-by-maps writer param))
(defmethod delete clojure.lang.PersistentArrayMap [writer param]
  (delete-by-maps writer param))
(defmethod delete String [writer param]
  (delete-by-query writer param))