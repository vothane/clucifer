(ns clucifer.core-test
  (:require [clojure.test :refer :all]
            [clucifer.core :refer :all]
            [clucifer.index :refer :all]
            [clucifer.search :refer :all]
            [clucifer.delete :refer :all])
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


(testing "lucene macro"
  (let [_ (deflucene *instance*)]
    (is (= (.toString (class *instance*)) "class org.apache.lucene.store.RAMDirectory"))))

(deflucene *test*)

(lucene-> *test*
  (index-> "ide" "test"))

(lucene-> *test*
  (search-> "ide" "*:*"
    (is (= 1 (.totalHits hits)))
    (is (= "test" (.scoreDocs hits)))))

(lucene-> *test*
  (delete-> {"ide" "test"})
  (search-> "ide" "*:*"
    (is (not= "test" (.scoreDocs hits)))))