package testy;

import gra.MojaPlansza;
import gra.Plansza;
import gra.Postać;
import sample.ProstaPostać;
import sample.Finder;
import sample.SpecificFinder;

public class MojaPlanszaTests {
    public static void beginTest(String name) {
        Testing.beginTest("MojaPlansza." + name);
    }

    public static void checkEmpty(Plansza plansza, int row, int column,
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

    public static void checkFilled(Plansza plansza, int row, int column,
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

    public static void checkIsSpecificPostać(Plansza plansza,
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

    public static void testPostaw() {
        beginTest("postaw");

        Plansza plansza = new MojaPlansza(5, 6);
        checkEmpty(plansza, 0, 0, 5, 6, "MojaPlansza is empty at first");

        Postać postać0 = new ProstaPostać(6, 6);

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                try {
                    plansza.postaw(postać0, 0, 0);
                } catch (InterruptedException ie) {
                }
            }
        }, new IllegalArgumentException(),
        "Postać has to fit on the board");

        Postać postać1 = new ProstaPostać(2, 2);

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                try {
                    plansza.postaw(postać1, -1, 0);
                } catch (InterruptedException ie) {
                }
            }
        }, new IllegalArgumentException(),
        "Postać has to fit on the board");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                try {
                    plansza.postaw(postać1, 4, 5);
                } catch (InterruptedException ie) {
                }
            }
        }, new IllegalArgumentException(),
        "Postać has to fit on the board");

        try {
            plansza.postaw(postać1, 0, 0);
        } catch (InterruptedException ie) {
        }

        checkIsSpecificPostać(plansza, postać1, 0, 0, 2, 2,
                "Placed Postać correctly");
        checkEmpty(plansza, 2, 0, 3, 6, "Rest of board still empty (1/2)");
        checkEmpty(plansza, 0, 2, 2, 4, "Rest of board still empty (2/2)");

        Postać postać2 = new ProstaPostać(2, 2);

        Thread stawianie = new Thread(new Runnable() {
            public void run() {
                try {
                    plansza.postaw(postać2, 0, 1);
                } catch (InterruptedException ie) {
                }
            }
        });
        stawianie.setDaemon(true);
        stawianie.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
        }

        Testing.checkEqual(stawianie.getState(), Thread.State.WAITING,
                "Postać that doesn't fit is blocked");
        checkIsSpecificPostać(plansza, postać1, 0, 0, 2, 2,
                "Original Postać still on board");
        checkEmpty(plansza, 2, 0, 3, 6, "Rest of board still empty (1/2)");
        checkEmpty(plansza, 0, 2, 2, 4, "Rest of board still empty (2/2)");

        Postać postać3 = new ProstaPostać(2, 2);
        try {
            plansza.postaw(postać3, 0, 2);
        } catch (InterruptedException ie) {
        }
        checkIsSpecificPostać(plansza, postać3, 0, 2, 2, 2,
                "New Postać placed on board");
        checkIsSpecificPostać(plansza, postać1, 0, 0, 2, 2,
                "Original Postać still on board");
        checkEmpty(plansza, 2, 0, 3, 6, "Rest of board still empty (1/2)");
        checkEmpty(plansza, 0, 4, 2, 2, "Rest of board still empty (2/2)");
    }

    public static void testPrzesuń() {
        beginTest("przesuń");
    }

    public static void testUsuń() {
        beginTest("usuń");
        Plansza plansza = new MojaPlansza(5, 6);

        Postać postać1 = new ProstaPostać(2, 2);
        Postać postać2 = new ProstaPostać(2, 2);

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                plansza.usuń(postać1);
            }
        }, new IllegalArgumentException(),
        "Can't remove Postać that's not on board");

        try {
            plansza.postaw(postać1, 0, 0);
            plansza.postaw(postać2, 2, 0);
        } catch (InterruptedException ie) {
        }

        checkIsSpecificPostać(plansza, postać1, 0, 0, 2, 2,
                "First Postać is on board");
        checkIsSpecificPostać(plansza, postać2, 2, 0, 2, 2,
                "Second Postać is on board");

        plansza.usuń(postać2);

        checkIsSpecificPostać(plansza, postać1, 0, 0, 2, 2,
                "Original Postać still on board");
        checkEmpty(plansza, 2, 0, 3, 6, "Rest of board empty (1/2)");
        checkEmpty(plansza, 0, 2, 2, 4, "Rest of board empty (2/2)");
    }

    public static void testSprawdź() {
        beginTest("sprawdź");
    }

    public static void main(String[] args) {
        testPostaw();
        testPrzesuń();
        testUsuń();
        testSprawdź();
    }
}
