package org.example;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable{
    private BlockingQueue<Clients> clients;
    private AtomicInteger waitingPeriod;

    public Queue() {

    }

    @Override
    public void run() {}
}
