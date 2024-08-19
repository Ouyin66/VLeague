package com.example.vleague.Class;

public class Bank {
    long id = -1;
    String NameBank, BankNum, BankCvv, BankDate;
    int BankMoney, BankCusId;

    public Bank() {
    }

    public Bank(long id, String nameBank, String bankNum, String bankCvv, String bankDate, int bankMoney, int bankCusId) {
        this.id = id;
        NameBank = nameBank;
        BankNum = bankNum;
        BankCvv = bankCvv;
        BankDate = bankDate;
        BankMoney = bankMoney;
        BankCusId = bankCusId;
    }

    public long getId() {
        return id;
    }

    public String getNameBank() {
        return NameBank;
    }

    public String getBankNum() {
        return BankNum;
    }

    public String getBankCvv() {
        return BankCvv;
    }

    public String getBankDate() {
        return BankDate;
    }

    public int getBankMoney() {
        return BankMoney;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNameBank(String nameBank) {
        NameBank = nameBank;
    }

    public void setBankNum(String bankNum) {
        BankNum = bankNum;
    }

    public void setBankCvv(String bankCvv) {
        BankCvv = bankCvv;
    }

    public void setBankDate(String bankDate) {
        BankDate = bankDate;
    }

    public void setBankMoney(int bankMoney) {
        BankMoney = bankMoney;
    }

    public int getBankCusId() {
        return BankCusId;
    }

    public void setBankCusId(int bankCusId) {
        BankCusId = bankCusId;
    }
}
