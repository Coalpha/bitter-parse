build:
	javac FracCalc.java -d bin

run: build
	java -cp bin FracCalc

.PHONY: build run
