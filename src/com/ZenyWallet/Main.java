package com.ZenyWallet;

import com.ZenyWallet.view.WalletView;
import com.ZenyWallet.viewmodel.WalletViewModel;

public class Main {
    public static void main(String[] args) {

        WalletView walletView = new WalletView();
        WalletViewModel walletViewModel = new WalletViewModel(walletView);

        walletViewModel.run();
    }
}
