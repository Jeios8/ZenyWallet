package com.ZenyWallet;

import com.ZenyWallet.model.UserRepository;
import com.ZenyWallet.view.WalletView;
import com.ZenyWallet.viewmodel.WalletViewModel;

public class Main {
    public static void main(String[] args) {
        String db_url = "jdbc:sqlite:src/resources/users.db";
        UserRepository userRepository = new UserRepository(db_url);
        WalletView walletView = new WalletView();
        WalletViewModel walletViewModel = new WalletViewModel(walletView, userRepository);

        walletViewModel.run();
    }
}
