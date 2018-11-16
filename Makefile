start: clean build

build: ./src/FracCalc.java
	cd src && walk FracCalc.java

clean:
	./clear_class.sh yes

.PHONY: start, build, clean
