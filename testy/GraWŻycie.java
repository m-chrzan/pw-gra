package testy;

import gra.Plansza;
import gra.MojaPlansza;
import java.util.concurrent.CyclicBarrier;
import sample.KontrolerKomórki;
import sample.Finder;

public class GraWŻycie {
    private int wysokość;
    private int szerokość;
    private KontrolerKomórki[][] komórki;
    private Plansza plansza;
    private CyclicBarrier bariera;
    private boolean barieraGotowa = true;

    public GraWŻycie(int wysokość, int szerokość, boolean[][] stany) {
        this.wysokość = wysokość;
        this.szerokość = szerokość;
        komórki = new KontrolerKomórki[wysokość + 2][szerokość + 2];
        plansza = new MojaPlansza(wysokość + 2, szerokość + 2);
        bariera = new CyclicBarrier(wysokość * szerokość, new Runnable() {
            public void run() {
                if (barieraGotowa()) {
                    for (int wiersz = 1; wiersz <= wysokość; wiersz++) {
                        for (int kolumna = 1; kolumna <= szerokość; kolumna++) {
                            Finder znajdywacz = new Finder();
                            plansza.sprawdź(wiersz, kolumna, znajdywacz, znajdywacz);
                            if (znajdywacz.foundPostać()) {
                                System.out.print("#");
                            } else {
                                System.out.print(".");
                            }
                        }
                        System.out.println();
                    }

                    System.out.println();

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ie) {
                    }
                }
            }
        });

        for (int wiersz = 1; wiersz <= wysokość; wiersz++) {
            for (int kolumna = 1; kolumna <= szerokość; kolumna++) {
                komórki[wiersz][kolumna] = new KontrolerKomórki(wiersz, kolumna,
                        plansza, stany[wiersz - 1][kolumna - 1], bariera);
                komórki[wiersz][kolumna].start();
            }
        }
    }

    private boolean barieraGotowa() {
        barieraGotowa = !barieraGotowa;

        return barieraGotowa;
    }

    public static void main(String[] args) {
        // http://www.conwaylife.com/wiki/Queen_bee_shuttle
        boolean[][] królowaPszczół = {
            { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
            { false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false },
            { false, false, false, false, false, false, false, false, true,  false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false },
            { false, true,  true,  false, false, false, false, true,  false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  true,  false },
            { false, true,  true,  false, false, false, true,  false, false, true,  false, false, false, false, false, false, false, false, false, false, false, true,  true,  false },
            { false, false, false, false, false, false, false, true,  false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false, false },
            { false, false, false, false, false, false, false, false, true,  false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false },
            { false, false, false, false, false, false, false, false, false, false, true,  false, false, false, false, false, false, false, false, false, false, false, false, false },
            { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false }
        };

        GraWŻycie gra = new GraWŻycie(9, 24, królowaPszczół);
    }
}
