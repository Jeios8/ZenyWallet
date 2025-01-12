package com.ZenyWallet.model;

import java.util.HashMap;

public class UserRepository {
    private HashMap<String, User> users;

    public UserRepository() {
        users = new HashMap<>();

        users.put("412435", new User("412435","7452","Chris Sandoval", 32000.00));
        users.put("264863", new User("264863", "1349","Marc Yim", 1000.00));
        users.put("040810", new User("040810", "0408","R-John D.", 10000000.00));
    }
}
