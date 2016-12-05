package sample;

import gra.Akcja;
import gra.Postać;

public class Finder implements Akcja, Runnable {
    protected boolean found = false;

    public void run() {
        found = false;
    }

    public void wykonaj(Postać postać) {
        found = true;
    }

    public boolean foundPostać() {
        return found;
    }
}
