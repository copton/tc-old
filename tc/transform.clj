(ns tc.transform
	(:require [tc.lib :as lib])
)

(defn transform [ast blocking]
	(prn "transform")
	(lib/first-of ast :nix)
)
