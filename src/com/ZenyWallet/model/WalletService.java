package com.ZenyWallet.model;

public class WalletService {
    private Database db;

    public WalletService(Database db) {
        this.db = db;
    }

    public Database getDB() {
        return db;
    }

    public Boolean userExist(int UserID) {
        User user = db.getUserByID(UserID);
        return user != null;
    }
}
