package com.example.memo.presenter;

import com.example.memo.model.Card;
import com.example.memo.model.MemoryModel;
import com.example.memo.view.MemoryView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class MemoryPresenter implements MemoryView.CardClickListener {
    private final MemoryModel model;
    private final MemoryView view;

    private Card firstSelected = null;
    private Card secondSelected = null;

    private boolean isWaiting = false;

    public MemoryPresenter(MemoryModel model, MemoryView view) {
        this.model = model;
        this.view = view;
        this.view.setCardClickListener(this);
        this.view.updateBoard(model.getBoard());
    }

    @Override
    public void onCardClicked(int row, int col) {
        if (isWaiting) return;

        Card clickedCard = model.getCard(row, col);

        if (clickedCard.isOpened() || clickedCard.isMatched()) return;

        if (firstSelected == null) {
            firstSelected = clickedCard;
            firstSelected.setOpened(true);
            view.updateBoard(model.getBoard()); // Показываем её лицо на экране
            return;
        }

        secondSelected = clickedCard;
        secondSelected.setOpened(true);

        model.incrementMoves();
        view.updateMovesDisplay(model.getMoves());
        view.updateBoard(model.getBoard());


        if (firstSelected.getId() == secondSelected.getId()) {
            firstSelected.setMatched(true);
            secondSelected.setMatched(true);

            firstSelected = null;
            secondSelected = null;

            if (model.checkWin()) {
                view.showWinAlert(model.getMoves());
            }
        } else {
            isWaiting = true;

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                firstSelected.setOpened(false);
                secondSelected.setOpened(false);

                firstSelected = null;
                secondSelected = null;
                isWaiting = false;

                view.updateBoard(model.getBoard());
            });
            pause.play();
        }
    }
}