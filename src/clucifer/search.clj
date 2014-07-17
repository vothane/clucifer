(ns clucifer.search)

(defmacro search-> 
  [^String field ^String query & body]
  {:pre (string? query)}
  `(let [~'reader   (index-reader *index*)
         ~'searcher (IndexSearcher. ~'reader)
         ~'term     (Term. ~field ~query)
         ~'query    (TermQuery. ~'term)
         ~'docs     (.search ~'searcher ~query 10)]
     (or ~@body
         ~'docs)))
