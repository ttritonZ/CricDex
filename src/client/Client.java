package client;

import java.io.IOException;
import data.database.Club;
import data.network.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import util.NetworkUtil;
import java.util.List;

public class Client extends Application {
    private Stage stage;
    private NetworkUtil networkUtil;
    private TransferWindowRefreshThread refreshThread;

    private void connectToServer() throws Exception {
        String serverAddress = "127.0.0.1";
        int serverPort = 45045;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLoginPage();
    }

    private void showLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/ui/clubLoginWindow.fxml"));
        Parent root = fxmlLoader.load();
        ClubLoginWindowController controller = fxmlLoader.getController();
        controller.setClient(this);
        controller.init();
        Scene scene = new Scene(root);
        stage.setOnCloseRequest(e -> stage.close());
        stage.setTitle("Home");
        stage.setX(375);
        stage.setY(80);
        stage.setScene(scene);
        stage.show();
    }

    private void showClubHomePage(String clubName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/ui/clubHomeWindow.fxml"));
        Parent root = fxmlLoader.load();
        ClubHomeWindowController controller = fxmlLoader.getController();
        controller.init(this, clubName);
        Scene scene = new Scene(root);
        stage.setOnCloseRequest(e -> {
            e.consume();
            logoutClub(clubName);
        });
        stage.setTitle(clubName);
        stage.setScene(scene);
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }

    public void showRegistrationWindow(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/ui/clubRegWindow.fxml"));
        Parent root = fxmlLoader.load();
        ClubRegistrationWindowController controller = fxmlLoader.getController();
        controller.setClient(this);
        controller.setUserNameLabelText(username);

        Scene scene = new Scene(root);

        stage.setOnCloseRequest(e -> {
            try {
                showLoginPage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        stage.setTitle("Registration");
        stage.setX(375);
        stage.setY(80);
        stage.setScene(scene);
        stage.show();
    }

    public void loginClub(String username, String password) {
        LoginInfo loginInfo = new LoginInfo(MessageHeader.LOGIN, username, password);
        try {
            networkUtil.write(loginInfo);
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (b) {
                    showClubHomePage(username);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Login window",
                            "Login is unsuccessful.\nClub session may already be in progress.\nOr, registration of this club has not been completed yet.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void registerClub(String username, String password) {
        LoginInfo loginInfo = new LoginInfo(MessageHeader.REGISTER, username, password);
        try {
            networkUtil.write(loginInfo);
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (b) {
                    showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Registration window",
                            "Registration is successful");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Registration window",
                            "Registration is unsuccessful.\nClub may have already been registered.");
                }
                showLoginPage();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void logoutClub(String clubName) {
        try {
            networkUtil.write(new Message(MessageHeader.LOGOUT, clubName));
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (b) {
                    interruptRefreshThread();
                    showLoginPage();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Logout window", "Logout is unsuccessful.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean sellPlayer(String playerName, int playerPrice) {
        try {
            networkUtil.write(new SaleInfo(MessageHeader.SELL, playerName, playerPrice));
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (!b) {
                    showAlert(Alert.AlertType.ERROR, "Error", playerName, "Player could not be sold!");
                }
                return b;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean buyPlayer(String playerName, String clubName) {
        try {
            networkUtil.write(new BuyInfo(MessageHeader.BUY, playerName, clubName));
            Object obj = networkUtil.read();
            if (obj instanceof Boolean) {
                Boolean b = (Boolean) obj;
                if (!b) {
                    showAlert(Alert.AlertType.ERROR, "Error", playerName,
                            "Player is unavailable!\nThis player may have already been bought.");
                }
                return b;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Club loadClubFromServer(String clubName) {
        try {
            networkUtil.write(new Message(MessageHeader.CLUB_INFO, clubName));
            Object obj = networkUtil.read();
            if (obj instanceof Club) {
                return (Club) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<?> loadClubList() {
        try {
            networkUtil.write(new Message(MessageHeader.CLUB_LIST, null));
            Object obj = networkUtil.read();
            if (obj instanceof List) {
                return (List<?>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    synchronized public List<?> loadTransferList() {
        try {
            networkUtil.write(new Message(MessageHeader.TRANSFER_WINDOW, null));
            Object obj = networkUtil.read();
            if (obj instanceof List) {
                return (List<?>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    synchronized public void startRefreshThread(ClubHomeWindowController clubHomeWindowController) {
        refreshThread = new TransferWindowRefreshThread(clubHomeWindowController);
    }

    synchronized public void interruptRefreshThread() {
        if (refreshThread != null)
            refreshThread.getThread().interrupt();
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
