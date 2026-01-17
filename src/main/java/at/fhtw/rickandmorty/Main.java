package at.fhtw.rickandmorty;

import at.fhtw.rickandmorty.logging.Logger;
import at.fhtw.rickandmorty.ui.RickAndMortyUI;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends javafx.application.Application
{
    @Override
    public void start(Stage rickAndMortyStage)
    {
        RickAndMortyUI rickAndMortyUI = new RickAndMortyUI();
        Scene rickAndMortyScene = new Scene(rickAndMortyUI, 1200, 800);
        rickAndMortyStage.setScene(rickAndMortyScene);
        rickAndMortyStage.setTitle("Rick & Morty");
        rickAndMortyStage.show();
        rickAndMortyStage.getIcons().add(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/media/appicon.png")))
        );
        Logger.log("INFO", "Rick & Morty started.");

        rickAndMortyStage.setOnCloseRequest(event -> {
            Logger.log("INFO", "Rick & Morty closed by user.");
        });
    }

}
