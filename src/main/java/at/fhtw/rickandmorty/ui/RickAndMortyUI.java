package at.fhtw.rickandmorty.ui;

import at.fhtw.rickandmorty.mapper.*;
import at.fhtw.rickandmorty.network.PageData;
import at.fhtw.rickandmorty.network.TCPClient;
import at.fhtw.rickandmorty.series.Character;
import at.fhtw.rickandmorty.series.Episode;
import at.fhtw.rickandmorty.series.Location;
import at.fhtw.rickandmorty.series.World;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.List;

import static at.fhtw.rickandmorty.network.HTTPResponse.extractJson;

public class RickAndMortyUI extends BorderPane
{
    Serde<Character> charSerde = new CharacterSerde();
    Serde<Episode> epSerde = new EpisodeSerde();
    Serde<Location> locSerde = new LocationSerde();

    PageDataSerde pageSerde = new PageDataSerde();

    private final TableView<Character> charTable = new TableView<>();
    private final ObservableList<Character> charData = FXCollections.observableArrayList();

    private final TableView<String> epTable = new TableView<>();
    private final ObservableList<String> epData = FXCollections.observableArrayList();

    private final TableView<String> locTable = new TableView<>();
    private final ObservableList<String> locData = FXCollections.observableArrayList();

    public RickAndMortyUI()
    {
        initializeUI();
        loadInitialData();
    }

    private void initializeUI()
    {
        Button btnThemeToggle = new Button("Toggle Dark Mode");

        ToolBar topToolBar = new ToolBar(new Pane(), btnThemeToggle);
        HBox.setHgrow(topToolBar.getItems().getFirst(), Priority.ALWAYS);
        setTop(topToolBar);

        // possible error message box
        ToolBar bottomToolBar = new ToolBar(new Pane());
        bottomToolBar.setPrefHeight(30);
        setBottom(bottomToolBar);

        TabPane tabPane = new TabPane();

        Tab charTab = new Tab("Characters");
        charTab.setClosable(false);

        TableColumn<Character, Integer> charIdCol = new TableColumn<>("ID");
        charIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Character, String> charNameCol = new TableColumn<>("Name");
        charNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Character, String> charStatusCol = new TableColumn<>("Status");
        charStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Character, String> charSpeciesCol = new TableColumn<>("Species");
        charSpeciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));

        TableColumn<Character, String> charTypeCol = new TableColumn<>("Type");
        charTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Character, String> charGenderCol = new TableColumn<>("Gender");
        charGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Character, String> charOriginCol = new TableColumn<>("Origin");
        charOriginCol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getOrigin().getName());
        });

        TableColumn<Character, String> charLocationCol = new TableColumn<>("Location");
        charLocationCol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getLocation().getName());
        });

        charTable.getColumns().addAll(charIdCol, charNameCol, charStatusCol, charSpeciesCol, charGenderCol, charOriginCol, charLocationCol);
        charTable.setItems(charData);
        charTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        charTab.setContent(charTable);

        Tab epTab = new Tab("Episodes");
        epTab.setClosable(false);

        TableColumn<String, Integer> epIdCol = new TableColumn<>("ID");
        epIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<String, String> epNameCol = new TableColumn<>("Name");
        epNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, String> epEpisodeCol = new TableColumn<>("Episode");
        epEpisodeCol.setCellValueFactory(new PropertyValueFactory<>("Episode"));

        TableColumn<String, String> epAirDateCol = new TableColumn<>("Air Date");
        epAirDateCol.setCellValueFactory(new PropertyValueFactory<>("air_date"));

        epTable.getColumns().addAll(epIdCol, epEpisodeCol, epNameCol, epAirDateCol);
        epTable.setItems(epData);
        epTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        epTab.setContent(epTable);

        Tab locTab = new Tab("Locations");
        locTab.setClosable(false);

        TableColumn<String, Integer> locIdCol = new TableColumn<>("ID");
        locIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<String, String> locNameCol = new TableColumn<>("Name");
        locNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, String> locTypeCol = new TableColumn<>("Type");
        locTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<String, String> locDimensionCol = new TableColumn<>("Dimension");
        locDimensionCol.setCellValueFactory(new PropertyValueFactory<>("Dimension"));

        TableColumn<String, String> locResidentsCol = new TableColumn<>("Residents");
        locResidentsCol.setCellValueFactory(new PropertyValueFactory<>("residents"));

        locTable.getColumns().addAll(locIdCol, locNameCol, locTypeCol, locDimensionCol, locResidentsCol);
        locTable.setItems(locData);
        locTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        locTab.setContent(locTable);

        tabPane.getTabs().add(charTab);
        tabPane.getTabs().add(epTab);
        tabPane.getTabs().add(locTab);

        setCenter(tabPane);

        // Logic of Dark/Light Mode Button
    }

    private void loadInitialData() {
        int port = 443;
        String baseURL = "rickandmortyapi.com";
        String charPath = "/api/character";
        String epPath   = "/api/episode";
        String locPath  = "/api/location";

        Thread charThread = new Thread(() -> {
            try {
                fetchPage(baseURL, port, charPath, charData, charSerde);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        charThread.setDaemon(true);
        charThread.start();
    }

    private <T extends World> void fetchPage(String baseURL, int port, String path, ObservableList<T> oList, Serde<T> serde) {

        String response = TCPClient.get(baseURL, port, path);
        String json = extractJson(response);
        if (json == null){
            return;
        }

        PageData meta = pageSerde.deserializePageData(json);
        int pageCount = meta.getPages();

        List<T> entities = serde.deserializeJsonList(json);
        oList.addAll(entities);
        entities.clear();

        for (int i = 2; i <= pageCount; i++) {
            response = TCPClient.get(baseURL, port, path + "/?page=" + i);
            json = extractJson(response);
            if (json == null) {
                continue;
            }

            entities = serde.deserializeJsonList(json);
            oList.addAll(entities);
            entities.clear();
        }
    }
}
