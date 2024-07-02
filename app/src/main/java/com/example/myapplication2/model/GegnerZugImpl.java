package com.example.myapplication2.model;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Klasse implementiert GegnerZug, berechnet also den nächsten Zug des Computers
 */
public class GegnerZugImpl implements GegnerZug {
    /*
    1. AI gewinnt
    2. Spielergewinn verhindern
    3. zweites Feld mit einem leeren dahinter
    4. random Feld mit weiteren zwei leeren Feldern
    5. random Feld
     */

    /**
     * 0 = weiß --> kein Spieler
     * 1 = rot --> Spieler 1
     * 2 = blau --> Spieler 2
     */
    private int[][] button;

    /**
     * Konstruktor
     * @param button Spielfeld
     */
    public GegnerZugImpl(int[][] button){
        this.button = button;
    }



    @Override
    public int naechstesFeld() {
        int aiGewinnt = pruefeGewinn();
        if (aiGewinnt != -1) {
            return aiGewinnt;
        }

        int spielergewinnVerhindern = pruefeSpielergewinnVerhindern();
        if (spielergewinnVerhindern != -1) {
            return spielergewinnVerhindern;
        }

        int zweitesFeld = zweitesFeld();
        if (zweitesFeld != -1) {
            return zweitesFeld;
        }

        int randomFeld1 = randomFeld1();
        if (randomFeld1 != -1) {
            return randomFeld1;
        }

        int randomFeld2 = randomFeld2();
        return randomFeld2;
    }


    /**
     * prüfe, welches Feld noch frei ist --> keine Möglichkeit mehr für den Computer zu gewinnen
     * @return Button 1-9
     */
    private int randomFeld2() {
        HashSet<Integer> setOfPossibleReturn = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (button[i][j] == 0){
                    switch (i){
                        case 0: switch (j){
                            case 0: setOfPossibleReturn.add(0);break;
                            case 1: setOfPossibleReturn.add(1);break;
                            case 2: setOfPossibleReturn.add(2);break;
                        } break;
                        case 1: switch (j){
                            case 0: setOfPossibleReturn.add(3);break;
                            case 1: setOfPossibleReturn.add(4);break;
                            case 2: setOfPossibleReturn.add(5);break;
                        }break;
                        case 2: switch (j){
                            case 0: setOfPossibleReturn.add(6);break;
                            case 1: setOfPossibleReturn.add(7);break;
                            case 2: setOfPossibleReturn.add(8);break;
                        }break;
                    }
                }
            }
        }
        return returnInt(setOfPossibleReturn);
    }

    /**
     * prüft, ob der Computer ein Feld setzen kann, bei dem 2 weitere freie Felder sind
     * @return Button 1-9, falls ja; -1 falls nein
     */
    private int randomFeld1() {
        HashSet<Integer> setOfPossibleReturn = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            int j = 0;

            //Horizontal
            if (button[i][j] == 0 && button[i][j + 1] == 0 && button[i][j + 2] == 0) {
                setOfPossibleReturn.add(2 + i * 3);
                setOfPossibleReturn.add(1 + i * 3);
                setOfPossibleReturn.add(i * 3);
            }
            if (button[i][j] == 0 && button[i][j + 1] == 0 && button[i][j + 2] == 0) {
                setOfPossibleReturn.add(2 + i * 3);
                setOfPossibleReturn.add(1 + i * 3);
                setOfPossibleReturn.add(i * 3);
            }
            if (button[i][j] == 0 && button[i][j + 1] == 0 && button[i][j + 2] == 0) {
                setOfPossibleReturn.add(2 + i * 3);
                setOfPossibleReturn.add(1 + i * 3);
                setOfPossibleReturn.add(i * 3);
            }

            //Senkrecht
            if (button[j][i] == 0 && button[j + 1][i] == 0 && button[j + 2][i] == 0) {
                setOfPossibleReturn.add(i);
                setOfPossibleReturn.add(3 + i);
                setOfPossibleReturn.add(6 + i);
            }
            if (button[j][i] == 0 && button[j + 1][i] == 0 && button[j + 2][i] == 0) {
                setOfPossibleReturn.add(i);
                setOfPossibleReturn.add(3 + i);
                setOfPossibleReturn.add(6 + i);
            }
            if (button[j][i] == 0 && button[j + 1][i] == 0 && button[j + 2][i] == 0) {
                setOfPossibleReturn.add(i);
                setOfPossibleReturn.add(3 + i);
                setOfPossibleReturn.add(6 + i);

            }




        }
        //Diagonal
        if (button[0][0] == 0 && button[1][1] == 0 && button[2][2] == 0) {
            setOfPossibleReturn.add(0);
            setOfPossibleReturn.add(4);
            setOfPossibleReturn.add(8);
        }
        if (button[2][0] == 0 && button[1][1] == 0 && button[0][2] == 0) {
            setOfPossibleReturn.add(2);
            setOfPossibleReturn.add(4);
            setOfPossibleReturn.add(6);
        }


        // wenn Set leer ist --> -1
        if (setOfPossibleReturn.isEmpty())
            return -1;
            // Wenn Set nicht leer ist --> random number aus set
        else {
            return returnInt(setOfPossibleReturn);
        }


    }

    /**
     * prüfe, ob der Computer ein Feld setzen kann, sodass zwei in einer Reihe und ein freies dahinter
     * @return Button 1-9, falls ja; -1, falls nein
     */
    private int zweitesFeld() {
        HashSet<Integer> setOfPossibleReturn = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            int j = 0;

            //Horizontal
            if (button[i][j] == 0 && button[i][j + 1] == 0 && button[i][j + 2] == 1) {
                setOfPossibleReturn.add(1 + i * 3);
                setOfPossibleReturn.add(i * 3);
            }
            if (button[i][j] == 0 && button[i][j + 1] == 1 && button[i][j + 2] == 0) {
                setOfPossibleReturn.add(2 + i * 3);
                setOfPossibleReturn.add(i * 3);
            }
            if (button[i][j] == 1 && button[i][j + 1] == 0 && button[i][j + 2] == 0) {
                setOfPossibleReturn.add(2 + i * 3);
                setOfPossibleReturn.add(1 + i * 3);
            }

            //Senkrecht
            if (button[j][i] == 0 && button[j + 1][i] == 0 && button[j + 2][i] == 1) {
                setOfPossibleReturn.add(i); // 0
                setOfPossibleReturn.add(3 + i);
            }
            if (button[j][i] == 0 && button[j + 1][i] == 1 && button[j + 2][i] == 0) {
                setOfPossibleReturn.add(i); // 0
                setOfPossibleReturn.add(6 + i);
            }
            if (button[j][i] == 1 && button[j + 1][i] == 0 && button[j + 2][i] == 0) {
                setOfPossibleReturn.add(3 + i);
                setOfPossibleReturn.add(6 + i);

            }

            //Diagonal
            if (button[0][0] == 1 && button[1][1] == 0 && button[2][2] == 0) {
                setOfPossibleReturn.add(4);
                setOfPossibleReturn.add(8);
            }
            if (button[0][0] == 0 && button[1][1] == 1 && button[2][2] == 0) {
                setOfPossibleReturn.add(0);
                setOfPossibleReturn.add(8);
            }
            if (button[0][0] == 0 && button[1][1] == 0 && button[2][2] == 1) {
                setOfPossibleReturn.add(0);
                setOfPossibleReturn.add(4);
            }
        }

        if (button[2][0] == 1 && button[1][1] == 0 && button[0][2] == 0) {
            setOfPossibleReturn.add(4);
            setOfPossibleReturn.add(8);
        }
        if (button[2][0] == 0 && button[1][1] == 1 && button[0][2] == 0) {
            setOfPossibleReturn.add(8);
            setOfPossibleReturn.add(2);
        }
        if (button[0][2] == 0 && button[1][1] == 0 && button[2][0] == 1) {
            setOfPossibleReturn.add(2);
            setOfPossibleReturn.add(4);
        }
        // wenn Set leer ist --> -1
        if (setOfPossibleReturn.isEmpty())
            return -1;
            // Wenn Set nicht leer ist --> random number aus set
        else {
            return returnInt(setOfPossibleReturn);
        }

    }

    /**
     * prüfe, ob Computer Gewinn verhindern kann
     * @return Button 1-9, falls ja; -1, falls nein
     */
    private int pruefeSpielergewinnVerhindern() {
        for (int i = 0; i < 3; i++) {
            int j = 0;

            //Horizontal
            if (button[i][j] == 2 && button[i][j + 1] == 2 && button[i][j + 2] == 0) {
                return 2 + i * 3;
            }
            if (button[i][j] == 2 && button[i][j + 1] == 0 && button[i][j + 2] == 2) {
                return 1 + i * 3;
            }
            if (button[i][j] == 0 && button[i][j + 1] == 2 && button[i][j + 2] == 2) {
                return i * 3;
            }

            //Senkrecht
            if (button[j][i] == 2 && button[j + 1][i] == 2 && button[j + 2][i] == 0) {
                return 6 + i;
            }
            if (button[j][i] == 2 && button[j + 1][i] == 0 && button[j + 2][i] == 2) {
                return 3 + i;
            }
            if (button[j][i] == 0 && button[j + 1][i] == 2 && button[j + 2][i] == 2) {
                return i;
            }
        }

        //Diagonal
        if (button[0][0] == 2 && button[1][1] == 2 && button[2][2] == 0)
            return 8;
        if ((button[0][0] == 2 && button[1][1] == 0 && button[2][2] == 2) || (button[0][2] == 2 && button[1][1] == 0 && button[2][0] == 2))
            return 4;
        if (button[0][0] == 0 && button[1][1] == 2 && button[2][2] == 2)
            return 0;

        if (button[2][0] == 2 && button[1][1] == 2 && button[0][2] == 0)
            return 2;
        if (button[2][0] == 0 && button[1][1] == 2 && button[0][2] == 2)
            return 6;


        return -1;
    }

    /**
     * prüfe, ob Computer mit dem nächsten Zug gewinnen kann
     * @return 1-9, falls ja; -1, falls nein
     */
    private int pruefeGewinn() {
        for (int i = 0; i < 3; i++) {
            int j = 0;

            //Horizontal
            if (button[i][j] == 1 && button[i][j + 1] == 1 && button[i][j + 2] == 0) {
                return 2 + i * 3;
            }
            if (button[i][j] == 1 && button[i][j + 1] == 0 && button[i][j + 2] == 1) {
                return 1 + i * 3;
            }
            if (button[i][j] == 0 && button[i][j + 1] == 1 && button[i][j + 2] == 1) {
                return i * 3;
            }

            //Senkrecht
            if (button[j][i] == 1 && button[j + 1][i] == 1 && button[j + 2][i] == 0) {
                return 6 + i;
            }
            if (button[j][i] == 1 && button[j + 1][i] == 0 && button[j + 2][i] == 1) {
                return 3 + i;
            }
            if (button[j][i] == 0 && button[j + 1][i] == 1 && button[j + 2][i] == 1) {
                return i;
            }
        }

        //Diagonal
        if (button[0][0] == 1 && button[1][1] == 1 && button[2][2] == 0)
            return 8;
        if ((button[0][0] == 1 && button[1][1] == 0 && button[2][2] == 1) || (button[0][2] == 1 && button[1][1] == 0 && button[2][0] == 1))
            return 4;
        if (button[0][0] == 0 && button[1][1] == 1 && button[2][2] == 1)
            return 0;

        if (button[2][0] == 1 && button[1][1] == 1 && button[0][2] == 0)
            return 2;
        if (button[2][0] == 0 && button[1][1] == 1 && button[0][2] == 1)
            return 6;


        return -1;
    }

    private int returnInt(HashSet<Integer> set){
        int randomNumber = (int)(Math.random() * 9);
        for (Integer t: set){
            System.out.println(t);
        }
        System.out.println("\n");
        if (set.contains(randomNumber) /*&& belegt(randomNumber)*/){
            return randomNumber;
        }
        else
            return returnInt(set);
    }






}

