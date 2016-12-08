package sample;

import gra.Postać;
import gra.Plansza;
import gra.Kierunek;

public class PostaćWątekZPostawieniem extends Thread implements Postać {
    private int wysokość;
    private int szerokość;
    private int wiersz;
    private int kolumna;
    private Plansza plansza;

    public PostaćWątekZPostawieniem(int wysokość, int szerokość, int wiersz,
            int kolumna, Plansza plansza) {
        this.wysokość = wysokość;
        this.szerokość = szerokość;
        this.wiersz = wiersz;
        this.kolumna = kolumna;
        this.plansza = plansza;
    }

    public void run() {
        try {
            plansza.postaw(this, wiersz, kolumna);
        } catch (InterruptedException ie) {
        }
    }

    public int dajWysokość() {
        return wysokość;
    }

    public int dajSzerokość() {
        return szerokość;
    }
}

