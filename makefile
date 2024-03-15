all: clear compile run clean




compile: 
	javac *.java
	
run:
	java TetrisGame
	
clear:
	@clear

clean:
	@rm *.class	
