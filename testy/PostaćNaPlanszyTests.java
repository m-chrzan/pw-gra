package testy;

import gra.PostaćNaPlanszy;
import sample.ProstaPostać;

public class PostaćNaPlanszyTests {
    public static void beginTest(String name) {
        Testing.beginTest("PostaćNaPlanszy." + name);
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

    public static void main(String[] args) {
        testDajPostać();
    }
}
