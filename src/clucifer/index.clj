(ns clucifer.index
  (:use [clucifer.core
         :only [index-writer *index* default-field]])
  (:import (java.io StringReader File)
           (org.apache.lucene.analysis Analyzer TokenStream)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.document Document Field Field$Index Field$Store 
             DoubleField Field$Store FloatField IntField LongField TextField)
           (org.apache.lucene.index IndexWriter IndexWriterConfig FieldInfo)))

(defmacro index-> 
  [arg & body]
  `(with-open [writer# (index-writer *index*)]
    (let [~'added (add writer# ~arg)]
      (or ~@body
          ~'added))))

(declare add-by-map add-by-maps)

(defmulti add
  (fn [writer param] (class param)))
(defmethod add clojure.lang.PersistentArrayMap [writer map]
  (add-by-map writer map))
(defmethod add clojure.lang.PersistentVector [writer maps]
  (add-by-maps writer maps))
(defmethod add clojure.lang.PersistentList [writer maps]
  (add-by-maps writer maps))

(def configs {:stored true :indexed true :analyzed false :norms false})
(def configs {:stored true :indexed true :analyzed false :norms false})
(def meta-map-pair {[false false] Field$Index/ANALYZED
                    [true false] Field$Index/NOT_ANALYZED
                    [false true] Field$Index/ANALYZED_NO_NORMS
                    [true true] Field$Index/NOT_ANALYZED_NO_NORMS})

(defmulti ->field
  (fn [k v stored indexed] (class v)))
(defmethod ->field :default [k v stored indexed]
  (TextField. (name k) (str v) stored))
(defmethod ->field Integer [k v stored indexed]
  (IntField. (name k) v stored))
(defmethod ->field Long [k v stored indexed]
  (LongField. (name k) v stored))
(defmethod ->field Float [k v stored]
  (FloatField. (name k) v stored))
(defmethod ->field Double [k v stored]
  (DoubleField. (name k) v stored))

(defn field-configs [meta-map]
  (let [stored  (if (false? (:stored meta-map)) Field$Store/NO Field$Store/YES)
        indexed (if (false? (:indexed meta-map))
                  Field$Index/NO
                  (meta-map-pair [(false? (:analyzed meta-map)) (false? (:norms meta-map))]))]
    [stored indexed]))

(defn- map->document [map]
  (let [document         (Document.)
        meta-map         (merge configs (meta map))
        [stored indexed] (field-configs meta-map)]
    (doseq [[key value] map]
      (.add ^Document document (->field key value stored indexed)))
    document))

(defn add-by-map [writer map]
  (.addDocument writer (map->document map)))

(defn add-by-maps [writer maps]
  (doseq [m maps]
    (add-by-map writer m)))
