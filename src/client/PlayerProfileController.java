package client;

import java.net.URL;
import java.util.ResourceBundle;

import data.database.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class PlayerProfileController implements Initializable{

    @FXML
    private VBox detailsVbox;

    @FXML
    private HBox nameHbox;

    @FXML
    private Label nameLabel;

    @FXML
    private HBox detailsHbox;

    @FXML
    private Label positionLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label clubLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label ageText;

    @FXML
    private Label ageLabel;

    @FXML
    private Label heightText;

    @FXML
    private Label heightLabel;

    @FXML
    private Label salaryText;

    @FXML
    private Label salaryLabel;

    @FXML
    private Line line;

    @FXML
    private VBox imageVbox;

    @FXML
    private ImageView playerImage;

    public void setData(Player player)
    {
        detailsVbox.setStyle("-fx-background-color:" + player.getPrimaryColor());
        playerImage.setImage(new Image(getClass().getResourceAsStream(player.getImgSrc())));
        nameLabel.setText(player.getName().toUpperCase());
        nameLabel.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        positionLabel.setText(player.getPosition().toUpperCase());
        positionLabel.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        clubLabel.setText(player.getClub().toUpperCase());
        clubLabel.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        if(player.getJerseyNum() != -1)
        {
            numberLabel.setText("#"+player.getJerseyNum());
        }
        else
        {
            numberLabel.setText("");
        }
        numberLabel.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        ageLabel.setText(player.getAge() + " YEARS");
        ageLabel.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        ageText.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        countryLabel.setText(player.getCountry().toUpperCase());
        countryLabel.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        salaryLabel.setText("$" + player.getWeeklySalary());
        salaryLabel.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        salaryText.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        heightLabel.setText(player.getHeight() + " METERS");
        heightLabel.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        heightText.setTextFill(javafx.scene.paint.Color.web(player.getSecondaryColor()));
        line.setStyle("-fx-stroke: " + player.getSecondaryColor() + ";");
        imageVbox.setStyle("-fx-background-color: " + player.getSecondaryColor() + ";");
        
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
