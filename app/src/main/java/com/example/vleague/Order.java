package com.example.vleague;

import java.io.Serializable;

public class Order implements Serializable {
    String logo1, logo2;
    int round, price;
    String name1, name2, date, local, time, seat;
    int quantity, totalPrice;
    String dateBooking, Status, cusName;

    public Order() {
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getLogo1() {
        return logo1;
    }

    public String getLogo2() {
        return logo2;
    }

    public int getRound() {
        return round;
    }

    public int getPrice() {
        return price;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getDate() {
        return date;
    }

    public String getLocal() {
        return local;
    }

    public String getTime() {
        return time;
    }

    public String getSeat() {
        return seat;
    }

    public void setLogo1(String logo1) {
        this.logo1 = logo1;
    }

    public void setLogo2(String logo2) {
        this.logo2 = logo2;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public String getStatus() {
        return Status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
