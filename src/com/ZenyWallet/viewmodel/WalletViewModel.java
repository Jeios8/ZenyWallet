package com.ZenyWallet.viewmodel;

import com.ZenyWallet.model.User;
import com.ZenyWallet.model.UserRepository;
import com.ZenyWallet.view.WalletView;

public class WalletViewModel {
    private final WalletView walletView;
    private final UserRepository userRepository;
    private User ACTIVE_USER;

    public WalletViewModel(WalletView walletView, UserRepository userRepository) {
        this.walletView = walletView;
        this.userRepository = userRepository;
    }

    public void run() {
        while (true) {
            walletView.displayHeader("Welcome to ZenyWallet");
            walletView.displayMenu(new String[]{"Login", "Register", "Exit"});
            int choice = walletView.getUserChoice();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    walletView.displayMessage("Thank you for using ZenyWallet.");
                    return;
                default:
                    walletView.displayMessage("Invalid option. Please try again.");
                    break;
            }

        }
    }


    public void login() {
        for (int i = 0; i < 3; i++) {
            String userID = walletView.getUserInput("Enter UserID (or 0 to cancel): ");
            if (userID.equals("0")) {
                walletView.displayMessage("\nLogin canceled.");
                return;
            }

            if (userID.matches("\\d{6}") && userRepository.authenticateUserID(userID)) {
                for (int j = 0; j < 3; j++) {
                    String pin = walletView.getUserInput("Enter PIN (or 0 to cancel): ");
                    if (pin.equals("0")) {
                        walletView.displayMessage("\nLogin canceled.");
                        return;
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
        while (true) {
            String userID = walletView.getUserInput("Enter User ID (6 digits, or 0 to cancel): ");
            if (userID.equals("0")) {
                walletView.displayMessage("\nRegistration canceled.");
                return;
            }

            if (userID.matches("\\d{6}")) {
                while (true) {
                    String pin = walletView.getUserInput("Enter User Pin (4 digits, or 0 to cancel): ");
                    if (pin.equals("0")) {
                        walletView.displayMessage("\nRegistration canceled.");
                        return;
                    }

                    if (pin.matches("\\d{4}")) {
                        String name = walletView.getUserInput("Enter your name: ");
                        ACTIVE_USER = new User(userID, pin, name, 0.0);
                        userRepository.addUser(ACTIVE_USER);
                        walletView.displayMessage("Registration successful!");
                        mainMenu();
                        return;
                    } else {
                        walletView.displayMessage("Invalid PIN. Please try again.");
                    }
                }
            } else {
                walletView.displayMessage("Invalid User ID. Please try again.");
            }
        }
    }

    public void mainMenu() {
        while (ACTIVE_USER != null) {
            String greetings = "Welcome, " + ACTIVE_USER.getName();
            walletView.displayHeader(greetings);
            walletView.displayMenu(new String[]{"Check Balance", "Cash-In", "Money Transfer", "Logout"});
            switch (walletView.getUserChoice()) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    cashin();
                    break;
                case 3:
                    moneyTransfer();
                    break;
                case 4:
                    walletView.displayMessage("You have been logged-out.");
                    ACTIVE_USER = null;
                    break;
                default:
                    walletView.displayMessage("Invalid option. Please try again.");
                    break;
            }

        }
    }

    public void checkBalance() {
        String currentBalance = "Your balance is " + String.format("Php %,.2f", ACTIVE_USER.getBalance());
        walletView.displayHeader(currentBalance);
        walletView.displayMenu(new String[]{"Back"});
        while (true) {
            int choice = walletView.getUserChoice();
            if (choice == 1) {
                return;
            } else {
                walletView.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    public void cashin() {
        walletView.displayHeader("Cash-in to ZenyWallet");
        walletView.displayMessage("Your current balance is Php " + String.format("%,.2f", ACTIVE_USER.getBalance()));

        while (true) {
            double amount = walletView.getAmountInput("Enter amount to Cash-In (Php, or 0 to cancel): ");
            if (amount == 0) {
                return;
            } else if (amount > 0) {
                ACTIVE_USER.addBalance(amount);
                userRepository.updateBalance(ACTIVE_USER.getUserID(), ACTIVE_USER.getBalance());
                walletView.displayMessage("Cash-in successful! New balance: Php " + String.format("%,.2f", ACTIVE_USER.getBalance()));
                return;
            } else {
                walletView.displayMessage("Invalid input. Cash-in amount must be greater than 0.");
            }
        }
    }

    public void moneyTransfer() {
        walletView.displayHeader("Money Transfer - ZenyWallet");
        walletView.displayMessage("Your current balance is Php " + String.format("%,.2f", ACTIVE_USER.getBalance()));

        while (true) {
            String receiverID = walletView.getUserInput("Enter Recipient User ID (or 0 to cancel): ");
            if (receiverID.equals("0")) {
                walletView.displayMessage("Transaction Cancelled.");
                return;
            }

            if (receiverID.matches("\\d{6}") && userRepository.authenticateUserID(receiverID)) {
                double amount = walletView.getAmountInput("Enter Amount to Transfer (Php, or 0 to cancel): ");
                if (amount == 0) {
                    return;
                } else if (amount > 0 && ACTIVE_USER.getBalance() >= amount) {
                    walletView.displayMessage("You are about to transfer Php " + String.format("%,.2f", amount) + " to User ID: " + receiverID);
                    String confirmation = walletView.getUserInput("Do you want to proceed? (y/n): ").toLowerCase();

                    if (confirmation.equals("y")) {
                        for (int i = 0; i < 3; i++) {
                            String pin = walletView.getUserInput("Enter your PIN to confirm: ");
                            if (userRepository.authenticatePIN(ACTIVE_USER.getUserID(), pin)) {
                                ACTIVE_USER.deductBalance(amount);
                                userRepository.updateBalance(receiverID, amount);
                                userRepository.updateBalance(ACTIVE_USER.getUserID(), ACTIVE_USER.getBalance());

                                walletView.displayMessage("Transfer successful!");
                                walletView.displayMessage("New Balance: Php " + String.format("%,.2f", ACTIVE_USER.getBalance()));
                                return;
                            } else {
                                walletView.displayMessage("Incorrect PIN. Attempt " + (i + 1) + " of 3.");
                            }
                        }
                        walletView.displayMessage("Transaction canceled due to incorrect PIN attempts.");
                    } else {
                        walletView.displayMessage("Transaction canceled.");
                    }
                    return;
                } else if (amount > ACTIVE_USER.getBalance()) {
                    walletView.displayMessage("Insufficient Balance. Please try again.");
                } else {
                    walletView.displayMessage("Invalid input. Transfer amount must be greater than 0.");
                }
            } else {
                walletView.displayMessage("Invalid Recipient User ID. Please try again.");
            }
        }
    }
}
