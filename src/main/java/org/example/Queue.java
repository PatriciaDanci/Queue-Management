package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

class Queue implements Runnable {
    private BlockingQueue<Client> clients;
    private AtomicInteger servicePeriod;
    private AtomicInteger waitingPeriod;
    private int id;
    private boolean continueSimulation;

    public Queue(int id) {
        clients = new LinkedBlockingQueue<>();
        servicePeriod = new AtomicInteger(0);
        waitingPeriod = new AtomicInteger(0);
        this.id = id;
        continueSimulation = true;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Client> clients) {
        this.clients = clients;
    }

    public AtomicInteger getServicePeriod() {
        return servicePeriod;
    }

    public void setServicePeriod(AtomicInteger servicePeriod) {
        this.servicePeriod = servicePeriod;
    }

    public void setContinueSimulation(boolean continueSimulation) {
        this.continueSimulation = continueSimulation;
    }

    private boolean getCotinueSimulation() {
        return continueSimulation;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void addClient(Client newClient) {
        for(Client client : clients) {
            waitingPeriod.getAndAdd(client.getServiceTime());
        }
        clients.add(newClient);
        servicePeriod.getAndAdd(newClient.getServiceTime());
        //waitingPeriod.getAndAdd(servicePeriod.get() - newClient.getServiceTime());
    }

@Override
    public void run() {
    while (continueSimulation) {
        if (!clients.isEmpty()) {
            Client client = clients.peek();
            try {
                Thread.sleep(1000);
                client.setServiceTime(client.getServiceTime() - 1);
                //waitingPeriod.getAndAdd(-1);

                if (client.getServiceTime() == 0) {
                    clients.remove(client);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    }
}
