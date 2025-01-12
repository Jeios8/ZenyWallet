package com.ZenyWallet;

import com.ZenyWallet.model.UserRepository;
import com.ZenyWallet.Service.WalletService;
import com.ZenyWallet.viewmodel.WalletViewModel;
import com.ZenyWallet.view.WalletView;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        WalletView walletView = new WalletView();
        WalletService walletService = new WalletService(userRepository);
        WalletViewModel walletPresenter = new WalletViewModel(walletView, walletService);

        walletPresenter.start();
    }
}
