package data.database;

import javafx.scene.image.Image;
import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private int jerseyNum;
    private int weeklySalary;
    private int price;
    private String imgSrc;
    private boolean isInTransferList;

    public Player() {
        imgSrc = "/img/players/unknown.png";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setImgSrc("/img/players/" + name.replace(' ', '_') + ".png");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getJerseyNum() {
        return jerseyNum;
    }

    public void setJerseyNum(int jerseyNum) {
        this.jerseyNum = jerseyNum;
    }

    public int getWeeklySalary() {
        return weeklySalary;
    }

    public void setWeeklySalary(int weeklySalary) {
        this.weeklySalary = weeklySalary;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        try {
            new Image(getClass().getResourceAsStream(imgSrc));
            this.imgSrc = imgSrc;
        } catch (Exception e) {
            this.imgSrc = "/img/players/unknown.png";
        }
    }

    public boolean isInTransferList() {
        return isInTransferList;
    }

    public void setInTransferList(boolean isInTransferList) {
        this.isInTransferList = isInTransferList;
    }

    public String getPrimaryColor() {
        switch (club) {
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
        switch (club) {
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

    @Override
    public String toString() {
        return String.format(
                "Name: %s\nCountry: %s\nAge: %d\nHeight: %.2f\nClub: %s\nPosition: %s\nNumber: %d\nWeekly Salary: %d",
                name, country, age, height, club, position, jerseyNum, weeklySalary);
    }

    public String toFileFormat() {
        if (jerseyNum == -1) {
            return String.format("%s,%s,%d,%.2f,%s,%s,,%d", name, country, age, height, club, position, weeklySalary);
        }
        return String.format("%s,%s,%d,%.2f,%s,%s,%d,%d", name, country, age, height, club, position, jerseyNum,
                weeklySalary);
    }
}