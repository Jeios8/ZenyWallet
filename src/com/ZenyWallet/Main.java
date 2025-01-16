package com.ZenyWallet;

import com.ZenyWallet.model.User;
import com.ZenyWallet.model.UserRepository;
import com.ZenyWallet.view.WalletView;
import com.ZenyWallet.viewmodel.WalletViewModel;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String db_url = "jdbc:mysql://127.0.0.1:3306/user_schema";
        String db_username = "root";
        String db_password = "2243519278";
        UserRepository userRepository = new UserRepository(db_url, db_username, db_password);
        WalletView walletView = new WalletView();
        WalletViewModel walletViewModel = new WalletViewModel(walletView, userRepository);

        walletViewModel.run();
    }
}
