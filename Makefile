scalac: \
	src/main/scala/ll1rdp \
	src/main/scala/ll1rdl \
	src/main/scala/llkrdp \
	src/main/scala/backtrack \
	src/main/scala/memoizing

.PHONY: src/main/scala/ll1rdl
src/main/scala/ll1rdl:
	scalac src/main/scala/ll1rdl/*.scala

.PHONY: src/main/scala/ll1rdp
src/main/scala/ll1rdp:
	scalac src/main/scala/ll1rdp/*.scala

.PHONY: src/main/scala/llkrdp
src/main/scala/llkrdp:
	scalac src/main/scala/llkrdp/*.scala

.PHONY: src/main/scala/backtrack
src/main/scala/backtrack:
	scalac src/main/scala/backtrack/*.scala

.PHONY: src/main/scala/memoizing
src/main/scala/memoizing:
	scalac src/main/scala/memoizing/*.scala
