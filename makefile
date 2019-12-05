JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES =	\
	MinBinaryHeap.java	\
	MinBinaryHeapNode.java		\
	RedBlackTree.java	\
	risingCity.java	\
	RedBlackTreeNode.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class