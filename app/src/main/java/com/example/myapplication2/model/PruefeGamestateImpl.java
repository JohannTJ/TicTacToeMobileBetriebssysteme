package com.example.myapplication2.model;


/**
 * Diese Klasse prüft ob Spiel entscheiden oder unendschieden
 */
public class PruefeGamestateImpl implements PruefeGamestate {


    /**
     * 0 = weiß --> kein Spieler
     * 1 = rot --> Spieler 1
     * 2 = blau --> Spieler 2
     */
    private final int[][] button;

    /**
     * Konstruktor
     * @param button spielstand
     */
    public PruefeGamestateImpl(int[][] button){
        this.button = button;
    }

    @Override
    public boolean pruefeGewinn(){
        for (int i = 0; i < 3; i++) {
            int j = 0;

            //Horizontal
            if ((button[i][j] == 1 && button[i][j + 1] == 1 && button[i][j + 2] == 1) || (button[i][j] == 2 && button[i][j + 1] == 2 && button[i][j + 2] == 2)) {
                return true;
            }


            //Senkrecht
            if ((button[j][i] == 1 && button[j + 1][i] == 1 && button[j + 2][i] == 1) || (button[j][i] == 2 && button[j + 1][i] == 2 && button[j + 2][i] == 2)) {
                return true;
            }
        }
        //Diagonal
        if ((button[0][0] == 1 && button[1][1] == 1 && button[2][2] == 1) || (button[0][0] == 2 && button[1][1] == 2 && button[2][2] == 2)) {
            return true;
        }
        if ((button[2][0] == 1 && button[1][1] == 1 && button[0][2] == 1) || (button[2][0] == 2 && button[1][1] == 2 && button[0][2] == 2)) {
            return true;
        }
        return false;
    }


    @Override
    public boolean pruefeUnendschieden(){
        int nichtBelegt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (button[i][j] == 0){
                    nichtBelegt++;
                }
            }
        }

        if (nichtBelegt == 0 && !pruefeGewinn()){
            return true;
        }
        return false;
    }
}
