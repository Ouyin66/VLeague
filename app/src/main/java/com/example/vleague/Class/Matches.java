package com.example.vleague.Class;

public class Matches {
    private long id, idTeam1, idTeam2;
    private String logo1, logo2;
    private String name1, name2;
    private String location;
    private int round;
    private String date;
    private String time;
    private int price;

    public Matches() {
    }

    public long getIdTeam1() {
        return idTeam1;
    }

    public long getIdTeam2() {
        return idTeam2;
    }

    public void setIdTeam1(long idTeam1) {
        this.idTeam1 = idTeam1;
    }

    public void setIdTeam2(long idTeam2) {
        this.idTeam2 = idTeam2;
    }

    public long getId() {
        return id;
    }

    public String getLogo1() {
        return logo1;
    }

    public String getLogo2() {
        return logo2;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getLocation() {
        return location;
    }

    public int getRound() {
        return round;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogo1(String logo1) {
        this.logo1 = logo1;
    }

    public void setLogo2(String logo2) {
        this.logo2 = logo2;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
