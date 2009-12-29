.PHONY: all clean

TARGETS = applied instantiated processed valued

all:
	for i in $(TARGETS); do \
		mkdir $$i; \
		java xtc.parser.Rats -html -$$i -in $(XTC)/src -out $$i $(XTC)/src/xtc/lang/C.rats; \
		cp grammar.css $$i; \
	done

clean:
	rm -fr $(TARGETS)
