package com.example.memo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryModel {
    public static final int ROWS = 6;
    public static final int COLS = 1;

    private final Card[][] board;
    private int moves;

    public MemoryModel() {
        this.board = new Card[ROWS][COLS];
        this.moves = 0;
        generateBoard();
    }

    private void generateBoard() {
        int totalCells = ROWS * COLS;
        List<Integer> ids = new ArrayList<>();

        int playableCells = (totalCells % 2 == 0) ? totalCells : totalCells - 1;
        int pairsCount = playableCells / 2;

        for (int i = 1; i <= pairsCount; i++) {
            ids.add(i);
            ids.add(i);
        }

        Collections.shuffle(ids);

        if (totalCells % 2 != 0) {
            ids.add(0);
        }

        int index = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int id = ids.get(index++);
                board[r][c] = new Card(id);

                // Если это пустая клетка, она сразу считается "отгаданной" и открытой
                if (id == 0) {
                    board[r][c].setOpened(true);
                    board[r][c].setMatched(true);
                }
            }
        }
    }

    public Card getCard(int row, int col) { return board[row][col]; }
    public Card[][] getBoard() { return board; }
    public int getMoves() { return moves; }
    public void incrementMoves() { moves++; }

    public boolean checkWin() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (!board[r][c].isMatched()) {
                    return false;
                }
            }
        }
        return true;
    }
}