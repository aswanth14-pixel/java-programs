class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private static int totalAccounts = 0;

    // Constructor
    public BankAccount(String accountHolderName, double initialDeposit) {
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    // Deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(amount + " deposited successfully!");
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // Withdraw
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(amount + " withdrawn successfully!");
        } else {
            System.out.println("Invalid/Insufficient funds!");
        }
    }

    // Check balance
    public void checkBalance() {
        System.out.println("Balance: " + balance);
    }

    // Static methods
    private static String generateAccountNumber() {
        return "ACC" + String.format("%03d", totalAccounts + 1);
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    // Display info
    public void displayAccountInfo() {
        System.out.println("Account No: " + accountNumber +
                ", Holder: " + accountHolderName +
                ", Balance: " + balance);
    }
}

public class BankSystem {
    public static void main(String[] args) {
        BankAccount[] accounts = new BankAccount[3];

        accounts[0] = new BankAccount("Alice", 5000);
        accounts[1] = new BankAccount("Bob", 3000);
        accounts[2] = new BankAccount("Charlie", 7000);

        accounts[0].deposit(2000);
        accounts[1].withdraw(1000);
        accounts[2].checkBalance();

        System.out.println("\nAll Accounts:");
        for (BankAccount acc : accounts) {
            acc.displayAccountInfo();
        }

        System.out.println("\nTotal Accounts: " + BankAccount.getTotalAccounts());
    }
}
