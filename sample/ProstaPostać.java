package sample;

import gra.Postać;

public class ProstaPostać implements Postać {
    private int wysokość;
    private int szerokość;

    public ProstaPostać(int wysokość, int szerokość) {
        this.wysokość = wysokość;
        this.szerokość = szerokość;
    }

    public int dajWysokość() {
        return wysokość;
    }

    public int dajSzerokość() {
        return szerokość;
    }
}
