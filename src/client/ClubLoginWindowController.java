package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;

import java.io.IOException;

import java.util.List;

public class ClubLoginWindowController {

    @FXML
    private ImageView bgImage;

    @FXML
    private AnchorPane clubLoginPane;

    @FXML
    private Button clubLoginButton;

    @FXML
    private Button clubPwResetButton;

    @FXML
    private Label notRegYetLabel;

    @FXML
    private Button signUpButton;

    @FXML
    private Label clubLoginUsernameLabel;

    @FXML
    private ChoiceBox<String> clubLoginUsernameChoiceBox;

    @FXML
    private Label clubLoginPasswordLabel;

    @FXML
    private PasswordField clubLoginPwBox;

    @FXML
    private Label clubLabel;

    @FXML
    private Line clubLine;

    private Client client;

    @FXML
    void login(ActionEvent event) {
        String username = clubLoginUsernameChoiceBox.getValue();
        String password = clubLoginPwBox.getText();
        if (username == null || password.isBlank()) {
            showAlert("Login");
        } else {
            client.loginClub(username, password);
            reset(event);
        }
    }

    private void showAlert(String header) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(header + " not successful");
        a.setContentText("Username or password field cannot be empty.");
        a.showAndWait();
    }

    @FXML
    void register(ActionEvent event) {
        String username = clubLoginUsernameChoiceBox.getValue();
        if (username == null) {
            showAlert("Registration request");
        } else {
            try {
                client.showRegistrationWindow(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void reset(ActionEvent event) {
        clubLoginUsernameChoiceBox.setValue(null);
        clubLoginPwBox.setText("");
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void init() {
        initializeUsernameChoiceBox();
    }

    private void initializeUsernameChoiceBox() {
        List<?> clubList = client.loadClubList();
        clubList.forEach(e -> {
            if (e instanceof String) {
                clubLoginUsernameChoiceBox.getItems().add((String) e);
            }
        });
    }
}
