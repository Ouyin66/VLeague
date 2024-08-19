package com.example.vleague.Class;

import java.io.Serializable;

public class Ticket implements Serializable {
    public long id;
    public long id_cus;
    public String cus_name, cus_phone, cus_email;
    public long id_match;
    public String seating_area;
    public double total_price;
    public String booking_date;
    public int Quantity, StatusPay;

    public Ticket() {
    }

    public Ticket(long id, long id_cus, String cus_name, String cus_phone, String cus_email, long id_match, String seating_area, double total_price, String booking_date, int quantity, int statusPay) {
        this.id = id;
        this.id_cus = id_cus;
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
        this.cus_email = cus_email;
        this.id_match = id_match;
        this.seating_area = seating_area;
        this.total_price = total_price;
        this.booking_date = booking_date;
        Quantity = quantity;
        StatusPay = statusPay;
    }

    public int getStatusPay() {
        return StatusPay;
    }

    public String getCus_name() {
        return cus_name;
    }

    public String getCus_phone() {
        return cus_phone;
    }

    public String getCus_email() {
        return cus_email;
    }

    public long getId() {
        return id;
    }

    public long getId_cus() {
        return id_cus;
    }

    public long getId_match() {
        return id_match;
    }

    public String getSeating_area() {
        return seating_area;
    }

    public double getTotal_price() {
        return total_price;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId_cus(long id_cus) {
        this.id_cus = id_cus;
    }

    public void setId_match(long id_match) {
        this.id_match = id_match;
    }

    public void setSeating_area(String seating_area) {
        this.seating_area = seating_area;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public void setCus_phone(String cus_phone) {
        this.cus_phone = cus_phone;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }

    public void setStatusPay(int statusPay) {
        StatusPay = statusPay;
    }
}
