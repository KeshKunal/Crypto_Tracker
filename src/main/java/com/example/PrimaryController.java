package com.example;

import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private void handleAddCoin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("add_coin.fxml"));
            GridPane page = (GridPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Coin");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddCoinController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                Coin newCoin = controller.getNewCoin();
                portfolio.add(newCoin);
                refreshPrices();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void refreshPrices() {
        new Thread(() -> {
            portfolio.forEach(coin -> {
                if (coin.getName().isEmpty()) {
                    String coinName = coin.getId().substring(0, 1).toUpperCase() + coin.getId().substring(1);
                    Platform.runLater(() -> coin.nameProperty().set(coinName));
                }
            });

            for (Coin coin : portfolio) {
                double newPrice = ApiService.getPrice(coin.getId());
                Platform.runLater(() -> {
                    coin.setPrice(newPrice);
                    updateTotalValue();
                });
            }
        }).start();
    }

    private void updateTotalValue() {
        double total = 0.0;
        for (Coin coin : portfolio) {
            total += coin.getValue();
        }
        totalValueLabel.setText(String.format("Total Value: $%.4f", total));
    }

}