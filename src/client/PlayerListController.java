package client;

import java.io.IOException;
import java.util.List;

import data.database.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class PlayerListController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private ClubHomeWindowController clubHomeWindowController;

    public void loadPlayerCards(List<Player> playerList) {
        try {
            int row = 0;
            int col = 0;
            scrollPane.setStyle("-fx-background-color:" + clubHomeWindowController.getClub().getPrimaryColor() + ";");
            gridPane.setStyle("-fx-background-color:" + clubHomeWindowController.getClub().getPrimaryColor() + ";");
            anchorPane.setStyle("-fx-background-color:" + clubHomeWindowController.getClub().getPrimaryColor() + ";");
            for (Player player : playerList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/playerCard.fxml"));

                Parent card = fxmlLoader.load();
                
                PlayerCardController playerCardController = fxmlLoader.getController();
                playerCardController.setData(player);
                playerCardController.setClubHomeWindowController(this.clubHomeWindowController);

                gridPane.add(card, col, row++);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
                double width = 641;
                gridPane.setMinWidth(width);
                gridPane.setPrefWidth(width);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClubHomeWindowController getClubHomeWindowController() {
        return clubHomeWindowController;
    }

    public void setClubHomeWindowController(ClubHomeWindowController clubHomeWindowController) {
        this.clubHomeWindowController = clubHomeWindowController;
    }

}
