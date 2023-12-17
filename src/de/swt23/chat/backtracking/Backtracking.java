package de.swt23.chat.backtracking;

import de.swt23.chat.hamster.HamsterDirection;
import de.swt23.chat.manager.ChatManager;
import de.swt23.chat.message.Message;
import de.swt23.chat.message.Text;
import de.swt23.chat.hamster.Hamster;

public class Backtracking {
    private int ANZAHL_REIHEN = 0;
    private int ANZAHL_SPALTEN =0;


    private static final int LEERES_FELD = 0;
    private static final int MAUER_DA = 1;
    private static final int KORN_DA = 2;

    private static final int MOEGLICHER_WEG = 3;


    private Hamster hamster; // eigener Hamster
    private Knoten[] weg; // speichert den Weg zum Korn
    private int knotenZaehler; // zählt alle Wegpunkte
    private int[][] karte; // Abbild des Spielfelds

    private ChatManager manager;

    public Backtracking(ChatManager manager,Hamster hamster) {
        this.hamster = hamster;
        knotenZaehler = 0;
        this.manager = manager;
    }

    /*
     * Scannt die Karte auf Mauern und Körner
     */
    public void scanneKarte(Text text) {
        int stelle = 3;

        if (text.getText().contains("territorium:")) {
            System.out.println("Check for valid input correct");

            String[] nachrichtTerritorium = text.getText().split(" ");

            ANZAHL_SPALTEN = Integer.parseInt(nachrichtTerritorium[1]);
            ANZAHL_REIHEN = Integer.parseInt(nachrichtTerritorium[2]);

            weg = new Knoten[ANZAHL_REIHEN * ANZAHL_SPALTEN];

            karte = new int[ANZAHL_REIHEN][ANZAHL_SPALTEN];

            for (int reihe = 0; reihe < karte.length; reihe++) {
                for (int spalte = 0; spalte < karte[0].length; spalte++) {

                    if(stelle < nachrichtTerritorium.length){
                        if (nachrichtTerritorium[stelle].equals("x")) {
                            karte[reihe][spalte] = MAUER_DA;
                        }
                        else if (nachrichtTerritorium[stelle].equals("!")) {
                            karte[reihe][spalte] = KORN_DA;
                        }
                        else if (nachrichtTerritorium[stelle].equals("0")) {
                            karte[reihe][spalte] = LEERES_FELD;
                        }else {
                            break;
                        }
                    }
                    stelle = stelle + 1;
                }

            }

        }

    }

    public void zeigeKarteInKonsole() {
        for (int reihe = 0; reihe < karte.length; reihe++) {
            for (int spalte = 0; spalte < karte[0].length; spalte++) {
                switch (karte[reihe][spalte]) {
                    case MAUER_DA:
                        System.out.print("x ");
                        break;
                    case KORN_DA:
                        System.out.print("! ");
                        break;
                    case LEERES_FELD:
                        System.out.print("0 ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println(); // Neue Zeile für die nächste Reihe
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
        //Mal gucken ob es klappt hardcodiert
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
        System.out.println("Gehe weg jetzt");
        for (int i = knotenZaehler - 1; i >= 0; i--) {
            int reihe = weg[i].getReihe();
            int spalte = weg[i].getSpalte();

            int alteReihe = hamster.getReihe();
            int alteSpalte = hamster.getSpalte();

            // Nach Süden gehen
            if (reihe > alteReihe) {
                hamster.setBlickrichtung(HamsterDirection.SOUTH);
                hamster.vor();
            }

            // Nach Norden gehen
            if (reihe < alteReihe) {
                hamster.setBlickrichtung(HamsterDirection.NORTH);
                hamster.vor();
            }

            // Nach Osten gehen
            if (spalte > alteSpalte) {
                hamster.setBlickrichtung(HamsterDirection.EAST);
                hamster.vor();
            }

            // Nach Westen gehen
            if (spalte < alteSpalte) {
                hamster.setBlickrichtung(HamsterDirection.WEST);
                hamster.vor();
            }
        }

    }

}






