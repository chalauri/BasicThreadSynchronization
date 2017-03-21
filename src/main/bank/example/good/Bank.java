package main.bank.example.good;



/**
 * Created by G.Chalauri on 03/21/17.
 */
public class Bank implements Runnable {

    private Account account;
    public Bank(Account account) {
        this.account=account;
    }

    @Override
    public void run() {
        for (int i=0; i<100; i++){
            account.subtractAmount(1000);
        }
    }
}
