package com.ZenyWallet.model;

public class User {
    private final String userID;
    private String pin;
    private final String name;
    private double balance;

    public User(String userID, String pin, String name, double balance) {
        this.userID = userID;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public String getUserID() {
        return userID;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double newBalance) {
        balance += newBalance;
    }

    public void deductBalance(double newBalance) {
        balance -= newBalance;
    }
}
