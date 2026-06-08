package com.example.memo;

import com.example.memo.model.MemoryModel;
import com.example.memo.presenter.MemoryPresenter;
import com.example.memo.view.MemoryViewImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        MemoryModel model = new MemoryModel();
        MemoryViewImpl view = new MemoryViewImpl();
        new MemoryPresenter(model, view);

        Scene scene = new Scene(view);

        stage.setTitle("Игра Мемори (MVP)");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}