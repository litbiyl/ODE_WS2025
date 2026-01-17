package at.fhtw.rickandmorty.ui;

import at.fhtw.rickandmorty.series.Episode;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class EpisodeUI {
    public void openEpisodeWindow(Episode c, List<String> style) {
        Stage epStage = new Stage();
        epStage.setTitle("Episode Sheet");

        Label lblName = new Label("Name: " + c.getName());
        Label lblEpisode = new Label("Episode: " + c.getEpisode());
        Label lblAirDate = new Label("Air date: " + c.getAir_date());
        Label lblCharacters = new Label("Characters: " + c.getCharacters());

        VBox sheetBox = new VBox(10, lblEpisode, lblName, lblAirDate, lblCharacters);
        sheetBox.setPadding(new Insets(10));

        Scene epScene = new Scene(sheetBox, 300, 200);
        epScene.getStylesheets().addAll(style);
        epStage.setScene(epScene);
        epStage.show();
    }
}
