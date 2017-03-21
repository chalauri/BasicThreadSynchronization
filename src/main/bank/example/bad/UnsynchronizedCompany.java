package main.bank.example.bad;

import main.bank.example.good.Account;

/**
 * Created by G.Chalauri on 03/21/17.
 */
public class UnsynchronizedCompany implements Runnable {
    private UnsynchronizedAccount account;

    public UnsynchronizedCompany(UnsynchronizedAccount account) {
        this.account = account;
    }


    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.addAmount(1000);
        }
    }
}
