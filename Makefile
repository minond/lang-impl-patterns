scalac: src/main/scala/ll1rdp src/main/scala/ll1rdl

.PHONY: src/main/scala/ll1rdl
src/main/scala/ll1rdl:
	scalac src/main/scala/ll1rdl/*.scala

.PHONY: src/main/scala/ll1rdp
src/main/scala/ll1rdp:
	scalac src/main/scala/ll1rdp/*.scala
