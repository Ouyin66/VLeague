package com.example.vleague.Class;

import java.io.Serializable;

public class User implements Serializable {
    private long id;
    private String HoTen;
    private String Email;
    private String Phone;
    private String Password;
    private long Role;

    public User() {
    }

    public User(long id, String hoTen, String email, String phone, String password, long role) {
        this.id = id;
        HoTen = hoTen;
        Email = email;
        Phone = phone;
        Password = password;
        Role = role;
    }

    public long getId() {
        return id;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPassword() {
        return Password;
    }

    public long getRole() {
        return Role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setRole(long role) {
        Role = role;
    }
}
