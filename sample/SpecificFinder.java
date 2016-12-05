package sample;

import gra.Postać;

public class SpecificFinder extends Finder {
    private Postać postaćToFind;

    public SpecificFinder(Postać postać) {
        postaćToFind = postać;
    }

    public void wykonaj(Postać postać) {
        found = postaćToFind.equals(postać);
    }
}
