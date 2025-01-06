package client;

import data.database.Club;
import data.database.Database;
import data.database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;


public class ClubHomeWindowController {

    @FXML
    private ImageView clubLogoImage;

    @FXML
    private ImageView appLogoImage;

    @FXML
    private Label clubNameLabel;

    @FXML
    private Label slogan;

    @FXML
    private VBox leftVbox;

    @FXML
    private VBox midVbox;

    @FXML
    private VBox rightVbox;

    @FXML
    private HBox leftTopHbox;

    @FXML
    private HBox leftBottomHbox;

    @FXML
    private Button buyPlayerButton;

    @FXML
    private HBox midTopHbox;

    @FXML
    private HBox playerListHbox;

    @FXML
    private HBox midBottomHbox;

    @FXML
    private TextField searchByNameTextField;

    @FXML
    private Button resetPlayerNameButton;

    @FXML
    private Button searchByNameButton;

    @FXML
    private HBox rightTopHbox;

    @FXML
    private HBox rightHbox2;

    @FXML
    private Label searchFilterLabel;

    @FXML
    private HBox rightHbox3;

    @FXML
    private Label countryLabel;

    @FXML
    private TreeView<CheckBox> filterTreeCountry;

    @FXML
    private HBox rightHbox4;

    @FXML
    private Label positionLabel;

    @FXML
    private TreeView<CheckBox> filterTreePosition;

    @FXML
    private HBox rightHbox5;

    @FXML
    private Label ageRangeLabel;

    @FXML
    private TextField ageFromTextField;

    @FXML
    private TextField ageToTextField;

    @FXML
    private HBox rightHbox6;

    @FXML
    private Label salaryRangeLabel;

    @FXML
    private TextField salaryFromTextField;

    @FXML
    private TextField salaryToTextField;

    @FXML
    private HBox rightHbox7;

    @FXML
    private Label heightRangeLabel;

    @FXML
    private TextField heightFromTextField;

    @FXML
    private TextField heightToTextField;

    @FXML
    private HBox rightBottomHbox;

    @FXML
    private Button applyFiltersButton;

    @FXML
    private Button resetFiltersButton;

    @FXML
    private Label budgetLabel;

    @FXML
    private Label budgetValueLabel;

    @FXML
    private Label refreshLabel;

    @FXML
    private ChoiceBox<String> refreshChoiceBox;

    @FXML
    private MenuButton menu;

    @FXML
    private Menu statsMenu;

    @FXML
    private MenuItem maxAgeMenuItem;

    @FXML
    private MenuItem maxHeightMenuItem;

    @FXML
    private MenuItem maxSalaryMenuItem;

    @FXML
    private MenuItem yearlySalaryMenuItem;

    @FXML
    private MenuItem logoutMenuItem;

    private Club club;
    private String clubName;
    private String logoImgSource;
    private List<Player> playerListOnDisplay;
    private Client client;
    volatile private int refreshRate;

    @FXML
    void showTransferWindow(ActionEvent event) {
        if (buyPlayerButton.getText().equals("BUY PLAYERS")) {
            client.startRefreshThread(this);
            refreshChoiceBox.setVisible(true);
            refreshLabel.setVisible(true);
            budgetLabel.setVisible(true);
            budgetValueLabel.setVisible(true);
            buyPlayerButton.setText("HOME");
        } else {
            client.interruptRefreshThread();
            refreshChoiceBox.setVisible(false);
            refreshLabel.setVisible(false);
            budgetLabel.setVisible(false);
            buyPlayerButton.setText("BUY PLAYERS");
            loadPlayerCards(this.club.getPlayers());
        }
    }

    void loadTransferWindow() {
        List<?> players = this.client.loadTransferList();
        if (players != null) {
            List<Player> playerList = new ArrayList<>();
            for (Object e : players) {
                if (e instanceof Player && !((Player) e).getClub().equals(this.clubName)) {
                    playerList.add((Player) e);
                }
            }
            loadPlayerCards(playerList);
        }

    }

    @FXML
    void searchPlayerByName(ActionEvent event) {
        String playerName = searchByNameTextField.getText().trim();
        Database db = new Database();
        db.addPlayer(club.getPlayers());
        Player player = db.searchByName(playerName);
        db.setPlayerList(new ArrayList<>());
        if (player != null) {
            db.getPlayerList().add(player);
        }
        loadPlayerCards(db.getPlayerList());
    }

    @FXML
    void resetPlayerNameTextField(ActionEvent event) {
        searchByNameTextField.setText("");
        loadPlayerCards(club.getPlayers());
    }

    private void applyFiltersCountry(Database db) {
        for (TreeItem<CheckBox> item : filterTreeCountry.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                for (Player player : db.searchByCountry(item.getValue().getText())) {
                    db.getPlayerList().remove(player);
                }
            }
        }
    }

    private void applyFiltersPosition(Database db) {
        for (TreeItem<CheckBox> item : filterTreePosition.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                for (Player player : db.searchByPosition(item.getValue().getText())) {
                    db.getPlayerList().remove(player);
                }
            }
        }
    }

    private void applyFiltersAge(Database db) {
        int l, h;
        try {
            l = Integer.parseInt(ageFromTextField.getText());
        } catch (Exception e) {
            l = 0;
            ageFromTextField.setText(String.valueOf(l));
        }
        try {
            h = Integer.parseInt(ageToTextField.getText());
        } catch (Exception e) {
            h = club.getMaxAgePlayers().get(0).getAge();
            ageToTextField.setText(String.valueOf(h));
        }
        db.setPlayerList(db.searchByAge(l, h));
    }

    private void applyFiltersSalary(Database db) {
        int l, h;
        try {
            l = Integer.parseInt(salaryFromTextField.getText());
        } catch (Exception e) {
            l = 0;
            salaryFromTextField.setText(String.valueOf(l));
        }
        try {
            h = Integer.parseInt(salaryToTextField.getText());
        } catch (Exception e) {
            h = club.getMaxSalaryPlayers().get(0).getWeeklySalary();
            salaryToTextField.setText(String.valueOf(h));
        }
        db.setPlayerList(db.searchByWeeklySalary(l, h));
    }

    private void applyFiltersHeight(Database db) {
        double l, h;
        try {
            l = Double.parseDouble(heightFromTextField.getText());
        } catch (Exception e) {
            l = 0;
            heightFromTextField.setText(String.valueOf(l));
        }
        try {
            h = Double.parseDouble(heightToTextField.getText());
        } catch (Exception e) {
            h = club.getMaxHeightPlayers().get(0).getHeight();
            heightToTextField.setText(String.valueOf(h));
        }
        db.setPlayerList(db.searchByHeight(l, h));
    }

    @FXML
    void applyFilters(ActionEvent event) {
        Database db = new Database();
        db.addPlayer(club.getPlayers());
        applyFiltersCountry(db);
        applyFiltersPosition(db);
        applyFiltersAge(db);
        applyFiltersSalary(db);
        applyFiltersHeight(db);

        loadPlayerCards(db.getPlayerList());
    }

    @FXML
    void resetFilters(ActionEvent event) {

        for (TreeItem<CheckBox> item : filterTreeCountry.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                item.getValue().setSelected(true);
            }
        }

        for (TreeItem<CheckBox> item : filterTreePosition.getRoot().getChildren()) {
            if (!item.getValue().isSelected()) {
                item.getValue().setSelected(true);
            }
        }

        ageFromTextField.setText("");
        ageToTextField.setText("");

        heightFromTextField.setText("");
        heightToTextField.setText("");

        salaryFromTextField.setText("");
        salaryToTextField.setText("");

        applyFilters(event);
    }

    private void makeRefreshRateChoiceBox() {
        refreshChoiceBox.getItems().addAll(
                "5 SECONDS",
                "10 SECONDS",
                "15 SECONDS",
                "30 SECONDS",
                "1 MINUTE",
                "2 MINUTES",
                "5 MINUTES");
        refreshChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (oldValue != null && !oldValue.equals(newValue)) {
                        this.refreshRate = changeRefreshRate(newValue);
                        client.interruptRefreshThread();
                        client.startRefreshThread(this);
                    }
                });
        refreshChoiceBox.setValue("5 SECONDS");
        this.refreshRate = 5;
    }

    int changeRefreshRate(String newValue) {
        String[] choice = newValue.split(" ");
        int rate = Integer.parseInt(choice[0]);
        if (choice[1].charAt(0) == 'M') {
            rate *= 60;
        }
        return rate;
    }

    private void makeFilterTree() {
        makeFilterTreeCountry();
        makeFilterTreePosition();
    }

    private void makeFilterTreePosition() {
        TreeItem<CheckBox> root;
        root = new TreeItem<>();
        root.setExpanded(true);

        this.club.getPositionList().forEach(e -> makeBranchFilterTree(e, root));

        filterTreePosition.setRoot(root);
        filterTreePosition.setShowRoot(false);
    }

    private void makeFilterTreeCountry() {
        TreeItem<CheckBox> root;
        root = new TreeItem<>();
        root.setExpanded(true);

        club.getCountryList().forEach(e -> makeBranchFilterTree(e, root));

        filterTreeCountry.setRoot(root);
        filterTreeCountry.setShowRoot(false);
    }

    private TreeItem<CheckBox> makeBranchFilterTree(String title, TreeItem<CheckBox> parent) {
        CheckBox checkBox = new CheckBox(title);
        checkBox.setSelected(true);
        TreeItem<CheckBox> item = new TreeItem<>(checkBox);
        parent.getChildren().add(item);
        return item;
    }

    private void initClubInfo() {
        logoImgSource = this.club.getImgSource();
        clubLogoImage.setImage(new Image(getClass().getResourceAsStream(logoImgSource)));
        clubNameLabel.setText(this.clubName.toUpperCase());
        clubNameLabel.setTextFill(javafx.scene.paint.Color.web(this.club.getSecondaryColor()));
        slogan.setText(club.getSlogan());
        slogan.setTextFill(javafx.scene.paint.Color.web(this.club.getSecondaryColor()));
        buyPlayerButton.setStyle("-fx-background-color: " + club.getSecondaryColor());
        buyPlayerButton.setTextFill(javafx.scene.paint.Color.web(club.getPrimaryColor()));
        budgetLabel.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        budgetValueLabel.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        leftTopHbox.setStyle("-fx-background-color: " + club.getSecondaryColor());
        leftBottomHbox.setStyle("-fx-background-color: " + club.getPrimaryColor());
        midTopHbox.setStyle("-fx-background-color: " + club.getPrimaryColor());
        playerListHbox.setStyle("-fx-background-color: " + club.getPrimaryColor());
        midBottomHbox.setStyle("-fx-background-color: " + club.getSecondaryColor());
        resetPlayerNameButton.setStyle("-fx-background-color: " + club.getPrimaryColor());
        resetPlayerNameButton.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        searchByNameButton.setStyle("-fx-background-color: " + club.getPrimaryColor());
        searchByNameButton.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        rightVbox.setStyle("-fx-background-color: " + club.getPrimaryColor());
        rightTopHbox.setStyle("-fx-background-color: " + club.getSecondaryColor());
        menu.setStyle("-fx-background-color: " + club.getPrimaryColor());
        menu.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        refreshLabel.setTextFill(javafx.scene.paint.Color.web(club.getPrimaryColor()));
        refreshChoiceBox.setStyle("-fx-background-color: " + club.getPrimaryColor());
        searchFilterLabel.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        countryLabel.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        positionLabel.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        ageRangeLabel.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        salaryRangeLabel.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        heightRangeLabel.setTextFill(javafx.scene.paint.Color.web(club.getSecondaryColor()));
        applyFiltersButton.setStyle("-fx-background-color: " + club.getSecondaryColor());
        applyFiltersButton.setTextFill(javafx.scene.paint.Color.web(club.getPrimaryColor()));
        resetFiltersButton.setStyle("-fx-background-color: " + club.getSecondaryColor());
        resetFiltersButton.setTextFill(javafx.scene.paint.Color.web(club.getPrimaryColor()));
        ageFromTextField.setStyle("-fx-background-color: " + club.getSecondaryColor());
        ageFromTextField.setStyle("-fx-text-fill: " + club.getPrimaryColor());
        ageToTextField.setStyle("-fx-background-color: " + club.getSecondaryColor());
        ageToTextField.setStyle("-fx-text-fill: " + club.getPrimaryColor());
        salaryFromTextField.setStyle("-fx-background-color: " + club.getSecondaryColor());
        salaryFromTextField.setStyle("-fx-text-fill: " + club.getPrimaryColor());
        salaryToTextField.setStyle("-fx-background-color: " + club.getSecondaryColor());
        salaryToTextField.setStyle("-fx-text-fill: " + club.getPrimaryColor());
        heightFromTextField.setStyle("-fx-background-color: " + club.getSecondaryColor());
        heightFromTextField.setStyle("-fx-text-fill: " + club.getPrimaryColor());
        heightToTextField.setStyle("-fx-background-color: " + club.getSecondaryColor());
        heightToTextField.setStyle("-fx-text-fill: " + club.getPrimaryColor());
        filterTreeCountry.setStyle("-fx-background-color: " + club.getSecondaryColor());
        filterTreePosition.setStyle("-fx-background-color: " + club.getSecondaryColor());
    }

    private void loadPlayerCards(List<Player> playerList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ui/playerListView.fxml"));
            Parent root = fxmlLoader.load();

            PlayerListController playerListController = fxmlLoader.getController();
            playerListController.setClubHomeWindowController(this);
            playerListController.loadPlayerCards(playerList);

            playerListHbox.getChildren().clear();
            playerListHbox.getChildren().add(root);

            this.playerListOnDisplay = new ArrayList<>(playerList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadClubData() {
        this.club = client.loadClubFromServer(this.clubName);
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @FXML
    void logoutClub(ActionEvent event) {
        client.logoutClub(this.clubName);
    }

    public void sellPlayer(String playerName, int playerPrice) {
        boolean b = client.sellPlayer(playerName, playerPrice);
        if (b) {
            this.club.removePlayer(playerName);
            makeFilterTree();
            loadPlayerCards(this.club.getPlayers());
        }
    }

    public void buyPlayer(Player player) {
        boolean b = client.buyPlayer(player.getName(), this.clubName);
        if (b) {
            this.playerListOnDisplay.remove(player);
            player.setInTransferList(false);
            player.setClub(this.clubName);
            this.club.addPlayer(player);
            makeFilterTree();
            loadPlayerCards(this.playerListOnDisplay);
        }
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    @FXML
    void listMaxAgePlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxAgePlayers());
    }

    @FXML
    void listMaxHeightPlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxHeightPlayers());
    }

    @FXML
    void listMaxSalaryPlayers(ActionEvent event) {
        loadPlayerCards(club.getMaxSalaryPlayers());
    }

    @FXML
    void showTotalYearlySalary(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Total Yearly Salary");
        a.setHeaderText(this.clubName.toUpperCase());
        a.setContentText("Total yearly salary is " + String.format("%,d", this.club.getTotalYearlySalary()) + " USD");
        a.showAndWait();
    }

    public void init(Client client, String clubName) {
        this.client = client;
        this.clubName = clubName;
        loadClubData();
        initClubInfo();
        loadPlayerCards(club.getPlayers());
        makeFilterTree();
        makeRefreshRateChoiceBox();
    }

}
