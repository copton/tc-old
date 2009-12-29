(ns tc.lib.convert
	(:import 
		xtc.tree.GNode
		xtc.util.Pair
	)
)


(defn from-gnode [obj]
	(cond
		(nil? obj) nil
		(string? obj) obj
		true
			(let [node (GNode/cast obj)]
				[(.getName node) (for [i (range (.size node))] (from-gnode (.get node i)))]
			)
	)
)

(defn to-gnode [obj]
	(letfn [
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
			true (let [
					[name, children] obj
				]
				(if (empty? children)
					(GNode/create name)
					(GNode/createFromPair name (seq-to-pair (map to-gnode children)))
				)
			)
		)
	)	
)

(defn to-xml [ast]
	(letfn [(process [obj]
			(cond
				(nil? obj) (str "<null/>")
				(string? obj) (str "<string>" obj "</string>")
				true (let [[name, children] obj]
					(str "<" name ">" (apply str (map process children)) "</" name ">")
				)
			)
		)
	]
		(str "<?xml version=\"1.0\" encoding=\"utf-8\"?>" (process ast))
	)
)
