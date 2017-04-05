all:
	@echo "Compiling files"
	@javac *.java
	@jar cf Server.jar *.class
	@rm *.class

run:
	@java -cp Server.jar MainClass

clean:
	@rm *.jar