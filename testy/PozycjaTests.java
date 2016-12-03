package testy;

import gra.Pozycja;
import gra.Kierunek;

public class PozycjaTests {
    private static void beginTest(String name) {
        Testing.beginTest("Pozycja." + name);
    }

    public static void testConstructor() {
        beginTest("Constructor");

        Pozycja p1 = new Pozycja(4, 2);
        Testing.checkEqual(p1.wiersz(), 4, "Row set correctly");
        Testing.checkEqual(p1.kolumna(), 2, "Column set correctly");

        Pozycja p2 = new Pozycja(12, 24);
        Testing.checkEqual(p2.wiersz(), 12, "Row set correctly");
        Testing.checkEqual(p2.kolumna(), 24, "Column set correctly");
    }

    public static void testPrzesuń() {
        beginTest("przesuń");

        Pozycja p1 = new Pozycja(4, 2);
        Testing.checkEqual(p1.przesuń(Kierunek.GÓRA), new Pozycja(3, 2),
                "Move up decreases row");
        Testing.checkEqual(p1.przesuń(Kierunek.DÓŁ), new Pozycja(5, 2),
                "Move down increases row");
        Testing.checkEqual(p1.przesuń(Kierunek.LEWO), new Pozycja(4, 1),
                "Move left decreases column");
        Testing.checkEqual(p1.przesuń(Kierunek.PRAWO), new Pozycja(4, 3),
                "Move right increases column");
    }

    public static void main(String[] args) {
        testConstructor();
        testPrzesuń();
    }
}
