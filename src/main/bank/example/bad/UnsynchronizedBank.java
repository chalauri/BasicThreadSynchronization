package main.bank.example.bad;

import main.bank.example.good.Account;

/**
 * Created by G.Chalauri on 03/21/17.
 */
public class UnsynchronizedBank implements Runnable{
    private UnsynchronizedAccount account;
    public UnsynchronizedBank(UnsynchronizedAccount account) {
        this.account=account;
    }

    @Override
    public void run() {
        for (int i=0; i<100; i++){
            account.subtractAmount(1000);
        }
    }
}
