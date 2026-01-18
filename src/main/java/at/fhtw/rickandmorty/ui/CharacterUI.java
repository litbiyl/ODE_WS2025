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
import java.util.Objects;

public class CharacterUI {
    public void openCharacterWindow(Character c, ObservableList<Episode> epData, List<String> style) {
        Stage charStage = new Stage();
        charStage.setTitle("Character Sheet");
        charStage.getIcons().add(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/media/appicon.png")))
        );

        ImageView charImage = new ImageView();
        charImage.setFitWidth(100);
        charImage.setFitHeight(100);
        charImage.setPreserveRatio(true);
        charImage.setImage(new Image(c.getImage(), false));

        Label lblName = new Label("Name: " + c.getName());
        Label lblStatus = new Label("Status: " + c.getStatus());
        Label lblSpecies = new Label("Species: " + c.getSpecies());
        Label lblGender = new Label("Gender: " + c.getGender());
        String originName = (c.getOrigin() != null) ? c.getOrigin().getName() : "Unknown";
        Label lblOrigin = new Label("Origin: " + originName);
        String locationName = (c.getLocation() != null) ? c.getLocation().getName() : "Unknown";
        Label lblLocation = new Label("Location: " + locationName);
        Label lblEpisodes = new Label("Episodes:");

        Label lblType = null;
        if (c.getType() != null && !c.getType().isEmpty()) {
            lblType = new Label("Type: " + c.getType());
        }

        ListView<String> epList = new ListView<>();
        epList.setFocusTraversable(false);

        List<Integer> epIds = new ArrayList<>();

        if (c.getEpisode() != null) {
            for (String epUrl : c.getEpisode()) {
                try {
                    String epId = epUrl.substring(epUrl.lastIndexOf("/") + 1);
                    epIds.add(Integer.parseInt(epId));
                } catch (NumberFormatException | StringIndexOutOfBoundsException ignored) {
                }
            }
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

        if (lblType != null) {
            sheetBox.getChildren().add(4, lblType);
        }
        VBox epBox = new VBox(10, lblEpisodes, epList);
        epBox.setPadding(new Insets(10));
        SplitPane splitPane = new SplitPane(sheetBox, epBox);

        Scene charScene = new Scene(splitPane, 600, 400);
        charScene.getStylesheets().addAll(style);
        charStage.setScene(charScene);
        charStage.show();
    }
}
