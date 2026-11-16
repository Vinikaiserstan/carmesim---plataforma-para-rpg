package br.edu.ifpi.carmesim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Carmesim");
        stage.setScene(new Scene(new StackPane(), 960, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
