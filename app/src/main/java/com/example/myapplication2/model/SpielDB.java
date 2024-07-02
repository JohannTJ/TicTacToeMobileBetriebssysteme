package com.example.myapplication2.model;

public interface SpielDB {
    /**
     * Gibt Gamestate zurück
     * @return int[][]
     */
    int[][] getGamestate();

    /**
     * Setze Gamestate neu
     * @param newGamestate, int[][]
     */
    void setGamestate(int[][] newGamestate);

    /**
     * setze einen Button in gamestate neu
     * @param reihe int --> in welcher Reihe befindet sich der Button
     * @param spalte int --> in welcher Spalte befindet sich der Button
     * @param spieler, Spieler, der Button gespielt hat --> 0-2
     */
    void setButton(int reihe, int spalte , int spieler);


    /**
     * gibt zurück, ob der Spieler im Mehrspielermodus oder Einzelspielermodus spielt
     * @return boolean
     */
    boolean getMehrspieler();

    /**
     * setzt mehrspieler neu
     * @param newMehrspieler boolean
     */
    void setMehrspieler(boolean newMehrspieler);

    /**
     * setzt boolean myturn neu
     * @param myturn boolean
     */
    void setMyturn(boolean myturn);

    /**
     * get boolean myturn
     * @return boolean
     */
    boolean getMyturn();


    /**
     * set boolean entschieden
     * @param entschieden
     */
    void setEntschieden(boolean entschieden);

    /**
     * get boolean entschieden
     * @return boolean
     */
    boolean getEntschieden();

}
