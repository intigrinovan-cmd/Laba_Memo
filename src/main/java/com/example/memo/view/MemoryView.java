package com.example.memo.view;

import com.example.memo.model.Card;

public interface MemoryView {
    interface CardClickListener {
        void onCardClicked(int row, int col);
    }

    void setCardClickListener(CardClickListener listener);
    void updateBoard(Card[][] board);
    void updateMovesDisplay(int moves);
    void showWinAlert(int totalMoves);
}