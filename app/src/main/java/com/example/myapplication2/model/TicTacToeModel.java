package com.example.myapplication2.model;

/**
 * Interface, was nötige Methoden einer Datenbank für ein TicTacToe-Spiel definiert
 */
public interface TicTacToeModel {
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
     * @param button Buttonnumber --> 0-8
     * @param spieler, Spieler, der Button gespielt hat --> 0-2
     */
    void setButton(int button, int spieler);
    /**
     * gibt zurück, wer den Button gedrückt hat
     * @return int button
     */
    int getButtonStatus(int button);
    /**
     * gibt den Rückgabewert der Methode pruefeGewinn() aus PruefeGamestate zurück
     * @return boolean
     */
    boolean pruefeGewinn();
    /**
     * gibt den Rückgabewert der Methode pruefeUnendschieden() aus PruefeGamestate zurück
     * @return boolean
     */
    boolean pruefeUnendschieden();
    /**
     * gibt den Rückgabewert der Methode naechsteFeld() aus GegnerZug zurück
     * @return int
     */
    int naechstesFeld();
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
