package gra;

public class Pozycja {
    private int wiersz;
    private int kolumna;

    public Pozycja(int wiersz, int kolumna) {
        this.wiersz = wiersz;
        this.kolumna = kolumna;
    }

    public int wiersz() {
        return wiersz;
    }

    public int kolumna() {
        return kolumna;
    }

    public Pozycja przesuń(Kierunek kierunek) {
        int nowyWiersz = wiersz;
        int nowaKolumna = kolumna;
        switch (kierunek) {
            case GÓRA:
                nowyWiersz--;
                break;
            case DÓŁ:
                nowyWiersz++;
                break;
            case LEWO:
                nowaKolumna--;
                break;
            case PRAWO:
                nowaKolumna++;
                break;
        }

        return new Pozycja(nowyWiersz, nowaKolumna);
    }

    public boolean equals(Object otherObject) {
        Pozycja other = (Pozycja) otherObject;
        return this.wiersz == other.wiersz && this.kolumna == other.kolumna;
    }
}
