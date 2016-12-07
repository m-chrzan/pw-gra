package gra;

public class PostaćNaPlanszy {
    private Postać postać;
    private Pozycja pozycja;
    private Pozycja chcianaPozycja;

    public PostaćNaPlanszy(Postać postać) {
        this.postać = postać;
        this.pozycja = Pozycja.dajPozycjęZaPlanszą();
        this.chcianaPozycja = Pozycja.dajPozycjęZaPlanszą();
    }

    public void chcianaPozycja(Pozycja pozycja) {
        chcianaPozycja = pozycja;
    }

    public void chcianyKierunek(Kierunek kierunek) {
        chcianaPozycja = pozycja.przesuń(kierunek);
    }

    public void przesuń() {
        pozycja = chcianaPozycja;
        chcianaPozycja = Pozycja.dajPozycjęZaPlanszą();
    }

    /* Odpowiada na pytanie: czy [postać] blokuje powierzchnię, która jest
     * chciana przez [this]?
     */
    public boolean blokujeszMnie(PostaćNaPlanszy postać) {
        if (!chcianaPozycja.naPlanszy() || !postać.pozycja.naPlanszy()) {
            return false;
        } else {
            return this.chcianyPrawyDolny().wiersz() >= postać.lewyGórny().wiersz() &&
                   postać.prawyDolny().wiersz() >= this.chcianyLewyGórny().wiersz() &&
                   this.chcianyPrawyDolny().kolumna() >= postać.lewyGórny().kolumna() &&
                   postać.prawyDolny().kolumna() >= this.chcianyLewyGórny().kolumna();
        }
    }

    public Postać dajPostać() {
        return postać;
    }

    public boolean zawiera(Pozycja pozycja) {
        return pozycja.wiersz() >= lewyGórny().wiersz() &&
               pozycja.wiersz() <= prawyDolny().wiersz() &&
               pozycja.kolumna() >= lewyGórny().kolumna() &&
               pozycja.kolumna() <= prawyDolny().kolumna();
    }

    public Pozycja lewyGórny() {
        return pozycja;
    }

    public Pozycja prawyDolny() {
        return new Pozycja(pozycja.wiersz() + postać.dajWysokość() - 1,
                           pozycja.kolumna() + postać.dajSzerokość() - 1);
    }

    public Pozycja chcianyLewyGórny() {
        return chcianaPozycja;
    }

    public Pozycja chcianyPrawyDolny() {
        return new Pozycja(chcianaPozycja.wiersz() + postać.dajWysokość() - 1,
                           chcianaPozycja.kolumna() + postać.dajSzerokość() - 1);
    }
}
