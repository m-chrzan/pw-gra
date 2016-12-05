package testy;

import gra.ProstaPlansza;
import gra.Postać;
import gra.Akcja;
import gra.Kierunek;
import sample.ProstaPostać;
import sample.Finder;
import sample.SpecificFinder;

public class ProstaPlanszaTests {
    public static void beginTest(String name) {
        Testing.beginTest("ProstaPlansza." + name);
    }

    public static void checkEmpty(ProstaPlansza plansza, int row, int column,
            int height, int width, String msg) {
        boolean empty = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Finder finder = new Finder();
                plansza.sprawdź(row + i, column + j, finder, finder);
                if (finder.foundPostać()) {
                    empty = false;
                }
            }
        }

        Testing.checkTrue(empty, msg);
    }

    public static void checkFilled(ProstaPlansza plansza, int row, int column,
            int height, int width, String msg) {
        boolean filled = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Finder finder = new Finder();
                plansza.sprawdź(row + i, column + j, finder, finder);
                if (!finder.foundPostać()) {
                    filled = false;
                }
            }
        }

        Testing.checkTrue(filled, msg);
    }

    public static void checkIsSpecificPostać(ProstaPlansza plansza,
            Postać postać, int row, int column, int height, int width,
            String msg) {
        boolean allSpecificPostać = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                SpecificFinder finder = new SpecificFinder(postać);
                plansza.sprawdź(row + i, column + j, finder, finder);
                if (!finder.foundPostać()) {
                    allSpecificPostać = false;
                }
            }
        }

        Testing.checkTrue(allSpecificPostać, msg);
    }

    public static void testChęćPostawienia() {
        beginTest("chęćPostawienia");

        ProstaPlansza plansza = new ProstaPlansza(5, 5);

        checkEmpty(plansza, 0, 0, 5, 5, "ProstaPlansza is empty at first");

        ProstaPostać postać1 = new ProstaPostać(3, 3);
        plansza.chęćPostawienia(postać1, 0, 0);
        plansza.przesuńNaChcianąPozycję(postać1);
        checkFilled(plansza, 0, 0, 3, 3, "Area of postać is filled");
        checkIsSpecificPostać(plansza, postać1, 0, 0, 3, 3,
               "Area of postać is all taken up by that postać");

        ProstaPostać postać2 = new ProstaPostać(1, 1);
        plansza.chęćPostawienia(postać2, 4, 3);
        plansza.przesuńNaChcianąPozycję(postać2);
        checkIsSpecificPostać(plansza, postać2, 4, 3, 1, 1,
                "New postać placed correctly");
        checkIsSpecificPostać(plansza, postać1, 0, 0, 3, 3,
               "Old postać is still on the board");
        checkEmpty(plansza, 3, 0, 2, 3, "Rest of the board still empty (1/3)");
        checkEmpty(plansza, 0, 3, 4, 2, "Rest of the board still empty (2/3)");
        checkEmpty(plansza, 4, 4, 1, 1, "Rest of the board still empty (3/3)");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                ProstaPostać postać = new ProstaPostać(3, 3);
                plansza.chęćPostawienia(postać, -1, 0);
            }
        }, new IllegalArgumentException(),
        "Postać row has to be positive");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                ProstaPostać postać = new ProstaPostać(3, 3);
                plansza.chęćPostawienia(postać, 0, -1);
            }
        }, new IllegalArgumentException(),
        "Postać column has to be positive");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                ProstaPostać postać = new ProstaPostać(3, 3);
                plansza.chęćPostawienia(postać, 5, 0);
            }
        }, new IllegalArgumentException(),
        "Postać row has to fit on the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                ProstaPostać postać = new ProstaPostać(3, 3);
                plansza.chęćPostawienia(postać, 0, 5);
            }
        }, new IllegalArgumentException(),
        "Postać column has to fit on the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                ProstaPostać postać = new ProstaPostać(3, 3);
                plansza.chęćPostawienia(postać, 3, 3);
            }
        }, new IllegalArgumentException(),
        "Postać lower right corner has to fit on the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                ProstaPostać postać = new ProstaPostać(3, 3);
                plansza.chęćPostawienia(postać, 0, 3);
            }
        }, new IllegalArgumentException(),
        "Postać right edge has to fit on the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                ProstaPostać postać = new ProstaPostać(3, 3);
                plansza.chęćPostawienia(postać, 3, 0);
            }
        }, new IllegalArgumentException(),
        "Postać lower edge has to fit on the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                ProstaPostać postać = new ProstaPostać(3, 3);
                plansza.chęćPostawienia(postać, 100, 200);
            }
        }, new IllegalArgumentException(),
        "Postać has to be on the board");
    }

    public static void testChęćPrzesunięcia() {
        beginTest("chęćPrzesunięcia");

        ProstaPlansza plansza = new ProstaPlansza(5, 5);
        ProstaPostać postać1 = new ProstaPostać(2, 2);

        plansza.chęćPostawienia(postać1, 1, 1);
        plansza.przesuńNaChcianąPozycję(postać1);

        plansza.chęćPrzesunięcia(postać1, Kierunek.GÓRA);
        plansza.przesuńNaChcianąPozycję(postać1);
        checkIsSpecificPostać(plansza, postać1, 0, 1, 2, 2,
                "Moved Postać up");

        plansza.chęćPrzesunięcia(postać1, Kierunek.LEWO);
        plansza.przesuńNaChcianąPozycję(postać1);
        checkIsSpecificPostać(plansza, postać1, 0, 0, 2, 2,
                "Moved Postać left");

        plansza.chęćPrzesunięcia(postać1, Kierunek.DÓŁ);
        plansza.przesuńNaChcianąPozycję(postać1);
        checkIsSpecificPostać(plansza, postać1, 1, 0, 2, 2,
                "Moved Postać down");

        plansza.chęćPrzesunięcia(postać1, Kierunek.PRAWO);
        plansza.przesuńNaChcianąPozycję(postać1);
        checkIsSpecificPostać(plansza, postać1, 1, 1, 2, 2,
                "Moved Postać right");

        plansza.chęćPostawienia(postać1, 0, 0);
        plansza.przesuńNaChcianąPozycję(postać1);

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                plansza.chęćPrzesunięcia(postać1, Kierunek.GÓRA);
            }
        }, new IllegalArgumentException(), "Can't move Postać up off the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                plansza.chęćPrzesunięcia(postać1, Kierunek.LEWO);
            }
        }, new IllegalArgumentException(),
        "Can't move Postać left off the board");

        ProstaPostać postać2 = new ProstaPostać(2, 2);
        plansza.chęćPostawienia(postać2, 3, 3);
        plansza.przesuńNaChcianąPozycję(postać2);

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                plansza.chęćPrzesunięcia(postać2, Kierunek.DÓŁ);
            }
        }, new IllegalArgumentException(),
        "Can't move Postać down off the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                plansza.chęćPrzesunięcia(postać2, Kierunek.PRAWO);
            }
        }, new IllegalArgumentException(),
        "Can't move Postać right off the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                plansza.chęćPrzesunięcia(new ProstaPostać(1, 1), Kierunek.DÓŁ);
            }
        }, new IllegalArgumentException(),
        "Can't move Postać that isn't on the board");
    }

    public static void main(String[] args) {
        testChęćPostawienia();
        testChęćPrzesunięcia();
    }
}
