(ns clucifer.search
  (:use [clucifer.core
         :only [index-reader *index* *version* *analyzer*]])
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.index IndexReader DirectoryReader)
           (org.apache.lucene.queryparser.classic QueryParser MultiFieldQueryParser)
           (org.apache.lucene.search BooleanClause BooleanClause$Occur
                                     BooleanQuery IndexSearcher Query TopDocs ScoreDoc
                                     Scorer TermQuery)
           (org.apache.lucene.document Document Field Field$Index Field$Store)
           (org.apache.lucene.store NIOFSDirectory RAMDirectory Directory)))

(defmacro search-> 
  [query & body]
  `(with-open [reader# (index-reader *index*)]
    (let [searcher# (IndexSearcher. reader#)
          query#    (make-query ~query)
          ~'hits    (.search searcher# query# 10)
          ~'results (documents->maps searcher# ~'hits)]
      (or ~@body
          ~'results))))

(declare query-type parse-query)

(defmulti make-query
  (fn [query] (query-type query)))
(defmethod make-query :term [query]
  (parse-query query (fn [v f a] (QueryParser. v f a))))
(defmethod make-query :multi [query]
  (parse-query query (fn [v f a] (MultiFieldQueryParser. v f a))))
          
(defn query-type [query] 
  (cond (= (count query) 1) :term
        (> (count query) 1) :multi
        :else :WAT?))   

(defn get-fields [query]
  (if (> (count query) 1)    
    (into-array (map #(name %) (keys query)))
    (name (key (first query)))))

(defn map->query [m] 
  (if (> (count m) 1)        
    (clojure.string/join 
      " AND "
      (map #(str (name (first %)) ":" "'" (second %) "'") m))
    (str (name (key (first m))) ":" (val (first m)))))

(defn parse-query [query query-parser]
  (let [fields (get-fields query)
        parser (query-parser *version* fields *analyzer*)]
    (.parse parser (map->query query))))
                
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
