# For Java
PROG     = OffloadServer
JAVAC    = javac
JAVA_DIR = ccs/labs/e2phone
JAVA_SRC = $(wildcard $(JAVA_DIR)/*.java) $(wildcard $(JAVA_DIR)/offloadserver/*.java)
JAVA_PACKAGE = ccs.labs.e2phone.offloadserver

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = $(JAVA_SRC:.java=.class)

all: $(CLASSES)

%.class : %.java
	$(JAVAC) $<

jar: $(CLASSES)
	echo Manifest-Version: 1.0 > MANIFEST.MF
	echo Main-Class: $(JAVA_PACKAGE).$(PROG) > MANIFEST.MF
	jar -cmf MANIFEST.MF $(PROG).jar $(JAVA_DIR)/*.class


# Run OffloadServer
run:
# 	java -jar $(PROG).jar
	java -Djava.library.path=$(LIB_DIR) $(JAVA_PACKAGE).$(PROG)

clean:
	$(RM) $(JAVA_DIR)/*.class $(JAVA_DIR)/offloadserver/*.class

