package sample;

import gra.Postać;
import gra.Plansza;
import gra.Kierunek;
import gra.DeadlockException;

public class PostaćWątekZRuchem extends Thread implements Postać {
    private int wysokość;
    private int szerokość;
    private Plansza plansza;
    private Kierunek kierunek;

    public PostaćWątekZRuchem(int wysokość, int szerokość, Plansza plansza,
            Kierunek kierunek) {
        this.wysokość = wysokość;
        this.szerokość = szerokość;
        this.plansza = plansza;
        this.kierunek = kierunek;
    }

    public void run() {
        try {
            plansza.przesuń(this, kierunek);
        } catch (InterruptedException ie) {
        } catch (DeadlockException de) {
            throw new DeadlockDetectedException();
        }
    }

    public int dajWysokość() {
        return wysokość;
    }

    public int dajSzerokość() {
        return szerokość;
    }
}
