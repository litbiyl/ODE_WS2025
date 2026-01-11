module at.fhtw.rickandmorty {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.fhtw.rickandmorty to javafx.fxml;
    exports at.fhtw.rickandmorty;
}