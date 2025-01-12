package com.ZenyWallet.viewmodel;

import com.ZenyWallet.Service.WalletService;
import com.ZenyWallet.view.WalletView;

public class WalletViewModel {
    private final WalletView walletView;
    private final WalletService walletService;

    public WalletViewModel(WalletView walletView, WalletService walletService) {
        this.walletView = walletView;
        this.walletService = walletService;
    }

    public void start() {
        while (true) {
            walletView.displayHeader();
            walletView.displayMessage("1. Login\n2. Exit\n");
            int response = walletView.getIntInput("Please choose an option (1 or 2): ");
            if (response == 1) {
                login();
                break;
            } else if (response == 2) {
                walletView.displayMessage("See you next time.");
                break;
            } else {
                walletView.displayMessage("Invalid input.");
            }
        }
    }

    public void login() {
        walletView.displayHeader();
        int userID;
        int pin;
        while (true) {
            userID = walletView.getIntInput("Enter User ID: ");
            if (walletService.userExist(userID)) {
                pin = walletView.getIntInput("Enter PIN: ");
            }
        }

    }
}
