(ns util.gnode2xml
	(:import xtc.tree.GNode)
)

(defn process [obj]
	(cond
		(nil? obj) (print (str "<null/>"))
		(string? obj) (print (str "<string>" obj "</string>"))
		true 	
			(let [node (GNode/cast obj)]
				(print (str "<" (.getName node) ">"))
				(dorun
					(for [i (range (.size node))] 
						(process (.get node i))
					)
				)
				(print (str "</" (.getName node) ">"))
			) 
	)
)

(defn dump [node]
	(print (str "<?xml version=\"1.0\" encoding=\"utf-8\"?>"))
	(process node)
)
