module at.fhtw.rickandmorty {
    requires java.desktop;
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires at.fhtw.rickandmorty;

    exports at.fhtw.rickandmorty;
    exports at.fhtw.rickandmorty.network;
}