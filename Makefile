TESTS=testy/PozycjaTests testy/PostaćNaPlanszyTests testy/ProstaPlanszaTests \
	  testy/MojaPlanszaTests

all: $(TESTS:=.class) life

life: testy/GraWŻycie.class

%.class:
	javac $(@:.class=.java)


.PHONY: run_tests clean

run_tests: $(TESTS:=.class)
	$(foreach test, $(TESTS), java $(test);)

clean:
	rm -f gra/*.class testy/*.class sample/*.class
