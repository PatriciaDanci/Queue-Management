package org.example;

import java.util.ArrayList;
import java.util.List;

class Scheduler {
    private List<Queue> queues;
    private Strategy strategy;
    private int numQueues;
    private int numClients;


    public Scheduler(int numQueues, int numClients) {
        this.numClients = numClients;
        this.numQueues = numQueues;

        queues = new ArrayList<>();

        for (int i = 0; i < numQueues; i++) {
            Queue queue = new Queue(i);
            queues.add(queue);
            new Thread(queue).start();

        }
        this.strategy = new TimeStrategy(); // Default strategy
    }

    public void changeStrategy(SelectionPolicy selectionPolicy) {
        if (selectionPolicy == SelectionPolicy.SHORTEST_TIME) {
            this.strategy = new TimeStrategy();
        } else if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE) {
            this.strategy = new QueueStrategy();
        }
    }

    public void dispatchClient(Client client) {
        strategy.addClient(queues, client);
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public int getNumClients() {
        return numClients;
    }

    public int getNumQueues() {
        return numQueues;
    }

    public void setNumClients(int numClients) {
        this.numClients = numClients;
    }

    public void setNumQueues(int numQueues) {
        this.numQueues = numQueues;
    }

    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }
}
