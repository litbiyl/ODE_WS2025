module at.fhtw.rickandmorty {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires com.fasterxml.jackson.core;
    //requires at.fhtw.rickandmorty;

    opens at.fhtw.rickandmorty.series to com.fasterxml.jackson.databind;
    opens at.fhtw.rickandmorty.mapper to com.fasterxml.jackson.databind;

    exports at.fhtw.rickandmorty;
    exports at.fhtw.rickandmorty.series;
    exports at.fhtw.rickandmorty.mapper;
    exports at.fhtw.rickandmorty.network;
    opens at.fhtw.rickandmorty.network to com.fasterxml.jackson.databind;
}