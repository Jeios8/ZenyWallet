package com.ZenyWallet.model;

import java.util.HashMap;
import com.ZenyWallet.model.User;

public class Database {
    private HashMap<Integer, User> userDB;

    public Database() {
        userDB = new HashMap<>();

        initDatabase();
    }

    public User getUserByID(int UserID) {
        return userDB.get(UserID);
    }

    private void initDatabase() {
        userDB.put(412435, new User(412435,7452,"Chris Sandoval", 32000.00));
        userDB.put(264863, new User(264863,1349,"Marc Yim", 1000));
    }
}
