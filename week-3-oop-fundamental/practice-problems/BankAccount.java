public class BankAccount {
    private static String bankName;
    private static int totalAccounts = 0;
    private static double interestRate = 0.0;

    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        totalAccounts++;
    }

    public static void setBankName(String name) {
        bankName = name;
    }

    public static void setInterestRate(double rate) {
        interestRate = rate;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static void displayBankInfo() {
        System.out.println("Bank Name: " + bankName);
        System.out.println("Total Accounts: " + totalAccounts);
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("------------------------");
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(accountHolder + " deposited $" + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println(accountHolder + " withdrew $" + amount);
        } else {
            System.out.println(accountHolder + " attempted invalid withdrawal.");
        }
    }

    public double calculateInterest() {
        return balance * (interestRate / 100);
    }

    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: $" + balance);
        System.out.println("Interest on Balance: $" + calculateInterest());
        System.out.println("------------------------");
    }

    public static void main(String[] args) {
        BankAccount.setBankName("Global Bank");
        BankAccount.setInterestRate(3.5);

        BankAccount acc1 = new BankAccount("A1001", "John Doe", 1000.0);
        BankAccount acc2 = new BankAccount("A1002", "Jane Smith", 2000.0);

        BankAccount.displayBankInfo();

        acc1.displayAccountInfo();
        acc2.displayAccountInfo();

        acc1.deposit(500);
        acc2.withdraw(300);

        acc1.displayAccountInfo();
        acc2.displayAccountInfo();

        System.out.println("Total accounts (via class): " + BankAccount.getTotalAccounts());
        System.out.println("Total accounts (via object): " + BankAccount.getTotalAccounts());

        System.out.println("Changing interest rate via acc1 to 4.0%");
        BankAccount.setInterestRate(4.0);

        BankAccount.displayBankInfo();
        acc1.displayAccountInfo();
        acc2.displayAccountInfo();
    }
}
