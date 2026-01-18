package at.fhtw.rickandmorty.ui;

import at.fhtw.rickandmorty.logging.Logger;
import at.fhtw.rickandmorty.mapper.*;
import at.fhtw.rickandmorty.network.PageData;
import at.fhtw.rickandmorty.network.TCPClient;
import at.fhtw.rickandmorty.series.Character;
import at.fhtw.rickandmorty.series.Episode;
import at.fhtw.rickandmorty.series.Location;
import at.fhtw.rickandmorty.series.World;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

import static at.fhtw.rickandmorty.network.ApiConstants.*;
import static at.fhtw.rickandmorty.network.HTTPResponse.extractJson;

public class RickAndMortyUI extends BorderPane
{
    private final Serde<Character> charSerde = new CharacterSerde();
    private final Serde<Episode> epSerde = new EpisodeSerde();
    private final Serde<Location> locSerde = new LocationSerde();

    private final PageDataSerde pageSerde = new PageDataSerde();
    private boolean isDarkMode = false;

    private final TableView<Character> charTable = new TableView<>();
    private final ObservableList<Character> charData = FXCollections.observableArrayList();
    private final FilteredList<Character> charFiltered = new FilteredList<>(charData, p -> true);
    private final SortedList<Character> charSorted = new SortedList<>(charFiltered);

    private final TableView<Episode> epTable = new TableView<>();
    private final ObservableList<Episode> epData = FXCollections.observableArrayList();
    private final SortedList<Episode> epSorted = new SortedList<>(epData);

    private final TableView<Location> locTable = new TableView<>();
    private final ObservableList<Location> locData = FXCollections.observableArrayList();
    private final SortedList<Location> locSorted = new SortedList<>(locData);

    Label charStatusLabel = new Label("Characters loaded (0/?)");
    Label epStatusLabel = new Label("Episodes loaded (0/?)");
    Label locStatusLabel = new Label("Locations loaded (0/?)");
    Label errorLabel = new Label("Placeholder error text");

    public RickAndMortyUI()
    {
        initializeUI();
        loadInitialData();
    }

    private void initializeUI()
    {
        Button btnThemeToggle = new Button("Toggle Dark Mode");

        ChoiceBox<String> statusFilter = new ChoiceBox<>();
        statusFilter.getItems().addAll("All", "Alive", "Dead", "unknown");
        statusFilter.setValue("All");

        Pane topSpacer = new Pane();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);
        Label statusLabel = new Label("Character Status");
        ToolBar topToolBar = new ToolBar(statusLabel, statusFilter, topSpacer, btnThemeToggle);
        setTop(topToolBar);

        errorLabel.setTextFill(Color.RED);
        errorLabel.setId("errorLabel");
        Pane bottomSpacer = new Pane();
        HBox.setHgrow(bottomSpacer, Priority.ALWAYS);
        HBox bottomBar = new HBox(charStatusLabel, epStatusLabel, locStatusLabel, bottomSpacer, errorLabel);
        bottomBar.setSpacing(80);
        bottomBar.setPadding(new Insets(10, 30, 10, 30));
        setBottom(bottomBar);

        TabPane tabPane = new TabPane();

        Tab charTab = new Tab("Characters");
        charTab.setClosable(false);

        TableColumn<Character, Integer> charIdCol = new TableColumn<>("ID");
        charIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        charIdCol.setMaxWidth(50);

        TableColumn<Character, String> charNameCol = new TableColumn<>("Name");
        charNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Character, String> charStatusCol = new TableColumn<>("Status");
        charStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        charStatusCol.setPrefWidth(30);

        TableColumn<Character, String> charSpeciesCol = new TableColumn<>("Species");
        charSpeciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));
        charSpeciesCol.setPrefWidth(45);

        TableColumn<Character, String> charGenderCol = new TableColumn<>("Gender");
        charGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        charGenderCol.setPrefWidth(35);

        TableColumn<Character, String> charOriginCol = new TableColumn<>("Origin");
        charOriginCol.setCellValueFactory(cellData -> {
            var origin = cellData.getValue().getOrigin();
            return new SimpleStringProperty(origin != null ? origin.getName() : "Unknown");
        });
        charOriginCol.setPrefWidth(60);

        TableColumn<Character, String> charLocationCol = new TableColumn<>("Location");
        charLocationCol.setCellValueFactory(cellData -> {
            var location = cellData.getValue().getLocation();
            return new SimpleStringProperty(location != null ? location.getName() : "Unknown");
        });

        charTable.getColumns().addAll(charIdCol, charNameCol, charStatusCol, charSpeciesCol, charGenderCol, charOriginCol, charLocationCol);
        charSorted.comparatorProperty().bind(charTable.comparatorProperty());
        charTable.setItems(charSorted);
        charTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        charTab.setContent(charTable);

        Tab epTab = new Tab("Episodes");
        epTab.setClosable(false);

        TableColumn<Episode, Integer> epIdCol = new TableColumn<>("ID");
        epIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        epIdCol.setMaxWidth(60);

        TableColumn<Episode, String> epNameCol = new TableColumn<>("Name");
        epNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        epNameCol.setMaxWidth(350);
        TableColumn<Episode, String> epEpisodeCol = new TableColumn<>("Episode");
        epEpisodeCol.setCellValueFactory(new PropertyValueFactory<>("Episode"));
        epEpisodeCol.setMaxWidth(120);

        TableColumn<Episode, String> epAirDateCol = new TableColumn<>("Air Date");
        epAirDateCol.setCellValueFactory(new PropertyValueFactory<>("air_date"));

        epTable.getColumns().addAll(epIdCol, epEpisodeCol, epNameCol, epAirDateCol);
        epSorted.comparatorProperty().bind(epTable.comparatorProperty());
        epTable.setItems(epSorted);
        epTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        epTab.setContent(epTable);

        Tab locTab = new Tab("Locations");
        locTab.setClosable(false);

        TableColumn<Location, Integer> locIdCol = new TableColumn<>("ID");
        locIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        locIdCol.setMaxWidth(50);

        TableColumn<Location, String> locNameCol = new TableColumn<>("Name");
        locNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Location, String> locTypeCol = new TableColumn<>("Type");
        locTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Location, String> locDimensionCol = new TableColumn<>("Dimension");
        locDimensionCol.setCellValueFactory(new PropertyValueFactory<>("Dimension"));

        locTable.getColumns().addAll(locIdCol, locNameCol, locTypeCol, locDimensionCol);
        locSorted.comparatorProperty().bind(locTable.comparatorProperty());
        locTable.setItems(locSorted);
        locTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        locTab.setContent(locTable);

        tabPane.getTabs().add(charTab);
        tabPane.getTabs().add(epTab);
        tabPane.getTabs().add(locTab);

        setCenter(tabPane);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) ->
        {
            // log which tab the user switched to
            Logger.log("INFO", "Tab changed to: " + newTab.getText());

            if (newTab == charTab)
            {
                statusFilter.setVisible(true);
                statusLabel.setVisible(true);
            } else
            {
                statusFilter.setVisible(false);
                statusLabel.setVisible(false);

            }
        });
        btnThemeToggle.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 2)
            {
                Logger.log("INFO", "Easter Egg: Hello Kitty Mode Activated!");

                this.getStylesheets().clear();
                String kittyPath = getClass().getResource("/themes/hellokitty.css").toExternalForm();
                this.getStylesheets().add(kittyPath);

                btnThemeToggle.setText("Hello Kitty Mode!");
                return;
            }

            if (event.getClickCount() == 1)
            {
                this.getStylesheets().clear();
                if (!isDarkMode)
                {
                    String darkPath = getClass().getResource("/themes/dark.css").toExternalForm();
                    this.getStylesheets().add(darkPath);
                    btnThemeToggle.setText("Toggle Light Mode");
                    isDarkMode = true;
                    Logger.log("INFO", "Theme changed to: Dark Mode");
                } else
                {
                    String lightPath = getClass().getResource("/themes/light.css").toExternalForm();
                    this.getStylesheets().add(lightPath);
                    btnThemeToggle.setText("Toggle Dark Mode");
                    isDarkMode = false;
                    Logger.log("INFO", "Theme changed to: Light Mode");
                }
            }

        });

        statusFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            Logger.log("INFO", "Filter changed to: " + newVal);
            charFiltered.setPredicate(character ->
            {
                if (newVal == null || newVal.equals("All")) return true;
                return character.getStatus().equalsIgnoreCase(newVal);
            });
        });

        charTable.setRowFactory(tv -> {
            TableRow<Character> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Character character = row.getItem();
                    Logger.log("INFO", "Character details opened: " + character.getName());
                    CharacterUI characterUI = new CharacterUI();
                    characterUI.openCharacterWindow(character, epData, this.getStylesheets());
                }
            });

            return row;
        });

        epTable.setRowFactory(tv -> {
            TableRow<Episode> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Episode episode = row.getItem();
                    Logger.log("INFO", "Episode details opened: " + episode.getName());
                    EpisodeUI episodeUI = new EpisodeUI();
                    episodeUI.openEpisodeWindow(episode, charData, this.getStylesheets());
                }
            });

            return row;
        });

        locTable.setRowFactory(tv -> {
            TableRow<Location> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Location location = row.getItem();
                    Logger.log("INFO", "Location details opened: " + location.getName());
                    LocationUI locationUI = new LocationUI();
                    locationUI.openLocationWindow(location, charData, this.getStylesheets());
                }
            });

            return row;
        });
    }


    private void loadInitialData() {
        Thread charThread = new Thread(() ->
        {
            try
            {
                fetchPage(CHARACTER_PATH, charData, charSerde, charStatusLabel, "Characters");
            } catch (RuntimeException e)
            {
                Platform.runLater(() -> errorLabel.setText("ERROR: " + e.getMessage()));
                Logger.log("ERROR", "Failed to fetch characters: " + e.getMessage());
            }
        });

        Thread epThread = new Thread(() ->
        {
            try
            {
                fetchPage(EPISODE_PATH, epData, epSerde, epStatusLabel, "Episodes");
            } catch (RuntimeException e)
            {
                Platform.runLater(() -> errorLabel.setText("ERROR: " + e.getMessage()));
                Logger.log("ERROR", "Failed to fetch episodes: " + e.getMessage());
            }
        });

        Thread locThread = new Thread(() ->
        {
            try
            {
                fetchPage(LOCATION_PATH, locData, locSerde, locStatusLabel, "Locations");
            } catch (RuntimeException e)
            {
                Platform.runLater(() -> errorLabel.setText("ERROR: " + e.getMessage()));
                Logger.log("ERROR", "Failed to fetch locations: " + e.getMessage());
            }
        });

        charThread.setDaemon(true);
        charThread.start();

        epThread.setDaemon(true);
        epThread.start();

        locThread.setDaemon(true);
        locThread.start();
    }

    private <T extends World> void fetchPage(String path, ObservableList<T> oList, Serde<T> serde, Label statusLabel, String statusPrefix) {

        String response = TCPClient.get(API_HOST, API_PORT, path);
        String json = extractJson(response);
        if (json == null){
            return;
        }

        PageData meta = pageSerde.deserializePageData(json);
        int pageCount = meta.getPages();
        int itemCount = meta.getCount();

        Platform.runLater(() -> {
            statusLabel.setText(statusPrefix + " loaded " + "(0/" + itemCount + ")");
        });

        List<T> entities = serde.deserializeJsonList(json);
        oList.addAll(entities);
        entities.clear();

        Platform.runLater(() -> {
            statusLabel.setText(statusPrefix + " loaded " + "(" + oList.size() + "/" + itemCount + ")");
        });


        for (int i = 2; i <= pageCount; i++) {
            response = TCPClient.get(API_HOST, API_PORT, path + "/?page=" + i);
            json = extractJson(response);
            if (json == null) {
                continue;
            }

            entities = serde.deserializeJsonList(json);
            oList.addAll(entities);
            entities.clear();

            Platform.runLater(() -> {
                statusLabel.setText(statusPrefix + " loaded " + "(" + oList.size() + "/" + itemCount + ")");
            });
        }
    }
}