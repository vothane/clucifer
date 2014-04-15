(ns clucifer.index
  (:require [clucifer.field :refer :all])
  (:import (org.apache.lucene.document Document Field Field$Index Field$Store)
           (org.apache.lucene.index IndexWriter IndexReader Term
                                    IndexWriterConfig DirectoryReader FieldInfo)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.util Version AttributeSource)))

(def ^{:dynamic true} *version* Version/LUCENE_CURRENT)
(def ^{:dynamic true} *analyzer* (StandardAnalyzer. *version*))

(defn create-index []
  (let [index (ref {:index-info {}, :uncommitted {}})]
    index))

(defn- index-writer ^IndexWriter [index]
  (IndexWriter. index (IndexWriterConfig. *version* *analyzer*)))

(defn add [index data]
  (dosync (alter index merge-with merge {:uncommitted data})))

(defn clear [index]
  (dosync (alter index update-in [:uncommitted] {})))

(defn- add-field [document map & meta-map]
  (let [base  (create-field)
        field (update-meta-data base map)]
    (.add ^Document document (to-lucene-field field))))

(defn- map->document [map]
  (let [document (Document.)]
    (for [pair map] (add-field document pair (meta pair)))))

(defn- add->lucence [index & maps]
  (with-open [writer (index-writer index)]
    (doseq [m maps]
      (.addDocument writer (map->document m)))))

(defn commit [index]
  (add->lucence index (:uncommitted @index))
  (clear index))

(defn uncommitted [index]
  (:uncommitted @index))