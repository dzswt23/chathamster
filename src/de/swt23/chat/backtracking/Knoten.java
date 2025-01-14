package de.swt23.chat.backtracking;

public class Knoten {
    private final int reihe;
    private final int spalte;

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