(ns tc
	(:import 
		(xtc.lang CParser)
		(java.io InputStreamReader)
		(java.io BufferedWriter)
		(java.io OutputStreamWriter)
		(xtc.lang CPrinter)
		(xtc.tree Printer)
	)
	(:use 
		[tc.transform :only (transform)]
		[tc.lib.convert :only (from-gnode to-xml to-gnode)]
	)
)

(def blocking #{"blocking"})

(let [ 
	pretty-print (fn [ast]
		(let [ 
			stream (BufferedWriter. (OutputStreamWriter. System/out))
			printer (CPrinter. (Printer. stream))
		]
			(.dispatch printer ast)		
			(.flush stream)
		)
	)

	parser (CParser. (InputStreamReader. System/in) "stdin")
    result (.pTranslationUnit parser 0) 
]
	(if (.hasValue result) 
		(let [
			ast (from-gnode (. result value))
		]
			(if (= (first *command-line-args*) "dumpXML")
				(print (to-xml ast))
				(pretty-print (to-gnode (transform ast blocking)))
			)
		)
		(if (= -1 (. result index)) 
			(print "Parse Error")
			(print str((.location parser (. result index)) ": " (. result msg)))
		)
	)
)

