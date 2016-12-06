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

    public static void testJestBlokowany() {
        beginTest("jestBlokowany");

        ProstaPlansza plansza = new ProstaPlansza(5, 6);

        ProstaPostać postać1 = new ProstaPostać(2, 3);
        ProstaPostać postać2 = new ProstaPostać(3, 2);

        plansza.chęćPostawienia(postać1, 2, 1);
        plansza.przesuńNaChcianąPozycję(postać1);

        Testing.checkFalse(plansza.jestBlokowany(postać1),
                "Postać not wanting to move is not blocked");

        postać2 = new ProstaPostać(3, 2);
        plansza.chęćPostawienia(postać2, 2, 1);
        Testing.checkTrue(plansza.jestBlokowany(postać2),
                "Can't have two Postać objects in the same place");

        postać2 = new ProstaPostać(3, 2);
        plansza.chęćPostawienia(postać2, 0, 0);
        Testing.checkTrue(plansza.jestBlokowany(postać2),
                "Two Postać objects can't share a corner");

        postać2 = new ProstaPostać(3, 2);
        plansza.chęćPostawienia(postać2, 1, 1);
        Testing.checkTrue(plansza.jestBlokowany(postać2),
                "Two Postać objects can't run into each other");

        postać2 = new ProstaPostać(3, 2);
        plansza.chęćPostawienia(postać2, 1, 3);
        Testing.checkTrue(plansza.jestBlokowany(postać2),
                "Two Postać objects can't run into each other");

        postać2 = new ProstaPostać(3, 2);
        plansza.chęćPostawienia(postać2, 0, 4);
        Testing.checkFalse(plansza.jestBlokowany(postać2),
                "Two Postać objects can stand next to each other");

        postać2 = new ProstaPostać(3, 2);
        plansza.chęćPostawienia(postać2, 2, 4);
        Testing.checkFalse(plansza.jestBlokowany(postać2),
                "Two Postać objects can stand next to each other");

        ProstaPlansza plansza2 = new ProstaPlansza(6, 5);
        ProstaPostać postać3 = new ProstaPostać(1, 1);
        ProstaPostać postać4 = new ProstaPostać(3, 3);
        plansza2.chęćPostawienia(postać4, 0, 0);
        plansza2.chęćPostawienia(postać3, 1, 1);
        Testing.checkFalse(plansza2.jestBlokowany(postać4),
                "Postać not blocked while board empty");
        Testing.checkFalse(plansza2.jestBlokowany(postać3),
                "Postać not blocked while board empty");
        plansza2.przesuńNaChcianąPozycję(postać3);
        Testing.checkTrue(plansza2.jestBlokowany(postać4),
                "Single square inside Postać blocks it");
    }

    public static void testTworzyCykl() {
        beginTest("tworzyCykl");

        ProstaPlansza plansza = new ProstaPlansza(6, 6);
        ProstaPostać[] postacie = new ProstaPostać[5];

        for (int i = 0; i < 5; i++) {
            postacie[i] = new ProstaPostać(2, 2);
        }

        plansza.chęćPostawienia(postacie[0], 0, 0);
        plansza.chęćPostawienia(postacie[1], 0, 2);
        plansza.chęćPostawienia(postacie[2], 2, 0);
        plansza.chęćPostawienia(postacie[3], 2, 2);
        plansza.chęćPostawienia(postacie[4], 4, 4);

        for (Postać postać : postacie) {
            plansza.przesuńNaChcianąPozycję(postać);
            Testing.checkFalse(plansza.tworzyCykl(postać), 
                    "Before movement, no Postać is in a cycle");
        }

        plansza.chęćPrzesunięcia(postacie[0], Kierunek.PRAWO);
        plansza.chęćPrzesunięcia(postacie[1], Kierunek.DÓŁ);
        plansza.chęćPrzesunięcia(postacie[2], Kierunek.GÓRA);
        Testing.checkFalse(plansza.tworzyCykl(postacie[0]),
                "Postać still not in cycle");
        Testing.checkFalse(plansza.tworzyCykl(postacie[3]),
                "Postać still not in cycle");
        plansza.chęćPrzesunięcia(postacie[3], Kierunek.LEWO);
        for (int i = 0; i < 4; i++) {
            Testing.checkTrue(plansza.tworzyCykl(postacie[i]),
                    "Postać is in cycle");
        }
        Testing.checkFalse(plansza.tworzyCykl(postacie[4]),
                    "Postać is outside of cycle");

        plansza = new ProstaPlansza(2, 1);
        Postać postać1 = new ProstaPostać(1, 1);
        Postać postać2 = new ProstaPostać(1, 1);

        plansza.chęćPostawienia(postać1, 0, 0);
        plansza.chęćPostawienia(postać2, 1, 0);
        plansza.przesuńNaChcianąPozycję(postać1);
        plansza.przesuńNaChcianąPozycję(postać2);
        plansza.chęćPrzesunięcia(postać1, Kierunek.DÓŁ);

        Testing.checkFalse(plansza.tworzyCykl(postać1), "No cycle yet");
        Testing.checkFalse(plansza.tworzyCykl(postać2), "No cycle yet");

        plansza.chęćPrzesunięcia(postać2, Kierunek.GÓRA);
        Testing.checkTrue(plansza.tworzyCykl(postać1), "Cycle created");
        Testing.checkTrue(plansza.tworzyCykl(postać2), "Cycle created");
    }

    public static void main(String[] args) {
        testChęćPostawienia();
        testChęćPrzesunięcia();
        testJestBlokowany();
        testTworzyCykl();
    }
}
