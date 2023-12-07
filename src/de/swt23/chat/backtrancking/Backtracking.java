package de.swt23.chat.backtrancking;

import de.swt23.chat.message.Message;
import de.swt23.chat.message.Text;

import java.util.ArrayList;

/*class*/public class Backtracking {
    private int ANZAHL_REIHEN = 0;
    private int ANZAHL_SPALTEN = 0;


    private static final int LEERES_FELD = 0;
    private static final int MAUER_DA = 1;
    private static final int KORN_DA = 2;
    private static final int MOEGLICHER_WEG = 3;


    private Hamster hamster; // eigener Hamster
    private Knoten[] weg; // speichert den Weg zum Korn
    private int knotenZaehler; // zählt alle Wegpunkte
    private int[][] karte; // Abbild des Spielfelds

    public Backtracking() {
        hamster = new Hamster();
        knotenZaehler = 0;
    }

    /*
     * Scannt die Karte auf Mauern und Körner
     */
    public void scanneKarte(ArrayList<Message> messages) {
        int stelle = 17;
        Message m = messages.get(messages.size() - 1);
        if (m instanceof Text text) {
            if (text.getText().contains("territorium:")) {
                ANZAHL_SPALTEN = text.getText().charAt(14);
                ANZAHL_REIHEN = text.getText().charAt(16);

                weg = new Knoten[ANZAHL_REIHEN * ANZAHL_SPALTEN];

                karte = new int[ANZAHL_REIHEN][ANZAHL_SPALTEN];
                for (int reihe = 0; reihe < karte.length; reihe++) {

                    for (int spalte = 0; spalte < karte[0].length; spalte++) {

                        if (text.getText().charAt(stelle) == 'x') {
                            karte[reihe][spalte] = MAUER_DA;
                        }
                        if (text.getText().charAt(stelle) == '!') {
                            karte[reihe][spalte] = KORN_DA;
                        }
                        if (text.getText().charAt(stelle) == '0') {
                            karte[reihe][spalte] = LEERES_FELD;
                        }

                        stelle = stelle + 2;
                    }

                }

            }

        }

    }

    /*
     * Prüfung, ob der Hamster zu der Position aus den Übergabeparametern reihe
     * und spalte gehen kann Dazu gehört: Er darf das Spielfeld nicht verlassen
     * Dazu gehört: Er darf keine Mauer auf der Position sein
     * Dazu gehört: Er darf nocht nicht dort gewesen sein
     */
    private boolean darfGehen(int reihe, int spalte) {
        return reihe >= 0 && spalte >= 0 && reihe < ANZAHL_REIHEN
                && spalte < ANZAHL_SPALTEN && karte[reihe][spalte] != MAUER_DA
                && karte[reihe][spalte] != MOEGLICHER_WEG;
    }

    /*
     * Setzt den Startpunkt für die Tiefensuche
     * Ruft die sucheWeg-Methode auf, um eine Route zum Korn zu finden
     */
    public void sucheRoute() {
        if (!sucheWeg(hamster.getReihe(), hamster.getSpalte())) {
            System.out.println("Ziel nicht erreichbar");
        }
    }

    /*
     * Sucht Weg vom Hamster zum Korn, durch rekursiven Methodenaufruf
     */
    private boolean sucheWeg(int reihe, int spalte) {
        // Überprüft, ob der Hamster zu der Position gehen darf, Pfad kann hier
        // nicht fortgesetzt werden
        if (!darfGehen(reihe, spalte)) {
            return false;
        }

        // Korn gefunden
        if (karte[reihe][spalte] == KORN_DA) {
            weg[knotenZaehler++] = new Knoten(reihe, spalte);
            return true;
        }

        // Markiert den möglichen Weg in der Karten-Übersicht
        karte[reihe][spalte] = MOEGLICHER_WEG;

        // Prüft nächsten Knoten über dem Hamster
        if (sucheWeg(reihe - 1, spalte)) {
            weg[knotenZaehler++] = new Knoten(reihe, spalte);
            return true;
        }

        // Prüft nächsten Knoten rechts neben dem Hamster
        if (sucheWeg(reihe, spalte + 1)) {
            weg[knotenZaehler++] = new Knoten(reihe, spalte);
            return true;
        }

        // Prüft nächsten Knoten unter dem Hamster
        if (sucheWeg(reihe + 1, spalte)) {
            weg[knotenZaehler++] = new Knoten(reihe, spalte);
            return true;
        }

        // Prüft nächsten Knoten links neben dem Hamster
        if (sucheWeg(reihe, spalte - 1)) {
            weg[knotenZaehler++] = new Knoten(reihe, spalte);
            return true;
        }

        // Kennzeichnet den Knoten, von dem man gekommen ist, als bereits
        // geprüft
        karte[reihe][spalte] = LEERES_FELD;
        return false;
    }

    /*
     * Lässt den Hamster den berechneteten Weg traversieren
     */
    public void geheWeg() {
        for (int i = knotenZaehler - 1; i >= 0; i--) {
            int reihe = weg[i].getReihe();
            int spalte = weg[i].getSpalte();

            int alteReihe = hamster.getReihe();
            int alteSpalte = hamster.getSpalte();

            // Nach Süden gehen
            if (reihe > alteReihe) {
                hamster.setBlickrichtung(2);
                hamster.vor();
            }

            // Nach Norden gehen
            if (reihe < alteReihe) {
                hamster.setBlickrichtung(0);
                hamster.vor();
            }

            // Nach Osten gehen
            if (spalte > alteSpalte) {
                hamster.setBlickrichtung(1);
                hamster.vor();
            }

            // Nach Westen gehen
            if (spalte < alteSpalte) {
                hamster.setBlickrichtung(3);
                hamster.vor();
            }
        }

    }

}