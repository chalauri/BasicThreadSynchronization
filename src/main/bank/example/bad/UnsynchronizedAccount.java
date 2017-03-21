package main.bank.example.bad;

/**
 * Created by G.Chalauri on 03/21/17.
 */
public class UnsynchronizedAccount {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public  void addAmount(double amount) {
        double temp = balance;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        temp += amount;
        balance = temp;
    }

    public  void subtractAmount(double amount) {
        double temp = balance;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        temp -= amount;
        balance = temp;
    }
}
