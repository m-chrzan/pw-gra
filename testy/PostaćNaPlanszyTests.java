package testy;

import gra.PostaćNaPlanszy;
import gra.Pozycja;
import sample.ProstaPostać;

public class PostaćNaPlanszyTests {
    public static void beginTest(String name) {
        Testing.beginTest("PostaćNaPlanszy." + name);
    }

    public static void checkContainsAll(PostaćNaPlanszy p, int row,
            int column, int height, int width, String msg) {
        boolean containsAll = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!p.zawiera(new Pozycja(row + i, column + j))) {
                    containsAll = false;
                }
            }
        }

        Testing.checkTrue(containsAll, msg);
    }

    public static void checkContainsNone(PostaćNaPlanszy p, int row,
            int column, int height, int width, String msg) {
        boolean containsNone = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (p.zawiera(new Pozycja(row + i, column + j))) {
                    containsNone = false;
                }
            }
        }

        Testing.checkTrue(containsNone, msg);
    }

    public static void testDajPostać() {
        beginTest("dajPostać");

        ProstaPostać pp1 = new ProstaPostać(4, 2);
        ProstaPostać pp2 = new ProstaPostać(4, 2);

        PostaćNaPlanszy postać = new PostaćNaPlanszy(pp1);

        Testing.checkEqual(postać.dajPostać(), pp1, "Correct Postać reference kept");
        Testing.checkNotEqual(postać.dajPostać(), pp2,
               "PostaćNaPlanszy object refers to a specific Postać object");
    }

    public static void testZawiera() {
        beginTest("zawiera");

        PostaćNaPlanszy postać = new PostaćNaPlanszy(new ProstaPostać(4, 2));
        postać.chcianaPozycja(new Pozycja(1, 1));
        postać.przesuń();

        checkContainsAll(postać, 1, 1, 4, 2,
                "Postać contains all squares inside it");
        checkContainsNone(postać, 0, 0, 1, 4,
                "Postać doesn't contain the squares above it");
        checkContainsNone(postać, 1, 0, 4, 1,
                "Postać doesn't contain the squares to its left");
        checkContainsNone(postać, 5, 0, 1, 4,
                "Postać doesn't contain the squares below it");
        checkContainsNone(postać, 1, 3, 4, 1,
                "Postać doesn't contain the squares to its right");
    }

    public static void main(String[] args) {
        testDajPostać();
        testZawiera();
    }

}
