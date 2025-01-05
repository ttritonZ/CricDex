package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

public class ClubRegistrationWindowController {

    @FXML
    private ImageView bgImage;

    @FXML
    private Label clubEnterLabel;

    @FXML
    private Line line;
    
    @FXML
    private Label usernameLabel;

    @FXML
    private Label username;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField pwdField;

    @FXML
    private Label retypePassLabel1;

    @FXML
    private Label retypePassLabel2;

    @FXML
    private PasswordField rtypPwField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button resetButton;

    private Client client;

    @FXML
    void register(ActionEvent event) {
        String password = pwdField.getText();
        String retypePassword = rtypPwField.getText();
        if (password.isBlank() || retypePassword.isBlank()) {
            showAlert("Password field cannot be empty.");
        } else if (!password.equals(retypePassword)) {
            showAlert("Retyped password did not match.");
            reset(event);
        }
        else {
            client.registerClub(username.getText(), password);
        }
    }

    private void showAlert(String text) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText("Registration not successful");
        a.setContentText(text);
        a.showAndWait();
    }

    @FXML
    void reset(ActionEvent event) {
        pwdField.setText("");
        rtypPwField.setText("");
    }

    public String getUserNameLabelText() {
        return username.getText();
    }

    public void setUserNameLabelText(String userName) {
        this.username.setText(userName.toUpperCase());
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
