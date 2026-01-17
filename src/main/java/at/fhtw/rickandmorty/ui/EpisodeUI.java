package at.fhtw.rickandmorty.ui;

import at.fhtw.rickandmorty.series.Episode;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EpisodeUI {
    public void openEpisodeWindow(Episode c) {
        Stage epStage = new Stage();
        epStage.setTitle("Episode Sheet");

        Label lblName = new Label("Name: " + c.getName());
        Label lblEpisode = new Label("Episode: " + c.getEpisode());
        Label lblAirDate = new Label("Air date: " + c.getAir_date());
        Label lblCharacters = new Label("Characters: " + c.getCharacters());

        VBox sheetBox = new VBox(10, lblEpisode, lblName, lblAirDate, lblCharacters);
        sheetBox.setPadding(new Insets(10));

        Scene epScene = new Scene(sheetBox, 300, 200);
        epStage.setScene(epScene);
        epStage.show();
    }
}
