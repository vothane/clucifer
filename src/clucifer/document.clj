(ns clucifer.document
  (:import (org.apache.lucene.document Document)))

(defn create-document
  []
  (let [doc (atom (Document.))]
    doc))

(defn add-field 
  [doc field]
  (.add doc field))