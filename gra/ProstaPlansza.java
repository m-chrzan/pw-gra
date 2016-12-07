package gra;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

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

    private PostaćNaPlanszy znajdźPostać(Postać postać)
        throws NieZnalezionoPostaci {
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
            return istniejePostaćKtóraBlokuje(postaćNaPlanszy);
        } catch (NieZnalezionoPostaci np) {
            return false;
        }
    }

    private boolean istniejePostaćKtóraBlokuje(PostaćNaPlanszy postać) {
        for (PostaćNaPlanszy innaPostać : postacie) {
            if (!postać.equals(innaPostać) &&
                postać.blokujeszMnie(innaPostać)) {
                return true;
            }
        }

        return false;
    }

    public boolean tworzyCykl(Postać postać) {
        try {
            PostaćNaPlanszy postaćNaPlanszy = znajdźPostać(postać);
            return sprawdźCzyRozpoczynaCykl(postaćNaPlanszy);
        } catch (NieZnalezionoPostaci np) {
            return false;
        }
    }

    private boolean sprawdźCzyRozpoczynaCykl(PostaćNaPlanszy źródło) {
        Stack<PostaćNaPlanszy> stos = new Stack<PostaćNaPlanszy>();
        Map<PostaćNaPlanszy, Boolean> odwiedzone =
            new HashMap<PostaćNaPlanszy, Boolean>();

        for (PostaćNaPlanszy postać : postacie) {
            odwiedzone.put(postać, false);
        }

        dodajNieodwiedzonychSąsiadówNaStos(stos, odwiedzone, źródło);
        return jestCyklDoŹródła(stos, odwiedzone, źródło);
    }

    private boolean jestCyklDoŹródła(Stack<PostaćNaPlanszy> stos,
            Map<PostaćNaPlanszy, Boolean> odwiedzone,
            PostaćNaPlanszy źródło) {
        if (stos.empty()) {
            return false;
        }

        PostaćNaPlanszy aktualny = stos.pop();
        odwiedzone.put(aktualny, true);

        if (aktualny.equals(źródło)) {
            return true;
        } else {
            dodajNieodwiedzonychSąsiadówNaStos(stos, odwiedzone, aktualny);
            return jestCyklDoŹródła(stos, odwiedzone, źródło);
        }
    }

    private void dodajNieodwiedzonychSąsiadówNaStos(Stack<PostaćNaPlanszy> stos,
            Map<PostaćNaPlanszy, Boolean> odwiedzone, PostaćNaPlanszy aktualny) {
        for (PostaćNaPlanszy postać : postacie) {
            if (!odwiedzone.get(postać) && krawędźMiędzy(aktualny, postać)) {
                stos.push(postać);
            }
        }
    }

    private boolean krawędźMiędzy(PostaćNaPlanszy postać1,
            PostaćNaPlanszy postać2) {
        if (postać1.equals(postać2)) {
            return false;
        } else if (postać1.blokujeszMnie(postać2)) {
            return true;
        } else {
            return false;
        }
    }

    public void usuń(Postać postać) {
        try {
            PostaćNaPlanszy postaćNaPlanszy = znajdźPostać(postać);
            postacie.remove(postaćNaPlanszy);
        } catch (NieZnalezionoPostaci np) {
            throw new IllegalArgumentException();
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
