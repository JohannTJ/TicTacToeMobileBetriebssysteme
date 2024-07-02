package com.example.myapplication2.model;

/**
 * Interface lässt X und Y Koordinaten der Buttons berechnen
 *

 */
public interface Feldkoordinaten {

    /**
     * Gibt die Spalte des gedrückten Buttons an
     * @param button int
     * @return int
     */
     int getButtonSpalte(int button);

    /**
     * gibt die Reihe des gedrückten Buttons zurück
     * @param button int
     * @return int
     */
    int getButtonReihe(int button);

    /**
     * gibt den Status eines bestimmten Buttons zurück
     * @param button welcher Button überprüft werden soll
     * @param gameState Spielstand
     * @return int
     */
    int getButtonStatus(int button, int[][] gameState);

}
