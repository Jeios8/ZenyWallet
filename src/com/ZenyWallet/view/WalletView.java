package com.ZenyWallet.view;

import java.util.Scanner;

public class WalletView {
    private final Scanner scanner;

    public WalletView() {
        scanner = new Scanner(System.in);
    }

    public void displayHeader() {
        System.out.println("=======================================");
        System.out.println("=      ZenyWallet Banking System      =");
        System.out.println("=======================================\n");
    }

    public void displayMainMenu() {
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Send Money");
        System.out.println("5. Exit\n");
    }

    public int getIntInput (String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid Input.");
                scanner.next();
            }
        }
    }

    public double getDoubleInput (String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                System.out.println("Invalid Input.");
                scanner.next();
            }
        }
    }

    public void displayMessage(String prompt) {
        System.out.println(prompt);
    }
}
