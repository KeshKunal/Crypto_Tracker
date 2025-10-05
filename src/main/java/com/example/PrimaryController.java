package com.example;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrimaryController {

    @FXML
    private TableView<Coin> coinTableView;

    @FXML
    private TableColumn<Coin, String> nameColumn;

    @FXML
    private TableColumn<Coin, Number> priceColumn;

    @FXML
    private TableColumn<Coin, Number> holdingsColumn;

    @FXML
    private TableColumn<Coin, Number> valueColumn;

    @FXML
    private Label totalValueLabel;

    @FXML
    private Button addCoinButton;

    @FXML
    private Button refreshButton;

    private ObservableList<Coin> portfolio = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        holdingsColumn.setCellValueFactory(new PropertyValueFactory<>("holdings"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        portfolio.add(new Coin("Bitcoin", 68000.75, 0.5));
        portfolio.add(new Coin("Ethereum", 3500.21, 10.0));
        portfolio.add(new Coin("Doge Coin", 150.55, 30.0));

        coinTableView.setItems(portfolio);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
