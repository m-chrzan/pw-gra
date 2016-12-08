package testy;

import gra.MojaPlansza;
import gra.Plansza;
import gra.Postać;
import gra.Kierunek;
import gra.DeadlockException;
import sample.ProstaPostać;
import sample.Finder;
import sample.SpecificFinder;
import sample.PostaćWątekZRuchem;
import sample.PostaćWątekZPostawieniem;
import sample.DeadlockDetectedException;

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

        final Plansza plansza = new MojaPlansza(5, 6);
        checkEmpty(plansza, 0, 0, 5, 6, "MojaPlansza is empty at first");

        final Postać postać0 = new ProstaPostać(6, 6);

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                try {
                    plansza.postaw(postać0, 0, 0);
                } catch (InterruptedException ie) {
                }
            }
        }, new IllegalArgumentException(),
        "Postać has to fit on the board");

        final Postać postać1 = new ProstaPostać(2, 2);

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

        final Postać postać2 = new ProstaPostać(2, 2);

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

        final Plansza plansza = new MojaPlansza(5, 6);

        final Postać postać1 = new ProstaPostać(2, 2);

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                try {
                    plansza.przesuń(postać1, Kierunek.DÓŁ);
                } catch (InterruptedException ie) {
                } catch (DeadlockException de) {
                }
            }
        }, new IllegalArgumentException(),
        "Can't move Postać that's not on board");


        try {
            plansza.postaw(postać1, 0, 0);
        } catch (Exception e) {
        }

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                try {
                    plansza.przesuń(postać1, Kierunek.LEWO);
                } catch (InterruptedException ie) {
                } catch (DeadlockException de) {
                }
            }
        }, new IllegalArgumentException(),
        "Can't move Postać off the board");

        try {
            plansza.przesuń(postać1, Kierunek.PRAWO);
        } catch (Exception e) {
        }


        checkIsSpecificPostać(plansza, postać1, 0, 1, 2, 2, "Postać moved right");
        checkEmpty(plansza, 0, 0, 2, 0,
                "Area previously occupied by Postać empty");

        final MojaPlansza plansza2 = new MojaPlansza(5, 6);

        final PostaćWątekZRuchem[] postacie = new PostaćWątekZRuchem[4];

        postacie[0] = new PostaćWątekZRuchem(1, 1, plansza2, Kierunek.PRAWO);
        postacie[1] = new PostaćWątekZRuchem(1, 1, plansza2, Kierunek.GÓRA);
        postacie[2] = new PostaćWątekZRuchem(1, 1, plansza2, Kierunek.DÓŁ);
        postacie[3] = new PostaćWątekZRuchem(1, 1, plansza2, Kierunek.LEWO);

        try {
            plansza2.postaw(postacie[0], 0, 0);
            plansza2.postaw(postacie[1], 1, 0);
            plansza2.postaw(postacie[2], 0, 1);
            plansza2.postaw(postacie[3], 1, 1);
        } catch (Exception e) {
        }

        for (Thread postać : postacie) {
            postać.setDaemon(true);
        }

        postacie[0].start();
        postacie[1].start();
        postacie[2].start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
        }

        checkIsSpecificPostać(plansza2, postacie[0], 0, 0, 1, 1,
                "First postać hasn't moved yet");
        checkIsSpecificPostać(plansza2, postacie[1], 1, 0, 1, 1,
                "Second postać hasn't moved yet");
        checkIsSpecificPostać(plansza2, postacie[2], 0, 1, 1, 1,
                "Third postać hasn't moved yet");
        checkIsSpecificPostać(plansza2, postacie[3], 1, 1, 1, 1,
                "Fourth postać hasn't moved yet");

        Testing.checkEqual(postacie[0].getState(), Thread.State.WAITING,
                "Postać tried to move, now waiting");
        Testing.checkEqual(postacie[1].getState(), Thread.State.WAITING,
                "Postać tried to move, now waiting");
        Testing.checkEqual(postacie[2].getState(), Thread.State.WAITING,
                "Postać tried to move, now waiting");

        Testing.checkExceptionThrown(new Runnable() {
            public void run() {
                try {
                    plansza2.przesuń(postacie[3], Kierunek.LEWO);
                } catch (InterruptedException ie) {
                } catch (DeadlockException de) {
                    throw new DeadlockDetectedException();
                }
            }
        }, new DeadlockDetectedException(), "Move creates deadlock");

        Plansza plansza3 = new MojaPlansza(2, 3);
        PostaćWątekZRuchem postać2 = new PostaćWątekZRuchem(1, 1, plansza3,
                Kierunek.PRAWO);
        PostaćWątekZPostawieniem postać3 = new PostaćWątekZPostawieniem(1, 1, 0, 1,
                plansza3);
        PostaćWątekZRuchem postać4 = new PostaćWątekZRuchem(1, 1, plansza3,
                Kierunek.GÓRA);

        try {
            plansza3.postaw(postać2, 0, 1);
            Thread.sleep(100);
        } catch (InterruptedException ie) {
        }

        postać3.start();

        checkIsSpecificPostać(plansza3, postać2, 0, 1, 1, 1,
                "Postać hasn't moved yet, other Postać blocked");

        postać2.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
        }

        checkIsSpecificPostać(plansza3, postać2, 0, 2, 1, 1,
                "Postać has moved");
        checkIsSpecificPostać(plansza3, postać3, 0, 1, 1, 1,
                "Postać placed in free spot");

        try {
            plansza3.postaw(postać4, 1, 1);
        } catch (InterruptedException ie) {
        }

        postać4.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
        }

        checkIsSpecificPostać(plansza3, postać3, 0, 1, 1, 1,
                "Postać hasn't moved yet");
        checkIsSpecificPostać(plansza3, postać4, 1, 1, 1, 1,
                "Postać blocked");

        try {
            plansza3.przesuń(postać3, Kierunek.LEWO);
        } catch (Exception e) {
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
        }

        checkIsSpecificPostać(plansza3, postać3, 0, 0, 1, 1,
                "Postać has moved");
        checkIsSpecificPostać(plansza3, postać4, 0, 1, 1, 1,
                "Postać has moved into freed spot");
    }

    public static void testUsuń() {
        beginTest("usuń");
        final Plansza plansza = new MojaPlansza(5, 6);

        final Postać postać1 = new ProstaPostać(2, 2);
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

        final Plansza plansza2 = new MojaPlansza(1, 1);
        final Postać postać3 = new ProstaPostać(1, 1);
        final PostaćWątekZPostawieniem postać4 =
            new PostaćWątekZPostawieniem(1, 1, 0, 0, plansza2);

        try {
            plansza2.postaw(postać3, 0, 0);
        } catch (InterruptedException ie) {
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
        }

        postać4.start();

        checkIsSpecificPostać(plansza2, postać3, 0, 0, 1, 1,
                "New Postać not placed");

        plansza2.usuń(postać3);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
        }

        checkIsSpecificPostać(plansza2, postać4, 0, 0, 1, 1,
                "New Postać placed in freed spot");
    }

    public static void main(String[] args) {
        testPostaw();
        testPrzesuń();
        testUsuń();
    }
}
