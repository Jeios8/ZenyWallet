package com.ZenyWallet.view;

import java.util.Scanner;

public class WalletView {
    private final Scanner scanner;

    public WalletView(){
        this.scanner = new Scanner(System.in);
    }

    public void displayMessage(String msg) {
        System.out.println(msg);
    }

    public void displayHeader(String title) {
        System.out.println("\n----------------------------");
        System.out.println(title);
        System.out.println("----------------------------\n");
    }

    public void displayMenu(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public int getUserChoice() {
        System.out.print("\nPlease choose an option: ");
        int choice = 0;
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine();
        } else {
            scanner.nextLine();
        }
        return choice;
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        String userInput = null;
        if (scanner.hasNext()) {
            userInput = scanner.nextLine();
        } else {
            scanner.nextLine();
        }
        return userInput;
    }

    public double getAmountInput(String prompt) {
        System.out.print(prompt);
        double amount = -1;
        if (scanner.hasNextDouble()) {
            amount = scanner.nextDouble();
            scanner.nextLine();
        } else {
            scanner.nextLine();
        }
        return amount;
    }
}
