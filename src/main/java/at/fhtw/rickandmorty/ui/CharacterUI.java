package at.fhtw.rickandmorty.ui;

import at.fhtw.rickandmorty.series.Character;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CharacterUI {
    public void openCharacterWindow(Character c) {
        Stage charStage = new Stage();
        charStage.setTitle("Character Sheet");

        Label lblName = new Label("Name: " + c.getName());
        Label lblStatus = new Label("Status: " + c.getStatus());
        Label lblSpecies = new Label("Species: " + c.getSpecies());
        Label lblGender = new Label("Gender: " + c.getGender());
        Label lblOrigin = new Label("Origin: " + c.getOrigin().getName());
        Label lblLocation = new Label("Location: " + c.getLocation().getName());
        Label lblEpisodes = new Label("Episodes: " + c.getEpisode());

        VBox sheetBox = new VBox(10, lblName, lblStatus, lblSpecies, lblGender, lblOrigin, lblLocation, lblEpisodes);
        sheetBox.setPadding(new Insets(10));

        Scene charScene = new Scene(sheetBox, 300, 400);
        charStage.setScene(charScene);
        charStage.show();
    }
}
