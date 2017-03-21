package main.lock_fairness;

import main.lock.example.PrintQueue;

/**
 * Created by G.Chalauri on 03/21/17.
 */
public class Job implements Runnable {

    private main.lock_fairness.PrintQueue printQueue;

    public Job(main.lock_fairness.PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.printf("%s: Going to print a document\n", Thread.
                currentThread().getName());
        printQueue.printJob(new Object());
        System.out.printf("%s: The document has been printed\n",
                Thread.currentThread().getName());
    }
}
