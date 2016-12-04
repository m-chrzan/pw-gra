package testy;

import gra.PostaćNaPlanszy;
import gra.Pozycja;
import gra.Kierunek;
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

    public static void testBlokujeszMnie() {
        beginTest("blokujeszMnie");

        PostaćNaPlanszy postać1 = new PostaćNaPlanszy(new ProstaPostać(4, 2));
        postać1.chcianaPozycja(new Pozycja(2, 2));
        postać1.przesuń();

        PostaćNaPlanszy postać2 = new PostaćNaPlanszy(new ProstaPostać(2, 2));

        postać2.chcianaPozycja(new Pozycja(0, 0));
        Testing.checkFalse(postać2.blokujeszMnie(postać1),
                "Two Postać objects can have neighboring corners");

        postać2.chcianaPozycja(new Pozycja(0, 4));
        Testing.checkFalse(postać2.blokujeszMnie(postać1),
                "Two Postać objects can have neighboring corners");

        postać2.chcianaPozycja(new Pozycja(0, 2));
        Testing.checkFalse(postać2.blokujeszMnie(postać1),
                "Two Postać objects can have a neighboring edge");

        postać2.chcianaPozycja(new Pozycja(2, 0));
        Testing.checkFalse(postać2.blokujeszMnie(postać1),
                "Two Postać objects can have a neighboring edge");

        postać2.chcianaPozycja(new Pozycja(3, 4));
        Testing.checkFalse(postać2.blokujeszMnie(postać1),
                "Two Postać objects can have a neighboring edge");

        postać2.chcianaPozycja(new Pozycja(100, 24));
        Testing.checkFalse(postać2.blokujeszMnie(postać1),
                "Two Postać objects can be far from each other");

        postać2.chcianaPozycja(new Pozycja(1, 1));
        Testing.checkTrue(postać2.blokujeszMnie(postać1),
                "Corners of Postać objects can't intersect");

        postać2.chcianaPozycja(new Pozycja(5, 3));
        Testing.checkTrue(postać2.blokujeszMnie(postać1),
                "Corners of Postać objects can't intersect");

        postać2.chcianaPozycja(new Pozycja(1, 2));
        Testing.checkTrue(postać2.blokujeszMnie(postać1),
                "Postać objects can't partially intersect");

        postać2.chcianaPozycja(new Pozycja(3, 2));
        Testing.checkTrue(postać2.blokujeszMnie(postać1),
                "One Postać can't be entirely on top of another");

        PostaćNaPlanszy postać3 = new PostaćNaPlanszy(new ProstaPostać(6, 5));
        postać3.chcianaPozycja(new Pozycja(0, 0));
        Testing.checkTrue(postać3.blokujeszMnie(postać1),
                "Postać can't contain another Postać");

        Testing.checkFalse(postać2.blokujeszMnie(postać3),
                "A Postać that's not on the board doesn't block anyone");

        postać3.przesuń();
        Testing.checkFalse(postać1.blokujeszMnie(postać3),
                "A Postać that doesn't want to move is not blocked by anyone");
    }

    public static void testBlokujeszIBędzieszMnieBlokował() {
        beginTest("blokujeszIBędzieszMnieBlokował");

        PostaćNaPlanszy postać1 = new PostaćNaPlanszy(new ProstaPostać(2, 2));
        postać1.chcianaPozycja(new Pozycja(1, 1));
        postać1.przesuń();

        PostaćNaPlanszy postać2 = new PostaćNaPlanszy(new ProstaPostać(2, 2));
        postać2.chcianaPozycja(new Pozycja(1, 1));

        Testing.checkFalse(postać1.blokujeszIBędzieszMnieBlokował(postać2),
                "Postać not on the board yet won't be blocking anyone");
        Testing.checkFalse(postać2.blokujeszIBędzieszMnieBlokował(postać1),
                "Postać not on the board yet won't be blocked by anyone");

        postać2.chcianaPozycja(new Pozycja(1, 3));
        postać2.przesuń();

        Testing.checkFalse(postać1.blokujeszIBędzieszMnieBlokował(postać2),
                "Postać objects not wanting to move won't block each other");
        Testing.checkFalse(postać2.blokujeszIBędzieszMnieBlokował(postać1),
                "Postać objects not wanting to move won't block each other");

        postać1.chcianyKierunek(Kierunek.PRAWO);
        Testing.checkFalse(postać1.blokujeszIBędzieszMnieBlokował(postać2),
                "Postać not wanting to move is not considered to be blocking after its potential move");
        Testing.checkFalse(postać2.blokujeszIBędzieszMnieBlokował(postać1),
                "Postać not wanting to move is not considered to have its potential move blocked by anyone");

        postać2.chcianyKierunek(Kierunek.PRAWO);
        Testing.checkFalse(postać1.blokujeszIBędzieszMnieBlokował(postać2),
                "Postać objects wanting to move in the same direction won't be blocking each other");

        postać2.chcianyKierunek(Kierunek.LEWO);
        Testing.checkTrue(postać1.blokujeszIBędzieszMnieBlokował(postać2),
                "Postać objects trying to move into each other's space will be blocking each other");

        Testing.checkTrue(postać2.blokujeszIBędzieszMnieBlokował(postać1),
                "Postać objects trying to move into each other's space will be blocking each other");

        postać1.chcianyKierunek(Kierunek.LEWO);
        postać2.chcianyKierunek(Kierunek.PRAWO);
        Testing.checkFalse(postać1.blokujeszIBędzieszMnieBlokował(postać2),
                "Postać objects moving away from each other won't be blocking each other.");
        Testing.checkFalse(postać2.blokujeszIBędzieszMnieBlokował(postać1),
                "Postać objects moving away from each other won't be blocking each other.");

        postać2.chcianaPozycja(new Pozycja(3, 2));
        postać2.przesuń();
        postać2.chcianyKierunek(Kierunek.PRAWO);
        postać1.chcianyKierunek(Kierunek.DÓŁ);
        Testing.checkFalse(postać1.blokujeszIBędzieszMnieBlokował(postać2),
                "Postać that will move out of another's way won't be blocking it");

        postać2.chcianyKierunek(Kierunek.LEWO);
        Testing.checkTrue(postać1.blokujeszIBędzieszMnieBlokował(postać2),
                "Postać that stays in another's way keeps blocking it");
    }

    public static void main(String[] args) {
        testDajPostać();
        testZawiera();
        testBlokujeszMnie();
        testBlokujeszIBędzieszMnieBlokował();
    }

}
