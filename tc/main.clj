(ns tc
	(:import 
		(xtc.lang CParser)
		(java.io InputStreamReader)
		(java.io BufferedWriter)
		(java.io OutputStreamWriter)
		(xtc.lang CPrinter)
		(xtc.tree Printer)
	)
	(:use [tc.transform :only (transform)])
)

(def blocking #{"blocking"})

(let [ 
	print (fn [ast]
		(let [ 
			stream (BufferedWriter. (OutputStreamWriter. System/out))
			printer (CPrinter. (Printer. stream))
		]
			(prn "print")
			(prn (.toString ast))
			(.dispatch printer ast)		
			(.flush stream)
		)
	)

	parser (CParser. (InputStreamReader. System/in) "stdin")
    result (.pTranslationUnit parser 0) 
]
	(if (.hasValue result) 
		(print (transform (. result value) blocking))
		(if (= -1 (. result index)) 
			(prn "Parse Error")
			(prn (.location parser (. result index)) ": " (. result msg))
		)
	)
)

