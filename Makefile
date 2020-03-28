build:
	javac FracCalc.java

run: build
	java FracCalc

.PHONY: build run
