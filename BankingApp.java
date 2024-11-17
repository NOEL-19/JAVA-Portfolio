import java.util.Scanner;
import java.util.HashMap;

class User {
    private int id;
    private int pin;
    private String name;
    private double balance;

    public User(int id, int pin, String name, double balance) {
        this.id = id;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }
    }

    public void transfer(User recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed.");
        }
    }
}

public class BankingApp {
    private static HashMap<Integer, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;

    public static void main(String[] args) {
        // Initialize users
        users.put(412435, new User(412435, 7452, "Chris Sandoval", 32000));
        users.put(264863, new User(264863, 1349, "Marc Yim", 1000));

        // Login flow
        while (loggedInUser == null) {
            login();
        }

        // Main menu
        while (true) {
            System.out.println("\nWelcome, " + loggedInUser.getName() + "!");
            System.out.println("1. Check Balance");
            System.out.println("2. Cash-In");
            System.out.println("3. Money Transfer");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: checkBalance();
                case 2: cashIn();
                case 3: moneyTransfer();
                case 4: {
                    System.out.println("Logging out...");
                    loggedInUser = null;
                    return;
                }
                default:
                
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter User ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter 4-digit PIN: ");
        int pin = scanner.nextInt();

        User user = users.get(id);
        if (user != null && user.getPin() == pin) {
            loggedInUser = user;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid User ID or PIN. Please try again.");
        }
    }

    private static void checkBalance() {
        System.out.println("Your current balance is: " + loggedInUser.getBalance());
    }

    private static void cashIn() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        loggedInUser.deposit(amount);
        System.out.println("Deposit successful! New balance: " + loggedInUser.getBalance());
    }

    private static void moneyTransfer() {
        System.out.print("Enter recipient User ID: ");
        int recipientId = scanner.nextInt();
        User recipient = users.get(recipientId);

        if (recipient != null && recipient != loggedInUser) {
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            loggedInUser.transfer(recipient, amount);
        } else {
            System.out.println("Invalid recipient or self-transfer not allowed.");
        }
    }
}

