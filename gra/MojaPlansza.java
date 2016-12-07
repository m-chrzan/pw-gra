package gra;

public class MojaPlansza implements Plansza {
    private ProstaPlansza plansza;

    public MojaPlansza(int wysokość, int szerokość) {
        plansza = new ProstaPlansza(wysokość, szerokość);
    }

    synchronized public void postaw(Postać postać, int wiersz, int kolumna)
            throws InterruptedException {
        plansza.chęćPostawienia(postać, wiersz, kolumna);

        czekajAżMożnaPostawić(postać);

        plansza.przesuńNaChcianąPozycję(postać);
    }

    private void czekajAżMożnaPostawić(Postać postać) throws InterruptedException {
        while (plansza.jestBlokowany(postać)) {
            wait();
        }
    }

    public void przesuń(Postać postać, Kierunek kierunek)
            throws InterruptedException, DeadlockException {
    }

    public void usuń(Postać postać) {
    }

    public void sprawdź(int wiersz, int kolumna,
            Akcja jeśliZajęte, Runnable jeśliWolne) {
        plansza.sprawdź(wiersz, kolumna, jeśliZajęte, jeśliWolne);
    }
}
