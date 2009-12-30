(ns tc.transform
	#_(:require [tc.lib.tree :as tree])
)

(defn out [node]
	(println (first node))
)

#_(defn caller-map [ast]
	(map out (tree/iterator ast))
)

(defn transform [ast blocking]
	(prn "transform")
	#_(caller-map ast)
	ast
)
