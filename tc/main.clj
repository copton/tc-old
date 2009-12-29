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
		[util.gnode2xml :only (dump)]
	)
)

(def blocking #{"blocking"})

(let [ 
	print (fn [ast]
		(let [ 
			stream (BufferedWriter. (OutputStreamWriter. System/out))
			printer (CPrinter. (Printer. stream))
		]
			(print (.toString ast))
			(.dispatch printer ast)		
			(.flush stream)
		)
	)

	parser (CParser. (InputStreamReader. System/in) "stdin")
    result (.pTranslationUnit parser 0) 
]
	(if (.hasValue result) 
		(if (= (first *command-line-args*) "dumpXML")
			(dump (. result value))
			(print (transform (. result value) blocking))
		)
		(if (= -1 (. result index)) 
			(print "Parse Error")
			(print str((.location parser (. result index)) ": " (. result msg)))
		)
	)
)

