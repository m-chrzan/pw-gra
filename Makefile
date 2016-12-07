TESTS=testy/PozycjaTests testy/PostaćNaPlanszyTests testy/ProstaPlanszaTests \
	  testy/MojaPlanszaTests

all: $(TESTS:=.class)

%.class:
	javac $(@:.class=.java)


.PHONY: run_tests clean

run_tests: all
	$(foreach test, $(TESTS), java $(test);)

clean:
	rm -f gra/*.class testy/*.class sample/*.class
