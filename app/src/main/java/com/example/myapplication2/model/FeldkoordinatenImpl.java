package com.example.myapplication2.model;

/**
 * Klasse implementiert Feldkoordinaten und gibt X und Y Koordinaten für die Buttons an
 */
public class FeldkoordinatenImpl implements Feldkoordinaten{

    @Override
    public int getButtonSpalte(int button) {
        switch (button){
            case 0: return 0;
            case 1: return 1;
            case 2: return 2;
            case 3: return 0;
            case 4: return 1;
            case 5: return 2;
            case 6: return 0;
            case 7: return 1;
            default: return 2;
        }
    }

   @Override
    public int getButtonReihe(int button) {
        switch (button) {
            case 0: return 0;
            case 1: return 0;
            case 2: return 0;
            case 3: return 1;
            case 4: return 1;
            case 5: return 1;
            case 6: return 2;
            case 7: return 2;
            default:return 2;
        }
    }

    @Override
    public int getButtonStatus(int button, int[][] gameState) {
        int reihe = getButtonReihe(button);
        int spalte = getButtonSpalte(button);

        return gameState[reihe][spalte];
    }
}
