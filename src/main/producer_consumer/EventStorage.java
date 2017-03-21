package main.producer_consumer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by G.Chalauri on 03/21/17.
 */


/*
The key to this example is the set() and get() methods of the EventStorage class.
First of all, the set() method checks if there is free space in the storage attribute. If
it's full, it calls the wait() method to wait for free space. When the other thread calls
the notifyAll() method, the thread wakes up and checks the condition again. The
notifyAll() method doesn't guarantee that the thread will wake up. This process is
repeated until there is free space in the storage and it can generate a new event and store it.
The behavior of the get() method is similar. First, it checks if there are events on the
storage. If the EventStorage class is empty, it calls the wait() method to wait for events.
Where the other thread calls the notifyAll() method, the thread wakes up and checks the
condition again until there are some events in the storage.
 */
public class EventStorage {
    private int maxSize;
    private List<Date> storage;

    public EventStorage() {
        maxSize = 10;
        storage = new LinkedList<>();
    }

    public synchronized void set() {
        while (storage.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        storage.add(new Date());
        System.out.printf("Set: %d\n", storage.size());
        notifyAll();
    }

    public synchronized void get() {
        while (storage.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Get: %d: %s\n", storage.
                size(), ((LinkedList<?>) storage).poll());
        notifyAll();
    }
}
