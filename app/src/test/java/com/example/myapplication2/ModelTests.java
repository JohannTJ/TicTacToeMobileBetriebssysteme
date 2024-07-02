package com.example.myapplication2;

import com.example.myapplication2.model.GegnerZugImpl;
import com.example.myapplication2.model.TicTacToeModelImpl;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ModelTests {

    /*
    pruefeGewinn()
    pruefeUnendschieden()
    naestesFeld()
    getButtonStatus()
     */
    private TicTacToeModelImpl model;

    @Before
    public void setUp() {
        model = new TicTacToeModelImpl();
    }

    @Test
    public void testPruefeGewinnWin() {
        int[][] winState = {
                {1, 1, 1},
                {0, 2, 0},
                {2, 0, 0}
        };
        model.setGamestate(winState);
        assertTrue(model.pruefeGewinn());
    }

    @Test
    public void testPruefeGewinnNoWin(){
        int[][] noWinState = {
                {1, 1, 0},
                {0, 2, 0},
                {2, 0, 0}
        };
        model.setGamestate(noWinState);
        assertFalse(model.pruefeGewinn());
    }

    @Test
    public void testPruefeUnendschiedenUnendschieden() {
        int[][] drawState = {
                {1, 2, 1},
                {2, 1, 2},
                {2, 1, 2}
        };
        model.setGamestate(drawState);
        assertTrue(model.pruefeUnendschieden());
    }

    @Test
    public void testPruefeUnendschiedenKeinUnendschieden() {
        int[][] noDrawState = {
                {1, 2, 0},
                {2, 1, 2},
                {2, 1, 2}
        };
        model.setGamestate(noDrawState);
        assertFalse(model.pruefeUnendschieden());
    }

    @Test
    public void testNaechstesFeld_AiKannGewinnen() {
        int[][] gameState = {
                {1, 1, 0},
                {0, 2, 2},
                {0, 0, 0}
        };
        GegnerZugImpl gegnerZug = new GegnerZugImpl(gameState);
        int expected = 2;  // AI kann gewinnen, indem sie das dritte Feld in der ersten Reihe setzt.
        int actual = gegnerZug.naechstesFeld();
        assertEquals(expected, actual);
    }

    @Test
    public void testNaechstesFeld_SpielergewinnVerhindern() {
        int[][] gameState = {
                {2, 0, 0},
                {0, 1, 1},
                {0, 2, 0}
        };
        GegnerZugImpl gegnerZug = new GegnerZugImpl(gameState);
        int expected = 3;  // AI muss den Spielergewinn verhindern, indem sie das dritte Feld in der ersten Reihe setzt.
        int actual = gegnerZug.naechstesFeld();
        assertEquals(expected, actual);
    }

    @Test
    public void testNaechstesFeld_ZweitesFeld() {
        int[][] gameState = {
                {2, 1, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        GegnerZugImpl gegnerZug = new GegnerZugImpl(gameState);
        int actual = gegnerZug.naechstesFeld();
        System.out.println(actual);
        assertTrue(actual == 4 || actual == 5 || actual == 7 || actual == 9);  // AI sollte eines der strategischen Felder setzen
    }

    @Test
    public void testNaechstesFeld_RandomFeld1() {
        int[][] gameState = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        GegnerZugImpl gegnerZug = new GegnerZugImpl(gameState);
        int actual = gegnerZug.naechstesFeld();
        assertTrue(actual >= 0 && actual <= 8);  // AI sollte ein beliebiges leeres Feld setzen.
    }

    @Test
    public void testNaechstesFeld_RandomFeld2() {
        int[][] gameState = {
                {1, 1, 2},
                {2, 2, 1},
                {1, 0, 1}
        };
        GegnerZugImpl gegnerZug = new GegnerZugImpl(gameState);
        int expected = 7;  // AI sollte das einzige verbleibende leere Feld setzen.
        int actual = gegnerZug.naechstesFeld();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetButtonStatus() {
        int[][] gameState = {
                {1, 2, 0},
                {2, 1, 0},
                {0, 0, 1}
        };
        model.setGamestate(gameState);
        assertEquals(1, model.getButtonStatus(0));
        assertEquals(2, model.getButtonStatus(1));
        assertEquals(0, model.getButtonStatus(2));
    }
}
