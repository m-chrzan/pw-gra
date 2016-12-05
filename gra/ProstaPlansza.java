package gra;

import java.util.List;
import java.util.ArrayList;

public class ProstaPlansza {
    private List<PostaćNaPlanszy> postacie;
    private int wysokość;
    private int szerokość;

    public ProstaPlansza(int wysokość, int szerokość) {
        postacie = new ArrayList<PostaćNaPlanszy>();
        this.wysokość = wysokość;
        this.szerokość = szerokość;
    }

    public void chęćPostawienia(Postać postać, int wiersz, int kolumna) {
        PostaćNaPlanszy postaćNaPlanszy = new PostaćNaPlanszy(postać);
        postaćNaPlanszy.chcianaPozycja(new Pozycja(wiersz, kolumna));
        if (!zmieściSięNaPlanszy(postaćNaPlanszy)) {
            throw new IllegalArgumentException();
        }
        postacie.add(postaćNaPlanszy);
    }

    private boolean zmieściSięNaPlanszy(PostaćNaPlanszy postać) {
        return pozycjaNaPlanszy(postać.chcianyLewyGórny()) &&
               pozycjaNaPlanszy(postać.chcianyPrawyDolny());
    }

    private boolean pozycjaNaPlanszy(Pozycja pozycja) {
        return pozycja.wiersz() >= 0 && pozycja.wiersz() < wysokość &&
               pozycja.kolumna() >= 0 && pozycja.kolumna() < szerokość;
    }

    public void chęćPrzesunięcia(Postać postać, Kierunek kierunek) {
        try {
            PostaćNaPlanszy postaćNaPlanszy = znajdźPostać(postać);
            postaćNaPlanszy.chcianyKierunek(kierunek);
            if (!zmieściSięNaPlanszy(postaćNaPlanszy)) {
                throw new IllegalArgumentException();
            }
        } catch (NieZnalezionoPostaci np) {
            throw new IllegalArgumentException();
        }
    }

    public void przesuńNaChcianąPozycję(Postać postać) {
        try {
            PostaćNaPlanszy postaćNaPlanszy = znajdźPostać(postać);
            postaćNaPlanszy.przesuń();
        } catch (NieZnalezionoPostaci np) {
            throw new IllegalArgumentException();
        }
    }

    private PostaćNaPlanszy znajdźPostać(Postać postać) throws NieZnalezionoPostaci {
        for (PostaćNaPlanszy postaćNaPlanszy : postacie) {
            if (postaćNaPlanszy.dajPostać().equals(postać)) {
                return postaćNaPlanszy;
            }
        }

        throw new NieZnalezionoPostaci();
    }

    private PostaćNaPlanszy znajdźPostać(Pozycja pozycja) throws NieZnalezionoPostaci {
        for (PostaćNaPlanszy postaćNaPlanszy : postacie) {
            if (postaćNaPlanszy.zawiera(pozycja)) {
                return postaćNaPlanszy;
            }
        }

        throw new NieZnalezionoPostaci();
    }

    public boolean jestBlokowany(Postać postać) {
        try {
            PostaćNaPlanszy postaćNaPlanszy = znajdźPostać(postać);
            for (PostaćNaPlanszy innaPostać : postacie) {
                if (!postaćNaPlanszy.equals(innaPostać) &&
                    postaćNaPlanszy.blokujeszMnie(innaPostać)) {
                    return true;
                }
            }

            return false;
        } catch (NieZnalezionoPostaci np) {
            return false;
        }
    }

    public void sprawdź(int wiersz, int kolumna, Akcja jeśliZajęte,
            Runnable jeśliWolne) {
        try {
            PostaćNaPlanszy postać = znajdźPostać(new Pozycja(wiersz, kolumna));
            jeśliZajęte.wykonaj(postać.dajPostać());
        } catch (NieZnalezionoPostaci np) {
            jeśliWolne.run();
        }
    }

    private static class NieZnalezionoPostaci extends Exception {
    }
}
