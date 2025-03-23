package client;

import java.io.IOException;
import javafx.scene.control.Label;

import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PlayerCardController {
    
    @FXML
    private AnchorPane cardPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Button infoButton;

    @FXML
    private Button sellButton;

    @FXML
    private Line line;

    private Player player;
    private ClubHomeWindowController clubHomeWindowController;

    @FXML
    void sellPlayer(ActionEvent event) {
        if (player.isInTransferList()) {
            boolean b = showPlayerBuyConfirmationWindow();
            if(b) clubHomeWindowController.buyPlayer(player);
        } else {
            boolean b = showPlayerSaleConfirmationWindow();
            if (b) clubHomeWindowController.sellPlayer(player.getName(), player.getPrice());
        }
    }

    private boolean showPlayerSaleConfirmationWindow() {
        boolean b = false;
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Sale Confirmation");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ui/saleConfirmationWindow.fxml"));

            Parent root = fxmlLoader.load();

            SaleConfirmationWindowController controller = fxmlLoader.getController();
            controller.setPlayer(this.player);
            controller.loadData();
            controller.setStage(window);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

            b = controller.isSaleConfirm();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    private boolean showPlayerBuyConfirmationWindow() {
        boolean b = false;
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Buy Confirmation");

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ui/buyConfirmationWindow.fxml"));

            Parent root = fxmlLoader.load();

            BuyConfirmationWindowController controller = fxmlLoader.getController();
            controller.setPlayer(this.player);
            controller.loadData();
            controller.setStage(window);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

            b = controller.isBuyConfirm();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    @FXML
    void showPlayerDetails(ActionEvent event) {

        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(player.getName());

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ui/playerProfileWindow.fxml"));
            Parent root = fxmlLoader.load();

            PlayerProfileController playerProfile = fxmlLoader.getController();
            playerProfile.setData(player);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setData(Player player) {
        this.player = player;
        nameLabel.setText(player.getName());
        nameLabel.setTextFill(javafx.scene.paint.Color.web(player.getPrimaryColor()));
        positionLabel.setText(player.getPosition());
        positionLabel.setTextFill(javafx.scene.paint.Color.web(player.getPrimaryColor()));
        sellButton.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        sellButton.setStyle("-fx-background-color: " + player.getPrimaryColor() + ";");
        infoButton.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        infoButton.setStyle("-fx-background-color: " + player.getPrimaryColor() + ";");
        if(player.getJerseyNum() != -1) {
            numberLabel.setText("#" + player.getJerseyNum());
        }
        else {
            numberLabel.setText("");
        }
        numberLabel.setTextFill(javafx.scene.paint.Color.web(player.getPrimaryColor()));
        line.setStyle("-fx-stroke: " + player.getPrimaryColor() + ";");
        cardPane.setStyle("-fx-background-color: " + player.getSecondaryColor() + ";");

        if (player.isInTransferList()) {
            sellButton.setText("BUY");
        } else {
            sellButton.setText("SELL");
        }
    }

    public ClubHomeWindowController getClubHomeWindowController() {
        return clubHomeWindowController;
    }

    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }
}
