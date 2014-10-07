(ns clucifer.core-test
  (:require [clojure.test :refer :all]
            [clucifer.core :refer :all]
            [clucifer.index :refer :all]
            [clucifer.search :refer :all]
            [clucifer.delete :refer :all])
  (:use [clucifer.data :only [top-selling-books top-grossing-films]] :reload)
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis Analyzer TokenStream)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.document Document Field Field$Index Field$Store 
             DoubleField Field$Store FloatField IntField LongField TextField)
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

(deflucene *book-index*)

(lucene-> *book-index*
  (do
    (index-> top-selling-books)
    (search-> {:title "Cities"}
      (do 
        (is (= 1 (count results)))
        (is (= "A Tale of Two Cities" (:title (first results)))
        (is (= "Charles Dickens" (:author (first results)))))))
    (search-> {:author "Dan Brown"}
      (do
        (is (= 1 (count results)))
        (is (= "The Da Vinci Code" (:title (first results)))
        (is (= 2003 (:published (first results)))))))))

(lucene-> *book-index*
  (delete-> {"title" "The Hobbit"}))

(lucene-> *book-index*
  (search-> {:title "The Hobbit"}
    (is (= 0 (count results)))))


(deflucene *film-index*)

(lucene-> *film-index*
  (do
    (index-> top-grossing-films)
    (search-> {:title "Pirates" :director "Gore"}
      (do 
        (is (= 2 (count results)))
        (is (= results
               [{:title "Pirates of the Caribbean: At World's End" :year 2007 :director "Gore Verbinski" :gross 963420425 :rank 23} 
                {:title "Pirates of the Caribbean: Dead Man's Chest" :year 2006 :director "Gore Verbinski" :gross 1066179725 :rank 12}]))))))
    