package sample;

import gra.Plansza;
import gra.Postać;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class KontrolerKomórki extends Thread {
    private Postać mojaPostać;
    private int iluSąsiadów;
    private boolean żyję;
    private Plansza plansza;
    private int wiersz;
    private int kolumna;
    private CyclicBarrier bariera;

    public KontrolerKomórki(int wiersz, int kolumna, Plansza plansza,
            boolean stanPoczątkowy, CyclicBarrier bariera) {
        mojaPostać = new ProstaPostać(1, 1);
        this.wiersz = wiersz;
        this.kolumna = kolumna;
        this.plansza = plansza;
        this.bariera = bariera;
        this.żyję = stanPoczątkowy;
    }

    public void run() {
        if (żyję) {
            try {
                plansza.postaw(mojaPostać, wiersz, kolumna);
            } catch (InterruptedException ie) {
            }
        }

        while (true) {
            try {
                bariera.await();
            } catch (BrokenBarrierException e) {
            } catch (InterruptedException ie) {
            }

            policzSąsiadów();

            try {
                bariera.await();
            } catch (BrokenBarrierException e) {
            } catch (InterruptedException ie) {
            }

            aktualizujStan();
        }
    }

    private void policzSąsiadów() {
        iluSąsiadów = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    Finder znajdywacz = new Finder();
                    plansza.sprawdź(wiersz + i, kolumna + j, znajdywacz,
                            znajdywacz);
                    if (znajdywacz.foundPostać()) {
                        iluSąsiadów++;
                    }
                }
            }
        }
    }

    private void aktualizujStan() {
        if (żyję && powinienemUmrzeć()) {
            plansza.usuń(mojaPostać);
            żyję = false;
        } else if (!żyję && powinienemSięUrodzić()) {
            try {
                plansza.postaw(mojaPostać, wiersz, kolumna);
                żyję = true;
            } catch (InterruptedException ie) {
            }
        }
    }

    private boolean powinienemUmrzeć() {
        return iluSąsiadów < 2 || iluSąsiadów > 3;
    }

    private boolean powinienemSięUrodzić() {
        return iluSąsiadów == 3;
    }
}
