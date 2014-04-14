(ns clucifer.index
  (:import (org.apache.lucene.document Document Field Field$Index Field$Store)))

(defn create-index []
  (let [index (ref {:index-info {}, :uncommitted {}, :deleted-ids []})]
    index))

(defn add [index data]
  (dosync (alter index merge-with merge {:uncommitted data})))

(defn clear [index]
  (dosync (alter index update-in [:uncommitted] {})))

(defn commit [index]
  (dosync (alter index update-in [:uncommitted] {})))

(defn uncommitted [index]
  (:uncommitted @index))