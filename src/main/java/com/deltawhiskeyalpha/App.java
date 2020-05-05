package com.deltawhiskeyalpha;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application
{
    private static int sceneWidth = 640;
    private static int sceneHeight = 480;

    public static void main(String[] args)
    {
        Sudoku game = new Sudoku();
        game.solve();
        launch();
    }

    @Override
    public void start(Stage stage)
    {
        var label = new Label("Hello, JavaFX");
        var scene = new Scene(new StackPane(label), sceneWidth, sceneHeight);
        stage.setScene(scene);
        stage.show();
    }

}