package com.ZenyWallet.Service;

import com.ZenyWallet.model.UserRepository;
import com.ZenyWallet.model.User;

public class WalletService {
    private UserRepository db;

    public WalletService(UserRepository db) {
        this.db = db;
    }

    public UserRepository getDB() {
        return db;
    }

    public Boolean userExist(int UserID) {
        User user = db.getUserByID(UserID);
        return user != null;
    }
}
