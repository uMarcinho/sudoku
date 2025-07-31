package br.com.dio.util;

import br.com.dio.model.Difficulty;
import br.com.dio.model.Space;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardGenerator {
    private static final Random random = new Random();
    
    public static List<List<Space>> generateBoard(Difficulty difficulty) {
        List<List<Space>> board = new ArrayList<>();
        int[][] solvedBoard = generateSolvedBoard();
        int cellsToKeep = difficulty.getFixedCells();
        
        for (int i = 0; i < 9; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 9; j++) {
                boolean keepCell = (i * 9 + j) < cellsToKeep;
                int value = keepCell ? solvedBoard[i][j] : 0;
                board.get(i).add(new Space(value, keepCell));
            }
        }
        return board;
    }

    private static int[][] generateSolvedBoard() {
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = (i + j) % 9 + 1;
            }
        }
        return board;
    }
}