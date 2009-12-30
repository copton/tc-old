(ns tc.lib.convert
	(:import 
		xtc.tree.GNode
		xtc.util.Pair
	)
)

; pair of string and sequence of Nodes
(defstruct Node :name :children)

; converts GNode to Node
(defn from-gnode [obj]
	(cond
		(nil? obj) nil
		(string? obj) obj
		true
			(let [node (GNode/cast obj)]
				(struct Node (.getName node) (for [i (range (.size node))] (from-gnode (.get node i))))
			)
	)
)

; converts Node to GNode
(defn to-gnode [obj]
	(letfn [
		; converts sequence of GNodes to nested Pairs of GNodes
		(seq-to-pair [seq]
			(cond
				(= (count seq) 1) (Pair. (first seq))
				true (Pair. (first seq) (seq-to-pair (rest seq)))
			)
		)
	]
		(cond
			(nil? obj) nil
			(string? obj) obj
			true (if (empty? (obj :children))
					(GNode/create (obj :name))
					(GNode/createFromPair (obj :name) (seq-to-pair (map to-gnode (obj :children))))
			)
		)
	)	
)

(defn to-xml [ast]
	(letfn [(process [obj]
			(cond
				(nil? obj) (str "<null/>")
				(string? obj) (str "<string>" obj "</string>")
				true (str "<" (obj :name) ">" (apply str (map process (obj :children))) "</" (obj :name) ">")
			)
		)
	]
		(str "<?xml version=\"1.0\" encoding=\"utf-8\"?>" (process ast))
	)
)
