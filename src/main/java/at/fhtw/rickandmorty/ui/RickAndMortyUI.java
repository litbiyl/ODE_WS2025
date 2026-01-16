package at.fhtw.rickandmorty.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class RickAndMortyUI extends BorderPane
{

    private final TableView<String> charTable = new TableView<>();
    private final ObservableList<String> charData = FXCollections.observableArrayList();

    private final TableView<String> epTable = new TableView<>();
    private final ObservableList<String> epData = FXCollections.observableArrayList();

    private final TableView<String> locTable = new TableView<>();
    private final ObservableList<String> locData = FXCollections.observableArrayList();

    public RickAndMortyUI()
    {
        initializeUI();
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

        TableColumn<String, Integer> charIdCol = new TableColumn<>("ID");
        charIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<String, String> charNameCol = new TableColumn<>("Name");
        charNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, String> charStatusCol = new TableColumn<>("Status");
        charStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<String, String> charSpeciesCol = new TableColumn<>("Species");
        charSpeciesCol.setCellValueFactory(new PropertyValueFactory<>("species"));

        TableColumn<String, String> charTypeCol = new TableColumn<>("Type");
        charTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<String, String> charGenderCol = new TableColumn<>("Gender");
        charGenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<String, String> charOriginCol = new TableColumn<>("Origin");
        charOriginCol.setCellValueFactory( new PropertyValueFactory<>("origin"));

        TableColumn<String, String> charLocationCol = new TableColumn<>("Location");
        charLocationCol.setCellValueFactory( new PropertyValueFactory<>("location"));

        TableColumn<String, String> charEpisodeCol = new TableColumn<>("Episodes");
        charEpisodeCol.setCellValueFactory(new PropertyValueFactory<>("episode"));

        charTable.getColumns().addAll(charIdCol, charNameCol, charStatusCol, charSpeciesCol, charGenderCol, charOriginCol, charLocationCol, charEpisodeCol);
        charTable.setItems(charData);

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

        locTab.setContent(locTable);

        tabPane.getTabs().add(charTab);
        tabPane.getTabs().add(epTab);
        tabPane.getTabs().add(locTab);

        setCenter(tabPane);

        // Logic of Dark/Light Mode Button
    }
}