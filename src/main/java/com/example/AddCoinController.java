package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCoinController {
    
    @FXML
    private TextField coinId;

    @FXML
    private TextField amount;

    private Stage dialogStage;
    private boolean okClicked = false;
    private Coin newCoin;

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Coin getNewCoin() {
        return newCoin;
    }

    @FXML
    private void handleOk()
    {
        if(isInputValid())
        {
            String id = coinId.getText().toLowerCase();
            double holdings = Double.parseDouble(amount.getText());
            newCoin = new Coin(id, "", 0.0, holdings);
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (coinId.getText() == null || coinId.getText().isEmpty()) {
            errorMessage += "No valid coin ID!\n";
        }
        if (amount.getText() == null || amount.getText().isEmpty()) {
            errorMessage += "No valid amount!\n";
        } else {
            try {
                Double.parseDouble(amount.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid amount (must be a number)!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            System.err.println(errorMessage);
            return false;
        }
    }

}
