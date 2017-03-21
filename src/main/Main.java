package main;


import main.bank.example.bad.UnsynchronizedAccount;
import main.bank.example.bad.UnsynchronizedBank;
import main.bank.example.bad.UnsynchronizedCompany;
import main.bank.example.good.Account;
import main.bank.example.good.Bank;
import main.bank.example.good.Company;
import main.cinema.example.Cinema;
import main.cinema.example.TicketOffice1;
import main.cinema.example.TicketOffice2;
import main.lock.example.Job;
import main.lock.example.PrintQueue;
import main.multiple_conditions.Buffer;
import main.multiple_conditions.FileMock;
import main.producer_consumer.Consumer;
import main.producer_consumer.EventStorage;
import main.producer_consumer.Producer;
import main.read_write_lock.PricesInfo;
import main.read_write_lock.Reader;
import main.read_write_lock.Writer;

/**
 * Created by G.Chalauri on 03/21/17.
 */
public class Main {

    public static void main(String[] args) {

        //bankExample();

        //unsynchronizedBankExample();

        //cinemaExample();

        // producerConsumerExample();

        // lockExample();

        //readWriteLockExample();

        //lockFairnessExample();

        multipleConditionsExample();
    }

    private static void multipleConditionsExample() {
        FileMock mock = new FileMock(100, 10);
        Buffer buffer = new Buffer(20);

        main.multiple_conditions.Producer producer = new main.multiple_conditions.Producer(mock, buffer);
        Thread threadProducer = new Thread(producer, "Producer");

        main.multiple_conditions.Consumer consumers[] = new main.multiple_conditions.Consumer[3];
        Thread threadConsumers[] = new Thread[3];
        for (int i = 0; i < 3; i++) {
            consumers[i] = new main.multiple_conditions.Consumer(buffer);
            threadConsumers[i] = new Thread(consumers[i], "Consumer " + i);
        }

        threadProducer.start();
        for (int i=0; i<3; i++){
            threadConsumers[i].start();
        }
    }

    private static void lockFairnessExample() {
        main.lock_fairness.PrintQueue printQueue = new main.lock_fairness.PrintQueue();

        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new main.lock_fairness.Job(printQueue), "Thread " + i);
        }

        for (int i = 0; i < 10; i++) {
            thread[i].start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readWriteLockExample() {
        PricesInfo pricesInfo = new PricesInfo();

        Reader readers[] = new Reader[5];
        Thread threadsReader[] = new Thread[5];

        for (int i = 0; i < 5; i++) {
            readers[i] = new Reader(pricesInfo);
            threadsReader[i] = new Thread(readers[i]);
        }

        Writer writer = new Writer(pricesInfo);
        Thread threadWriter = new Thread(writer);

        for (int i = 0; i < 5; i++) {
            threadsReader[i].start();
        }
        threadWriter.start();

    }


    private static void lockExample() {
        PrintQueue printQueue = new PrintQueue();

        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job(printQueue), "Thread " + i);
        }

        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }


    private static void producerConsumerExample() {
        EventStorage storage = new EventStorage();

        Producer producer = new Producer(storage);
        Thread thread1 = new Thread(producer);

        Consumer consumer = new Consumer(storage);
        Thread thread2 = new Thread(consumer);

        thread2.start();
        thread1.start();
    }


    /*
    In this example, we have an object that controls access to the
    vacanciesCinema1 attribute, so only one thread can modify
    this attribute each time, and another object controls access to the
    vacanciesCinema2 attribute, so only one thread can modify
    this attribute each time. But there may be two threads running
    simultaneously, one modifying the vacancesCinema1 attribute and
    the other one modifying the vacanciesCinema2 attribute.
     */
    private static void cinemaExample() {
        Cinema cinema = new Cinema();

        TicketOffice1 ticketOffice1 = new TicketOffice1(cinema);
        Thread thread1 = new Thread(ticketOffice1, "TicketOffice1");

        TicketOffice2 ticketOffice2 = new TicketOffice2(cinema);
        Thread thread2 = new Thread(ticketOffice2, "TicketOffice2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Room 1 Vacancies: %d\n", cinema.
                getVacanciesCinema1());
        System.out.printf("Room 2 Vacancies: %d\n", cinema.
                getVacanciesCinema2());
    }


    private static void bankExample() {
        Account account = new Account();
        account.setBalance(1000);

        Company company = new Company(account);
        Thread companyThread = new Thread(company);

        Bank bank = new Bank(account);
        Thread bankThread = new Thread(bank);

        System.out.printf("Account : Initial Balance: %f\n", account.
                getBalance());
        System.out.println(" Start the threads.");

        companyThread.start();
        bankThread.start();

        try {
            companyThread.join();
            bankThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Account : Final Balance: %f\n", account.
                getBalance());
    }

    private static void unsynchronizedBankExample() {
        UnsynchronizedAccount account = new UnsynchronizedAccount();
        account.setBalance(1000);

        UnsynchronizedCompany company = new UnsynchronizedCompany(account);
        Thread companyThread = new Thread(company);

        UnsynchronizedBank bank = new UnsynchronizedBank(account);
        Thread bankThread = new Thread(bank);

        System.out.printf("Account : Initial Balance: %f\n", account.
                getBalance());
        System.out.println(" Start the threads.");

        companyThread.start();
        bankThread.start();

        try {
            companyThread.join();
            bankThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Account : Final Balance: %f\n", account.
                getBalance());
    }

}
