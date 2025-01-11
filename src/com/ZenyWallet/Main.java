package com.ZenyWallet;

import com.ZenyWallet.model.Database;
import com.ZenyWallet.model.WalletService;
import com.ZenyWallet.presenter.WalletPresenter;
import com.ZenyWallet.view.WalletView;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        WalletView walletView = new WalletView();
        WalletService walletService = new WalletService(db);
        WalletPresenter walletPresenter = new WalletPresenter(walletView, walletService);

        walletPresenter.start();
    }
}
