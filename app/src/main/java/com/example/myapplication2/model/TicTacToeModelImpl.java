package com.example.myapplication2.model;

/**
 * Klasse implementiert TicTacToeDB, fungiert also als Datenbank
 */
public class TicTacToeModelImpl implements TicTacToeModel {

    /**
     * Datenbank für das Spiel
     */
    private SpielDB spielDB = new SpielDBImpl();
    private Feldkoordinaten feldkoordinaten = new FeldkoordinatenImpl();



    @Override
    public int[][] getGamestate() {
        return spielDB.getGamestate();
    }

    @Override
    public void setGamestate(int[][] newGamestate) {
        spielDB.setGamestate(newGamestate);
    }


    @Override
    public void setButton(int button, int spieler) {
        spielDB.setButton(feldkoordinaten.getButtonReihe(button),feldkoordinaten.getButtonSpalte(button),spieler);
    }

    @Override
    public int getButtonStatus(int button) {
        return feldkoordinaten.getButtonStatus(button, getGamestate());
    }

    @Override
    public boolean pruefeGewinn() {
        PruefeGamestate pruefeGamestate = new PruefeGamestateImpl(getGamestate());

        return pruefeGamestate.pruefeGewinn();
    }

    @Override
    public boolean pruefeUnendschieden() {
        PruefeGamestate pruefeGamestate = new PruefeGamestateImpl(getGamestate());

        return pruefeGamestate.pruefeUnendschieden();
    }

    @Override
    public int naechstesFeld() {
        GegnerZug gegnerZug = new GegnerZugImpl(getGamestate());

        return gegnerZug.naechstesFeld();
    }

    @Override
    public boolean getMehrspieler() {
        return spielDB.getMehrspieler();
    }

    @Override
    public void setMehrspieler(boolean newMehrspieler) {
        spielDB.setMehrspieler(newMehrspieler);
    }

    @Override
    public void setMyturn(boolean myturn) {
        spielDB.setMyturn(myturn);
    }

    @Override
    public boolean getMyturn() {
        return spielDB.getMyturn();
    }

    @Override
    public void setEntschieden(boolean entschieden) {
        spielDB.setEntschieden(entschieden);
    }

    @Override
    public boolean getEntschieden() {
        return spielDB.getEntschieden();
    }
}
