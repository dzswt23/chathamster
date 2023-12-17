package de.swt23.chat.backtracking;

/*class*/public class Knoten {
    private int reihe;
    private int spalte;

    public Knoten(int reihe, int spalte) {
        this.reihe = reihe;
        this.spalte = spalte;
    }

    public int getReihe() {
        return this.reihe;
    }

    public int getSpalte() {
        return this.spalte;
    }
}