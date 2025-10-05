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
import javafx.application.Platform;

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

        portfolio.add(new Coin("bitcoin","Bitcoin", 0.0, 0.5));
        portfolio.add(new Coin("ethereum","Ethereum", 0.0, 10.0));
        portfolio.add(new Coin("dogecoin","Doge Coin", 0.0, 30.0));

        coinTableView.setItems(portfolio);

        updateTotalValue();
        refreshPrices();
    }

    @FXML
    private void refreshPrices() {
        new Thread(() -> {
       for( Coin coin : portfolio)
       {
        double newPrice = ApiService.getPrice(coin.getId()); 
        Platform.runLater(() -> {
            coin.setPrice(newPrice);
            updateTotalValue();
        });
       }
    }).start();
}

private void updateTotalValue()
{
    double total = 0.0;
    for(Coin coin: portfolio)
    {
        total += coin.getValue();
    }

    totalValueLabel.setText(String.format("Total Value: $%.4f", total));
}
}