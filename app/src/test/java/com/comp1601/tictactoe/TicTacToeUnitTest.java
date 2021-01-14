package com.comp1601.tictactoe;

import org.junit.Test;

import static org.junit.Assert.*;


public class TicTacToeUnitTest {
    public static double toleranceForReals = 0.01;

    @Test
    public void invalidStepTest() throws Exception{
        TicTacToeGame tictactoe = new TicTacToeGame();
        int nextStep = tictactoe.nextStep(-1);
        double tolerance = toleranceForReals;
        assertEquals(nextStep, TicTacToeGame.InvalidResult, tolerance);
    }

}
