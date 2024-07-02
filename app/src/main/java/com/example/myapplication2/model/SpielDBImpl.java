package com.example.myapplication2.model;


/**
 * Klasse implementiert TicTacToeDB, fungiert also als Datenbank
 */
public class SpielDBImpl implements SpielDB{

    /**
     * Konstruktor
     */
    public SpielDBImpl(){

        //Ausgangsposition --> alle Felder unbelegt
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameState[i][j] = 0;
            }
        }
    }


    /**
     * speichert, ob Einzelspielermodus oder Mehrspielermodus gespielt wird
     * true => Mehrspieler
     * false => Einzelspieler
     */
    private boolean mehrspieler = false;

    /**
     * 2-Dimensionales Int Array
     * Speicherung des Gamestate*
     * 0 => unbelegt
     * 1 => Spieler1
     * 2 => Spieler2 / AI, bei Einzelspieler
     */
    private int [][] gameState = new int[3][3];

    /**
     * Boolean, der speichert, ob ich am Zug bin
     * false => keine Buttonaktion möglich
     */
    private boolean myTurn = false;

    private boolean entschieden = false;


    @Override
    public int[][] getGamestate() {
        return gameState;
    }

    @Override
    public void setGamestate(int[][] newGamestate) {
        gameState = newGamestate;
    }

    @Override
    public void setButton(int reihe,int spalte, int spieler) {
        gameState[reihe][spalte] = spieler;
    }

    @Override
    public boolean getMehrspieler() {
        return mehrspieler;
    }
    @Override
    public void setMehrspieler(boolean newMehrspieler) {
        mehrspieler = newMehrspieler;
    }
    @Override
    public void setMyturn(boolean myturn) {
        this.myTurn = myturn;
    }
    @Override
    public boolean getMyturn() {
        return myTurn;
    }
    @Override
    public void setEntschieden(boolean entschieden) {
        this.entschieden = entschieden;
    }
    @Override
    public boolean getEntschieden() {
        return entschieden;
    }
}
