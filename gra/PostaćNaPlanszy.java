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
        return pozycja.wiersz() >= górnaGranica() &&
               pozycja.wiersz() <= dolnaGranica() &&
               pozycja.kolumna() >= lewaGranica() &&
               pozycja.kolumna() <= prawaGranica();
    }

    private int górnaGranica() {
        return pozycja.wiersz();
    }

    private int lewaGranica() {
        return pozycja.kolumna();
    }

    private int dolnaGranica() {
        return pozycja.wiersz() + postać.dajWysokość() - 1;
    }

    private int prawaGranica() {
        return pozycja.kolumna() + postać.dajSzerokość() - 1;
    }
}
