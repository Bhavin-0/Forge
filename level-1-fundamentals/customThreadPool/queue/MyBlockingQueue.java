package queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<T> {
    private final int capacity;
    private final Object[] items;
    private int head;
    private int tail;
    private int size;

    MyBlockingQueue(int capacity){
        this.capacity = capacity;
        this.items = new Object[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public synchronized void put(T item) throws InterruptedException{
        while(size == capacity){
            wait();
        }
        items[tail] = item;
        tail = (tail + 1) % capacity ;
        size++;
        notifyAll();
    }

//    @SuppressWarnings("unchecked")
    public synchronized T take() throws InterruptedException{
        while(size == 0){
            wait();
        }
        T item = (T) items[head];
        head = (head + 1) % capacity ;
        items[head] = null;      //Help GC
        size--;
        notifyAll();
        return item;
    }

    public synchronized int size(){
        return size;
    }

    public synchronized boolean isEmpty(){
        return size == 0;
    }

    public synchronized boolean isFull(){
        return size == capacity;
    }
}
