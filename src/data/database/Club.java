package data.database;

import java.io.Serializable;
import java.util.*;

public class Club implements Serializable {
    private String name;
    private List<Player> players;
    private int budget;
    private String imgSrc;

    public Club() {
        players = new ArrayList<>();
    }

    public Club(Player player) {
        players = new ArrayList<>();
        setName(player.getClub());
        addPlayer(player);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setImgSource("/img/logos/" + name.replace(' ', '_') + ".png");
    }

    public int getPlayerCount() {
        return players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getImgSource() {
        return imgSrc;
    }

    public void setImgSource(String imgSource) {
        this.imgSrc = imgSource;
    }

    public List<String> getCountryList() {
        Set<String> countrySet = new LinkedHashSet<>();
        this.players.forEach(e -> countrySet.add(e.getCountry()));
        return new ArrayList<>(countrySet);
    }

    public List<String> getPositionList() {
        Set<String> positionSet = new LinkedHashSet<>();
        this.players.forEach(e -> positionSet.add(e.getPosition()));
        return new ArrayList<>(positionSet);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getMaxSalaryPlayers() {
        List<Player> playerList = new ArrayList<>();

        int salary = players.get(0).getWeeklySalary();
        for (Player player : players) {
            if (player.getWeeklySalary() > salary) {
                salary = player.getWeeklySalary();
            }
        }

        for (Player player : players) {
            if (salary == player.getWeeklySalary()) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> getMaxAgePlayers() {
        List<Player> playerList = new ArrayList<>();

        int age = players.get(0).getAge();
        for (Player player : players) {
            if (player.getAge() > age) {
                age = player.getAge();
            }
        }

        for (Player player : players) {
            if (age == player.getAge()) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public List<Player> getMaxHeightPlayers() {
        List<Player> playerList = new ArrayList<>();

        double height = players.get(0).getHeight();
        for (Player player : players) {
            if (player.getHeight() > height) {
                height = player.getHeight();
            }
        }

        for (Player player : players) {
            if (height == player.getHeight()) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    public long getTotalYearlySalary() {
        long sum = 0;
        for (Player player : players) {
            sum += 52L * player.getWeeklySalary();
        }
        return sum;
    }

    public boolean checkNumber(int jerseyNum) {
        if(jerseyNum == -1)
        {
            return false;
        }
        for (Player player : players) {
            if (player.getJerseyNum() == jerseyNum) {
                return true;
            }
        }
        return false;
    }

    public void removePlayer(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equalsIgnoreCase(playerName)) {
                players.remove(i);
                return;
            }
        }
    }

    public String getSlogan() {
        switch (name) {
            case "Kolkata Knight Riders":
                return "#KORBOLORBOJEETBO!!";
            case "Lucknow Super Giants":
                return "#ABAPNABARIHAI!!";
            case "Chennai Super Kings":
                return "#WHISTLEPODU!!";
            case "Delhi Capitals":
                return "#YEHHAINAYIDILLI!!";
            case "Rajasthan Royals":
                return "#HALLABOL!!";
            case "Gujarat Titans":
                return "#AAVEDE!!";
            case "Punjab Kings":
                return "#SADDAPUNJAB!!";
            case "Royal Challengers Bangalore":
                return "#PLAYBOLD!!";
            case "Sunrisers Hyderabad":
                return "#KEEPRISING!!";
            case "Mumbai Indians":
                return "#DUNIYAHILADENGEHUM!!";
            default:
                return "";
        }
    }

    public String getPrimaryColor() {
        switch (name) {
            case "Kolkata Knight Riders":
                return "#3A225D";
            case "Lucknow Super Giants":
                return "#13297C";
            case "Chennai Super Kings":
                return "#FFCC03";
            case "Delhi Capitals":
                return "#0040A4";
            case "Rajasthan Royals":
                return "#1327AA";
            case "Gujarat Titans":
                return "#1B2133";
            case "Punjab Kings":
                return "#D71A20";
            case "Royal Challengers Bangalore":
                return "#D22526";
            case "Sunrisers Hyderabad":
                return "#E65901";
            case "Mumbai Indians":
                return "#0F3D8B";
            default:
                return "";
        }
    }

    public String getSecondaryColor() {
        switch (name) {
            case "Kolkata Knight Riders":
                return "#F7D54E";
            case "Lucknow Super Giants":
                return "#D1171A";
            case "Chennai Super Kings":
                return "#0065B3";
            case "Delhi Capitals":
                return "#DA2D30";
            case "Rajasthan Royals":
                return "#E40594";
            case "Gujarat Titans":
                return "#C3A65A";
            case "Punjab Kings":
                return "#EBDA94";
            case "Royal Challengers Bangalore":
                return "#1F2531";
            case "Sunrisers Hyderabad":
                return "#242424";
            case "Mumbai Indians":
                return "#FBD665";
            default:
                return "";
        }
    }
}