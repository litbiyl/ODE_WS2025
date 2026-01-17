package at.fhtw.rickandmorty.ui;

import at.fhtw.rickandmorty.series.Character;
import at.fhtw.rickandmorty.series.Location;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LocationUI {
    public void openLocationWindow(Location l, ObservableList<Character> charData, List<String> style) {
        Stage locStage = new Stage();
        locStage.setTitle("Location Sheet");

        Label lblName = new Label("Name: " + l.getName());
        Label lblType = new Label("Type: " + l.getType());
        Label lblDimension = new Label("Dimension: " + l.getDimension());
        Label lblResidents = new Label("Residents:");

        ListView<String> charList = new ListView<>();
        charList.setFocusTraversable(false);

        List<Integer> charIds = new ArrayList<>();

        for (String charUrl : l.getResidents()) {
            String charId = charUrl.substring(charUrl.lastIndexOf("/") + 1);
            charIds.add(Integer.parseInt(charId));
        }

        for (int id : charIds) {
            for (Character c : charData) {
                if (c.getId() == id) {
                    charList.getItems().add(c.getName());
                    break;
                }
            }
        }

        VBox sheetBox = new VBox(10, lblName, lblType, lblDimension, lblResidents);
        sheetBox.setPadding(new Insets(10));
        VBox epBox = new VBox(10, lblResidents, charList);
        epBox.setPadding(new Insets(10));
        SplitPane splitPane = new SplitPane(sheetBox, epBox);

        Scene locScene = new Scene(splitPane, 600, 400);
        locScene.getStylesheets().addAll(style);
        locStage.setScene(locScene);
        locStage.show();
    }
}
