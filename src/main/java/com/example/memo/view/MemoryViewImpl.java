package com.example.memo.view;

import com.example.memo.model.Card;
import com.example.memo.model.MemoryModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;
import java.util.Map;

public class MemoryViewImpl extends VBox implements MemoryView {
    private final GridPane gridPane;
    private final Label movesLabel;
    private final Button[][] buttons;
    private CardClickListener listener;

    private final Map<Integer, Image> cardImages = new HashMap<>();
    private Image shirtImage;

    public MemoryViewImpl() {
        this.gridPane = new GridPane();
        this.movesLabel = new Label("Ходы: 0");
        this.buttons = new Button[MemoryModel.ROWS][MemoryModel.COLS];

        loadImages();
        setupUI();
    }

    private void loadImages() {
        try {
            shirtImage = new Image(getClass().getResourceAsStream("/images/shirt.png"));
        } catch (Exception e) {
            System.out.println("Рубашка не найдена.");
        }

        int totalCells = MemoryModel.ROWS * MemoryModel.COLS;
        int maxUniqueImages = totalCells / 2;

        for (int i = 1; i <= maxUniqueImages; i++) {
            try {
                var stream = getClass().getResourceAsStream("/images/" + i + ".png");
                if (stream != null) {
                    cardImages.put(i, new Image(stream));
                } else {
                    System.out.println("Файл " + i + ".png не найден.");
                }
            } catch (Exception e) {
                System.out.println("Не удалось загрузить картинку: " + i);
            }
        }
    }

    private void setupUI() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(10);
        this.setPadding(new Insets(15));

        movesLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        movesLabel.setMinHeight(40);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(12);
        gridPane.setVgap(12);

        for (int r = 0; r < MemoryModel.ROWS; r++) {
            for (int c = 0; c < MemoryModel.COLS; c++) {
                Button btn = new Button();
                btn.setMinSize(110, 110);
                btn.setMaxSize(110, 110);
                btn.setPrefSize(110, 110);
                btn.setPadding(Insets.EMPTY);

                int finalR = r;
                int finalC = c;
                btn.setOnAction(e -> {
                    if (listener != null) {
                        listener.onCardClicked(finalR, finalC);
                    }
                });

                buttons[r][c] = btn;
                gridPane.add(btn, c, r);
            }
        }

        this.getChildren().addAll(movesLabel, gridPane);
    }

    @Override
    public void setCardClickListener(CardClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void updateBoard(Card[][] board) {
        for (int r = 0; r < MemoryModel.ROWS; r++) {
            for (int c = 0; c < MemoryModel.COLS; c++) {
                Card card = board[r][c];
                Button btn = buttons[r][c];
                btn.setText("");

                if (card.getId() == 0) {
                    btn.setGraphic(null);
                    btn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                    btn.setDisable(true);
                } else if (card.isOpened() || card.isMatched()) {
                    Image img = cardImages.get(card.getId());
                    if (img != null) {
                        ImageView iv = new ImageView(img);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setFitWidth(85);
                        iv.setFitHeight(85);
                        btn.setGraphic(iv);
                    } else {
                        btn.setGraphic(null);
                        btn.setText(String.valueOf(card.getId()).trim());
                        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
                    }
                    btn.setStyle("-fx-background-color: #2196F3, #FFFFFF; -fx-background-insets: 0, 3; -fx-text-fill: #2196F3;");
                } else {
                    if (shirtImage != null) {
                        ImageView iv = new ImageView(shirtImage);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setFitWidth(85);
                        iv.setFitHeight(85);
                        btn.setGraphic(iv);
                    } else {
                        btn.setGraphic(null);
                        btn.setText("?");
                        btn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
                    }
                    btn.setStyle("-fx-background-color: #2196F3, #2196F3; -fx-background-insets: 0, 3; -fx-text-fill: white;");
                }
            }
        }
    }

    @Override
    public void updateMovesDisplay(int moves) {
        movesLabel.setText("Ходы: " + moves);
    }

    @Override
    public void showWinAlert(int totalMoves) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Победа!");
        alert.setHeaderText(null);
        alert.setContentText("Поздравляем! Вы открыли все картинки!\nВсего сделано ходов: " + totalMoves);
        alert.showAndWait();
    }
}