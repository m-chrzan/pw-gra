package gra;

public class MojaPlansza implements Plansza {
    private ProstaPlansza plansza;

    public MojaPlansza(int wysokość, int szerokość) {
        plansza = new ProstaPlansza(wysokość, szerokość);
    }

    synchronized public void postaw(Postać postać, int wiersz, int kolumna)
            throws InterruptedException {
        plansza.chęćPostawienia(postać, wiersz, kolumna);

        czekajAżNieblokowany(postać);

        plansza.przesuńNaChcianąPozycję(postać);
    }

    private void czekajAżNieblokowany(Postać postać)
        throws InterruptedException {
        while (plansza.jestBlokowany(postać)) {
            wait();
        }
    }

    synchronized public void przesuń(Postać postać, Kierunek kierunek)
            throws InterruptedException, DeadlockException {
        plansza.chęćPrzesunięcia(postać, kierunek);

        rzućJeśliDeadlock(postać);

        czekajAżNieblokowany(postać);

        plansza.przesuńNaChcianąPozycję(postać);
    }

    private void rzućJeśliDeadlock(Postać postać) throws DeadlockException {
        if (plansza.tworzyCykl(postać)) {
            throw new DeadlockException();
        }
    }

    synchronized public void usuń(Postać postać) {
        plansza.usuń(postać);
    }

    synchronized public void sprawdź(int wiersz, int kolumna,
            Akcja jeśliZajęte, Runnable jeśliWolne) {
        plansza.sprawdź(wiersz, kolumna, jeśliZajęte, jeśliWolne);
    }
}
