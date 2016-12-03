package testy;

import gra.Pozycja;
import gra.Kierunek;

public class PozycjaTests {
    private static void beginTest(String name) {
        Testing.beginTest("Pozycja." + name);
    }

    public static void testGetters() {
        beginTest("getters");

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

    public static void testNaPlanszy() {
        beginTest("naPlanszy");

        Pozycja p1 = new Pozycja(0, 0);
        Pozycja p2 = new Pozycja(0, 2);
        Pozycja p3 = new Pozycja(5, 0);
        Pozycja p4 = new Pozycja(1, 2);
        Pozycja p5 = new Pozycja(-1, 0);
        Pozycja p6 = new Pozycja(0, -3);
        Pozycja p7 = new Pozycja(-3, -4);

        Testing.checkTrue(p1.naPlanszy(), "(0, 0) is on the board");
        Testing.checkTrue(p2.naPlanszy(), "(0, 2) is on the board");
        Testing.checkTrue(p3.naPlanszy(), "(5, 0) is on the board");
        Testing.checkTrue(p4.naPlanszy(), "(1, 2) is on the board");
        Testing.checkFalse(p5.naPlanszy(), "(-1, 0) is not on the board");
        Testing.checkFalse(p6.naPlanszy(), "(0, -3) is not on the board");
        Testing.checkFalse(p7.naPlanszy(), "(-3, -4) is not on the board");
    }

    public static void testDajPozycjęZaPlanszą() {
        beginTest("dajPozycjęZaPlanszą");

        Pozycja p = Pozycja.dajPozycjęZaPlanszą();

        Testing.checkFalse(p.naPlanszy(), "Position is off the board");
    }

    public static void main(String[] args) {
        testGetters();
        testPrzesuń();
        testNaPlanszy();
        testDajPozycjęZaPlanszą();
    }
}
