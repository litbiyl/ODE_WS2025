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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CharacterUI {
    public void openCharacterWindow(Character c, ObservableList<Episode> epData, List<String> style) {
        Stage charStage = new Stage();
        charStage.setTitle("Character Sheet");

        ImageView charImage = new ImageView();
        charImage.setFitWidth(100);
        charImage.setFitHeight(100);
        charImage.setPreserveRatio(true);
        charImage.setImage(new Image(c.getImage(), false));

        String epNames = "";

        Label lblName = new Label("Name: " + c.getName());
        Label lblStatus = new Label("Status: " + c.getStatus());
        Label lblSpecies = new Label("Species: " + c.getSpecies());
        Label lblGender = new Label("Gender: " + c.getGender());
        Label lblOrigin = new Label("Origin: " + c.getOrigin().getName());
        Label lblLocation = new Label("Location: " + c.getLocation().getName());
        Label lblEpisodes = new Label("Episodes:");

        ListView<String> epList = new ListView<>();
        epList.setFocusTraversable(false);

        List<Integer> epIds = new ArrayList<>();

        for (String epUrl : c.getEpisode()) {
            String epId = epUrl.substring(epUrl.lastIndexOf("/") + 1);
            epIds.add(Integer.parseInt(epId));
        }

        for (int id : epIds) {
            for (Episode ep : epData) {
                if (ep.getId() == id) {
                    epList.getItems().add(ep.getName());
                    break;
                }
            }
        }

        VBox sheetBox = new VBox(10, charImage, lblName, lblStatus, lblSpecies, lblGender, lblOrigin, lblLocation, lblEpisodes, epList);
        sheetBox.setPadding(new Insets(10));
        VBox epBox = new VBox(10, lblEpisodes, epList);
        epBox.setPadding(new Insets(10));
        SplitPane splitPane = new SplitPane(sheetBox, epBox);

        Scene charScene = new Scene(splitPane, 600, 400);
        charScene.getStylesheets().addAll(style);
        charStage.setScene(charScene);
        charStage.show();
    }
}
