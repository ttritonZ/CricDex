package client;

import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BuyConfirmationWindowController {
    
    @FXML
    private AnchorPane buyPane;
    
    @FXML
    private Label nameLabel;

    @FXML
    private Label buyAtLabel;

    @FXML
    private Label buyPriceLabel;

    @FXML
    private Button confirmBuyButton;

    private Player player;
    private boolean isBuyConfirm;
    private Stage stage;


    @FXML
    void confirmBuy(ActionEvent event) {
        try {
            isBuyConfirm = true;
            this.stage.close();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Buy Request Failed!");
            a.showAndWait();
        }
    }
    
    public void loadData()
    {
        buyPane.setStyle("-fx-background-color: " + player.getSecondaryColor());
        nameLabel.setText(player.getName().toUpperCase());
        nameLabel.setTextFill(javafx.scene.paint.Color.web(player.getPrimaryColor()));
        buyPriceLabel.setText("$" + String.format("%,d", player.getPrice()));
        buyPriceLabel.setTextFill(javafx.scene.paint.Color.web(player.getPrimaryColor()));
        buyAtLabel.setTextFill(javafx.scene.paint.Color.web(player.getPrimaryColor()));
        confirmBuyButton.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        confirmBuyButton.setStyle("-fx-background-color: " + player.getPrimaryColor());

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isBuyConfirm() {
        return isBuyConfirm;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
