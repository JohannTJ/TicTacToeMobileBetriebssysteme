package com.example.myapplication2.model;

/**
 * Interface, was den Spielstatus berechnet
 */
public interface PruefeGamestate {

    /**
     * prüfe, ob ein Spieler gewonnen hat
     * @return true, wenn gewonnen, false anderenfalls
     */
    boolean pruefeGewinn();

    /**
     * prüfe, ob unendschieden gespielt wurde
     * @return true, falls unendschieden, false anderenfalls
     */
    boolean pruefeUnendschieden();

}
