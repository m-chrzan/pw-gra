package testy;

import gra.ProstaPlansza;
import gra.Postać;
import gra.Akcja;
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
    }

    public static void main(String[] args) {
        testChęćPostawienia();
    }

}
