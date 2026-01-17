package at.fhtw.rickandmorty;

import at.fhtw.rickandmorty.logging.Logger;
import at.fhtw.rickandmorty.ui.RickAndMortyUI;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        Logger.log("INFO", "Rick & Morty started.");
    }
}
