package data.database;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Player> playerList;
    private List<Country> countryList;
    private List<Club> clubList;

    public Database() {
        playerList = new ArrayList<>();
        countryList = new ArrayList<>();
        clubList = new ArrayList<>();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Club> getClubList() {
        return clubList;
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    public Player searchByName(String name) {
        for (Player player : playerList) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public List<Player> searchByCountryandClub(String country, String club) {
        List<Player> searchResult = new ArrayList<>();
        for (Player player : playerList) {
            if ((player.getClub().equalsIgnoreCase(club) || club.equalsIgnoreCase("any"))
                    && player.getCountry().equalsIgnoreCase(country)) {
                searchResult.add(player);
            }
        }
        return searchResult;
    }

    public List<Player> searchByCountry(String country) {
        List<Player> searchResult = new ArrayList<>();
        for (Player player : playerList) {
            if (player.getCountry().equalsIgnoreCase(country)) 
            {
                searchResult.add(player);
            }
        }
        return searchResult;
    }

    public List<Player> searchByClub(String club) {
        List<Player> searchResult = new ArrayList<>();
        for (Player player : playerList) {
            if (player.getClub().equalsIgnoreCase(club) || club.equalsIgnoreCase("any"))
            {
                searchResult.add(player);
            }
        }
        return searchResult;
    }

    public List<Player> searchByPosition(String position) {
        List<Player> searchResult = new ArrayList<>();
        for (Player player : playerList) {
            if (player.getPosition().equalsIgnoreCase(position)) {
                searchResult.add(player);
            }
        }
        return searchResult;
    }

    public int addPlayer(Player player) {
        Player p = searchByName(player.getName());
        if (p == null) {
            boolean b = checkClubValidity(player.getClub());
            if (!b) 
            {
                return -1;
            }
            b = checkDuplicateJerseyNumber(player.getClub(), player.getJerseyNum());
            if (b)
            {
                return -2;
            }
            modifyClubName(player);
            modifyCountryName(player);

            playerList.add(player);
            updateCountryList(player);
            updateClubList(player);
            return 1;
        }
        return 0;
    }

    public void addPlayer(List<Player> playerlist) {
        for (Player player : playerlist) {
            addPlayer(player);
        }
    }

    private void updateCountryList(Player player) {
        for (Country c : countryList) {
            if (c.getName().equalsIgnoreCase(player.getCountry())) {
                c.addPlayer(player);
                return;
            }
        }
        Country c = new Country(player);
        countryList.add(c);
    }

    public boolean checkClubValidity(String club) 
    {
        for (Club c: clubList) 
        {
            if (c.getName().equalsIgnoreCase(club)) 
            {
                return false;
            }
        }
        return true;
    }

    private void updateClubList(Player player) 
    {
        String club = player.getClub();
        for (Club c: clubList) 
        {
            if (c.getName().equalsIgnoreCase(club)) 
            {
                c.addPlayer(player);
                return;
            }
        }
        Club c = new Club(player);
        clubList.add(c);
    }

    public Club searchClub(String clubName) 
    {
        for (Club c: clubList) 
        {
            if (c.getName().equalsIgnoreCase(clubName)) 
            {
                return c;
            }
        }
        return null;
    }

    private void modifyClubName(Player player) 
    {
        for (Club club: clubList) 
        {
            if (club.getName().equalsIgnoreCase(player.getClub())) {
                player.setClub(club.getName());
                return;
            }
        }
    }

    private void modifyCountryName(Player player) {
        for (Country country: countryList) 
        {
            if (country.getName().equalsIgnoreCase(player.getCountry())) 
            {
                player.setCountry(country.getName());
                return;
            }
        }
    }

    public boolean checkDuplicateJerseyNumber(String club, int jerseyNum) 
    {
        for (Club c: clubList) 
        {
            if (c.getName().equalsIgnoreCase(club)) 
            {
                return c.checkNumber(jerseyNum);
            }
        }
        return false;
    }

    public List<Player> searchByAge(int low, int high) 
    {
        List<Player> searchResult = new ArrayList<>();
        for (Player player : playerList) 
        {
            int age = player.getAge();
            if (age >= low && age <= high)
            {
                searchResult.add(player);
            }
        }
        return searchResult;
    }

    public List<Player> searchByHeight(double low, double high) 
    {
        List<Player> searchResult = new ArrayList<>();
        for (Player player : playerList) 
        {
            double height = player.getHeight();
            if (height >= low && height <= high)
            {
                searchResult.add(player);
            }
        }
        return searchResult;
    }

    public List<Player> searchByWeeklySalary(int low, int high) 
    {
        List<Player> searchResult = new ArrayList<>();
        for (Player player : playerList) 
        {
            int sal = player.getWeeklySalary();
            if (sal >= low && sal <= high)
            {
                searchResult.add(player);
            }
        }
        return searchResult;
    }

    public void removePlayerFromClub(String playerName) throws Exception 
    {
        Player player = searchByName(playerName);
        Club club = searchClub(player.getClub());
        club.removePlayer(playerName);
    }
}
