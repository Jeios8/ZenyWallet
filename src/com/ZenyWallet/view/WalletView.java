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
        if (scanner.hasNextInt()) return scanner.nextInt();
        scanner.nextLine();
        return 0;
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        if (scanner.hasNext()) return scanner.next();
        scanner.nextLine();
        return null;
    }

    public double getAmountInput(String prompt) {
        System.out.print(prompt);
        if (scanner.hasNextDouble()) return scanner.nextDouble();
        scanner.nextLine();
        return -1;
    }
}
