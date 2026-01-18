package at.fhtw.rickandmorty.ui;

import at.fhtw.rickandmorty.series.Character;
import at.fhtw.rickandmorty.series.Episode;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EpisodeUI {
    public void openEpisodeWindow(Episode ep, ObservableList<Character> charData, List<String> style) {
        Stage epStage = new Stage();
        epStage.setTitle("Episode Sheet");
        epStage.getIcons().add(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/media/appicon.png")))
        );

        Label lblName = new Label("Name: " + ep.getName());
        Label lblEpisode = new Label("Episode: " + ep.getEpisode());
        Label lblAirDate = new Label("Air date: " + ep.getAir_date());
        Label lblCharacters = new Label("Characters:");

        ListView<String> charList = new ListView<>();
        charList.setFocusTraversable(false);

        List<Integer> charIds = new ArrayList<>();

        if (ep.getCharacters() != null) {
            for (String charUrl : ep.getCharacters()) {
                try {
                    String charId = charUrl.substring(charUrl.lastIndexOf("/") + 1);
                    charIds.add(Integer.parseInt(charId));
                } catch (NumberFormatException | StringIndexOutOfBoundsException ignored) {
                }
            }
        }

        for (int id : charIds) {
            for (Character c : charData) {
                if (c.getId() == id) {
                    charList.getItems().add(c.getName());
                    break;
                }
            }
        }

        VBox sheetBox = new VBox(10, lblEpisode, lblName, lblAirDate);
        sheetBox.setPadding(new Insets(10));
        VBox charBox = new VBox(10, lblCharacters, charList);
        charBox.setPadding(new Insets(10));
        SplitPane splitPane = new SplitPane(sheetBox, charBox);


        Scene epScene = new Scene(splitPane, 600, 400);
        epScene.getStylesheets().addAll(style);
        epStage.setScene(epScene);
        epStage.show();
    }
}
