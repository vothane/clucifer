(ns clucifer.index)

(defmacro index-> 
  [document field-key field-value store? indexed? & body]
  `(let [~'_      (.add ^Document ~document (Field. ~field-key ~field-value ~store? ~indexed?))
         ~'writer (index-writer *index*)
         ~'added  (.addDocument ~'writer ~document)]
     (or ~@body
         ~'added)))
