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
        if (wiersz < 0 || kolumna < 0 ||
            wiersz + postać.dajWysokość() > wysokość ||
            kolumna + postać.dajSzerokość() > szerokość) {
            throw new IllegalArgumentException();
        }
        PostaćNaPlanszy postaćNaPlanszy = new PostaćNaPlanszy(postać);
        postaćNaPlanszy.chcianaPozycja(new Pozycja(wiersz, kolumna));
        postacie.add(postaćNaPlanszy);
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
