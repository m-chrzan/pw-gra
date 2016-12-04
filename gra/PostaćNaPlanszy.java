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

    public void przesuń() {
        pozycja = chcianaPozycja;
        chcianaPozycja = Pozycja.dajPozycjęZaPlanszą();
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

    private Pozycja lewyGórny() {
        return pozycja;
    }

    private Pozycja prawyDolny() {
        return new Pozycja(pozycja.wiersz() + postać.dajWysokość() - 1,
                           pozycja.kolumna() + postać.dajSzerokość() - 1);
    }

    private Pozycja chcianyLewyGórny() {
        return chcianaPozycja;
    }

    private Pozycja chcianyPrawyDolny() {
        return new Pozycja(chcianaPozycja.wiersz() + postać.dajWysokość(),
                           chcianaPozycja.kolumna() + postać.dajSzerokość());
    }
}
