package client;

import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SaleConfirmationWindowController {
    
    @FXML
    private AnchorPane salePane;
    
    @FXML
    private Label nameLabel;

    @FXML
    private Label setPriceLabel;

    @FXML
    private TextField priceTextField;
    
    @FXML
    private Button clearButton;

    @FXML
    private Button confirmSaleButton;

    @FXML
    private Button undoSaleButton;

    private Player player;
    private boolean isSaleConfirm;
    private Stage stage;

    @FXML
    void clearPriceTextField(ActionEvent event) {
        priceTextField.setText("");
    }

    @FXML
    void confirmSale(ActionEvent event) {
        try {
            int price = Integer.parseInt(priceTextField.getText());
            player.setPrice(price);
            isSaleConfirm = true;
            this.stage.close();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Sale Request Failed!");
            a.setContentText("Please input a real value as price.");
            a.showAndWait();
        }
    }

    @FXML
    void undoSale(ActionEvent event) {
        isSaleConfirm = false;
        this.stage.close();
    }

    public void loadData()
    {
        salePane.setStyle("-fx-background-color: " + player.getSecondaryColor());
        nameLabel.setText(player.getName().toUpperCase());
        nameLabel.setTextFill(javafx.scene.paint.Color.web(player.getPrimaryColor()));
        setPriceLabel.setTextFill(javafx.scene.paint.Color.web(player.getPrimaryColor()));
        clearButton.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        clearButton.setStyle("-fx-background-color: " + player.getPrimaryColor());
        confirmSaleButton.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        confirmSaleButton.setStyle("-fx-background-color: " + player.getPrimaryColor());
        undoSaleButton.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        undoSaleButton.setStyle("-fx-background-color: " + player.getPrimaryColor());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isSaleConfirm() {
        return isSaleConfirm;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
