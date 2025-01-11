package com.ZenyWallet.model;

public class User {
    private int userID;
    private int pin;
    private String name;
    private double balance;

    public User (int userID, int pin, String name, double balance) {
        this.userID = userID;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public int getUserID() {
        return userID;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public double deductBalance(double balance) {
        this.balance -= balance;
        return this.balance;
    }

    public double addBalance(double balance) {
        this.balance += balance;
        return this.balance;
    }
}
