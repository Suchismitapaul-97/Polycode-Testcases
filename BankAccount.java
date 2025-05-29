public class BankAccount {
    private String accountNumber;
    private double balance;
    private boolean isFrozen;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isFrozen = false;
    }

    public double getBalance() { return balance; }
    public boolean isFrozen() { return isFrozen; }
    public void freeze() { this.isFrozen = true; }
    public void unfreeze() { this.isFrozen = false; }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance || isFrozen) return false;
        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0 || isFrozen) return false;
        balance += amount;
        return true;
    }
}
