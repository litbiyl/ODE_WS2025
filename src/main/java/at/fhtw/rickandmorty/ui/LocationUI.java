package at.fhtw.rickandmorty.ui;

import at.fhtw.rickandmorty.series.Location;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LocationUI {
    public void openLocationWindow(Location l, List<String> style) {
        Stage locStage = new Stage();
        locStage.setTitle("Location Sheet");

        Label lblName = new Label("Name: " + l.getName());
        Label lblType = new Label("Type: " + l.getType());
        Label lblDimension = new Label("Dimension: " + l.getDimension());
        Label lblResidents = new Label("Residents: " + l.getResidents());

        VBox sheetBox = new VBox(10, lblName, lblType, lblDimension, lblResidents);
        sheetBox.setPadding(new Insets(10));

        Scene locScene = new Scene(sheetBox, 300, 200);
        locScene.getStylesheets().addAll(style);
        locStage.setScene(locScene);
        locStage.show();
    }
}
