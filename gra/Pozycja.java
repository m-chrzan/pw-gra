package gra;

public class Pozycja {
    private static final Pozycja pozycjaZaPlanszą = new Pozycja(-1, -1);
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

    public boolean naPlanszy() {
        return wiersz >= 0 && kolumna >= 0;
    }

    public static Pozycja dajPozycjęZaPlanszą() {
        return pozycjaZaPlanszą;
    }
}
