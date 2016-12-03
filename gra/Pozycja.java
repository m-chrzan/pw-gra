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
}
