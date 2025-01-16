package com.ZenyWallet.viewmodel;

import com.ZenyWallet.model.User;
import com.ZenyWallet.model.UserRepository;
import com.ZenyWallet.view.WalletView;

import java.util.Scanner;

public class WalletViewModel {
    private final WalletView walletView;
    private final UserRepository userRepository;
    private User ACTIVE_USER;

    public WalletViewModel(WalletView walletView, UserRepository userRepository) {
        this.walletView = walletView;
        this.userRepository = userRepository;
    }

    public void run() {
        // welcome page
        while (true) {
            walletView.displayHeader("Welcome to ZenyWallet");
            walletView.displayMenu(new String[]{"Login", "Register", "Exit"});
            switch (walletView.getUserChoice()) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    walletView.displayMessage("Thank you for using ZenyWallet.");
                    return;
                }
                default -> walletView.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    public void login() {
        String userID;
        String pin;

        for (int i = 0; i < 3; i++) {
            walletView.displayMessage("\nEnter 0 to cancel login.");
            userID = walletView.getUserInput("Enter UserID: ");
            // Check if the user entered "0" to cancel the registration
            if (userID.equals("0")) {
                walletView.displayMessage("Login canceled.");
                return;  // Exit the login process
            }

            if (userID.matches("\\d{6}") && userRepository.authenticateUserID(userID)) {
                for (int j = 0; j < 3; j++) {
                    walletView.displayMessage("\nEnter 0 to cancel login.");
                    pin = walletView.getUserInput("Enter PIN: ");
                    if (pin.equals("0")) {
                        walletView.displayMessage("Login canceled.");
                        return;  // Exit the login process
                    }

                    if (pin.matches("\\d{4}") && userRepository.authenticatePIN(userID, pin)) {
                        walletView.displayMessage("Login successful!\n");
                        ACTIVE_USER = userRepository.getUser(userID);
                        mainMenu();
                        return;
                    } else {
                        walletView.displayMessage("Incorrect pin. Attempt " + (j + 1) + " of 3.");
                    }
                }
                walletView.displayMessage("Login failed. Please try again.\n");
                return;
            } else {
                walletView.displayMessage("User " + userID + " does not exist. Please try again. Attempt " + (i + 1) + " of 3.");
            }
        }

        walletView.displayMessage("Login failed. Please try again.\n");
    }

    public void register() {
        String userID;
        String pin;
        do {
            walletView.displayMessage("\nEnter 0 to cancel registration.");
            userID = walletView.getUserInput("Enter User ID: ");

            // Check if the user entered "0" to cancel the registration
            if (userID.equals("0")) {
                walletView.displayMessage("Registration canceled.");
                return;  // Exit the login process
            }
        } while (userID == null || !userID.matches("\\d{6}"));

        do {
            walletView.displayMessage("\nEnter 0 to cancel registration.");
            pin = walletView.getUserInput("Enter User Pin: ");

            // Check if the user entered "0" to cancel the registration
            if (userID.equals("0")) {
                walletView.displayMessage("Registration canceled.");
                return;  // Exit the login process
            }
        } while (pin == null || !pin.matches("\\d{4}"));

        String name = walletView.getUserInput("Enter your name: ");
        ACTIVE_USER = new User(userID, pin, name, 0.0);
        userRepository.addUser(ACTIVE_USER);

        mainMenu();
    }

    public void mainMenu() {
        while (true) {
            String greetings = "Welcome, " + ACTIVE_USER.getName();
            walletView.displayHeader(greetings);
            walletView.displayMenu(new String[]{"Check Balance", "Cash-In", "Money Transfer", "Logout"});
            switch (walletView.getUserChoice()) {
                case 1 -> checkBalance();
                case 2 -> cashin();
                case 3 -> moneyTransfer();
                case 4 -> {
                    walletView.displayMessage("You have been logged-out.");
                    ACTIVE_USER = null;
                    return;
                }
                default -> walletView.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    public void checkBalance() {
        while (true) {
            // Format the balance with commas
            String currentBalance = "Your balance is " + String.format("Php %,.2f", ACTIVE_USER.getBalance());
            walletView.displayHeader(currentBalance);
            walletView.displayMenu(new String[]{"Back"});
            int choice = walletView.getUserChoice();
            if (choice == 1) return;
        }
    }

    public void cashin() {
        String currentBalance = "Your current balance is " + String.format("Php %,.2f", ACTIVE_USER.getBalance());
        walletView.displayHeader("Cash-in to ZenyWallet");
        walletView.displayMessage(currentBalance);
        walletView.displayMessage("\nEnter 0 to go back to the Main Menu.");

        while (true) {
            double newBalance = walletView.getAmountInput("Enter amount to Cash-In (Php): ");
            if (newBalance > 0) {
                ACTIVE_USER.addBalance(newBalance);
                userRepository.updateBalance(ACTIVE_USER.getUserID(), ACTIVE_USER.getBalance());
                walletView.displayMessage("Cash-in Successful! New balance: " + String.format("Php %,.2f", ACTIVE_USER.getBalance()));
                walletView.displayMessage("\nPress 'Enter' to go back to the Main Menu...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                return;
            } else if (newBalance == 0) {
                return;
            } else {
                walletView.displayMessage("Invalid input. Cash-in amount must be greater than 0.");
            }
        }
    }

    public void moneyTransfer() {
        String currentBalance = "Your current balance is " + String.format("Php %,.2f", ACTIVE_USER.getBalance());
        walletView.displayHeader("Money Transfer - ZenyWallet");
        walletView.displayMessage(currentBalance);

        while (true) {
            String receiverID = walletView.getUserInput("Enter Recipient User ID: ");
            if (receiverID != null && receiverID.matches("\\d{6}") && userRepository.authenticateUserID(receiverID)) {
                walletView.displayMessage("\nEnter 0 to cancel transaction and go back to the Main Menu.");
                double transferAmount = walletView.getAmountInput("Enter Amount to Transfer (Php): ");
                if (transferAmount > 0 && ACTIVE_USER.getBalance() > transferAmount) {
                    walletView.displayMessage("\nTransfer Details:");
                    walletView.displayMessage("Recipient: " + userRepository.getUser(receiverID).getName() + " (ID: " + receiverID + ")");
                    walletView.displayMessage("Amount: Php " + String.format("%,.2f", transferAmount));
                    walletView.displayMessage("\nDo you want to proceed with the transfer?");
                    walletView.displayMenu(new String[]{"Yes", "No"});
                    int choice = walletView.getUserChoice();
                    if (choice == 1) {
                        for (int i = 0; i < 3; i++) {
                            // Ask for PIN
                            String pin = walletView.getUserInput("Enter your PIN to confirm the transfer: ");
                            if (pin != null && pin.matches("\\d{4}") && userRepository.authenticatePIN(ACTIVE_USER.getUserID(), pin)) {
                                ACTIVE_USER.deductBalance(transferAmount);
                                userRepository.updateBalance(receiverID, transferAmount);
                                userRepository.updateBalance(ACTIVE_USER.getUserID(), ACTIVE_USER.getBalance());
                                walletView.displayMessage("Transfer successful!");
                                walletView.displayMessage("You transferred: " + String.format("Php %,.2f", transferAmount));
                                walletView.displayMessage("New Balance: " + String.format("Php %,.2f", ACTIVE_USER.getBalance()));
                                return;
                            } else {
                                walletView.displayMessage("Incorrect pin. Attempt " + (i + 1) + " of 3.");
                            }
                        }
                        walletView.displayMessage("You've entered an incorrect pin. Transaction Canceled.");
                        return;
                    }
                } else if (transferAmount > 0 && ACTIVE_USER.getBalance() < transferAmount) {
                    walletView.displayMessage("Insufficient Balance. Please try again.");
                } else if (transferAmount == 0) {
                    return;
                } else {
                    walletView.displayMessage("Invalid input. Transfer amount must be greater than 0.");
                }
            }
        }
    }
}