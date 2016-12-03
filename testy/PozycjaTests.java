package testy;

import gra.Pozycja;

public class PozycjaTests {
    private static void beginTest(String name) {
        Testing.beginTest("PozycjaTest." + name);
    }

    public static void testConstructor() {
        beginTest("Constructor");

        Pozycja p1 = new Pozycja(4, 2);
        Testing.checkEqual(p1.wiersz(), 4, "Row set correctly");
        Testing.checkEqual(p1.kolumna(), 2, "Column set correctly");

        Pozycja p2 = new Pozycja(12, 24);
        Testing.checkEqual(p2.wiersz(), 12, "Row set correctly");
        Testing.checkEqual(p2.kolumna(), 24, "Column set correctly");

        Testing.finishTest();
    }

    public static void main(String[] args) {
        testConstructor();
    }
}
