package com.ZenyWallet.viewmodel;

import com.ZenyWallet.view.WalletView;

public class WalletViewModel {
    private WalletView walletView;

    public WalletViewModel(WalletView walletView) {
        this.walletView = walletView;
    }

    public void run() {
        // welcome page
        while (true) {
            walletView.displayMenu("Welcome to ZenyWallet", new String[]{"Login", "Exit"});
            switch (walletView.getUserChoice()) {
                case 1 -> login();
                case 2 -> {
                    walletView.displayMessage("Thank you for using ZenyWallet.");
                    return;
                }
                default -> walletView.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    public void login() {
        String userID = walletView.getUserInput("Enter UserID: ");
        if (userID != null && userID.matches("\\d{6}")) {
            walletView.displayMessage("Logged-in.");
        }
    }
}