package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable {
   private UI ui;
   private Scheduler scheduler;
    private List<Client> waitingClients;
    private List<Client> allClients;
    private final int noQueues;
    private final int noClients;
    private final int minArrivalTime;
    private final int maxArrivalTime;
    private final int minServiceTime;
    private final int maxServiceTime;
    private final int simulationInterval;
    static SelectionPolicy selectionPolicy;

    public SimulationManager(int queues, int clients, int simulationInterval,
                             int minArrivalTime, int maxArrivalTime, int minServiceTime,
                             int maxServiceTime, UI ui, SelectionPolicy strategy) {
       this.noClients = clients;
       this.noQueues = queues;
       this.minArrivalTime = minArrivalTime;
       this.maxArrivalTime = maxArrivalTime;
       this.minServiceTime = minServiceTime;
       this.maxServiceTime = maxServiceTime;
       this.simulationInterval = simulationInterval;
       this.ui = ui;
       selectionPolicy = strategy;
       this.waitingClients = new ArrayList<Client>();
       this.allClients = new ArrayList<Client>();
       this.scheduler = new Scheduler(queues, clients);

       this.scheduler.changeStrategy(selectionPolicy);
       this.generateNRandomClients();

        }

    public static void setStrategy(SelectionPolicy strategy) {
        selectionPolicy = strategy;
    }

    private int randomArrivalTime() {
        return (int) (Math.random() * (maxArrivalTime - minArrivalTime + 1)) + minArrivalTime;
    }

    private int randomServiceTime() {
        return (int) (Math.random() * (maxServiceTime - minServiceTime + 1)) + minServiceTime;
    }

    private void generateNRandomClients() {
        for (int i = 0; i < noClients; i++) {
            int arrivalTime = randomArrivalTime();
            int serviceTime = randomServiceTime();
            Client client = new Client();

            client.setId(i);
            client.setArrivalTime(arrivalTime);
            client.setServiceTime(serviceTime);

            waitingClients.add(client);
            allClients.add(client); //here, we store all clients to calculate the average waiting time
        }

    }

    private void log(String message) {
        ui.appendText(message);
        try {
            FileWriter writer = new FileWriter("simulation_output_tests.txt", true);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        log("-----------------");
        log("Simulation started.");

        int peakHour = 0;
        int maxClients = 0;

        for (int currentTime = 0; currentTime <= simulationInterval; currentTime++) {
            List<Client> arrivedClients = new ArrayList<>();
            for (int i = 0; i < waitingClients.size(); i++) {
                Client client = waitingClients.get(i);
                if (client.getArrivalTime() == currentTime) {
                    arrivedClients.add(client);
                    waitingClients.remove(i);
                    i--;
                }
            }

            for (Client client : arrivedClients) {
                scheduler.dispatchClient(client);
            }

            int totalClients = 0;
            for (Queue queue : scheduler.getQueues()) {
                totalClients += queue.getClients().size();
            }

            if (totalClients > maxClients) {
                // I have considered the peak hour as in the time when the number of clients in the queues is maximum
                maxClients = totalClients;
                peakHour = currentTime;
            }

            boolean allQueuesEmpty = true; // Here, we check to see if there is at least one queue with clients at it
            for (Queue queue : scheduler.getQueues()) {
                if (!queue.getClients().isEmpty()) {
                    allQueuesEmpty = false;
                    break;
                }
            }

            if (waitingClients.isEmpty() && allQueuesEmpty) {
                break; // We exit the loop if no more waiting clients and all queues are empty
            }

            StringBuilder stringBuilder = new StringBuilder("Time " + currentTime);
            stringBuilder.append('\n');
            stringBuilder.append("Waiting clients: ");
            for (Client client : waitingClients) {
                stringBuilder.append("(").append(client.getId()).append(", ").append(client.getArrivalTime()).append(", ").append(client.getServiceTime()).append(") ");
            }
            stringBuilder.append('\n');

            for (Queue queue : scheduler.getQueues()) {
                stringBuilder.append("Queue").append(queue.getId()).append(": ");
                if (queue.getClients().isEmpty()) {
                    stringBuilder.append("closed");
                } else {
                    for (Client client : queue.getClients()) {
                        stringBuilder.append("(").append(client.getId()).append(", ").append(client.getArrivalTime()).append(", ").append(client.getServiceTime()).append(") ");
                    }
                }
                stringBuilder.append('\n');
            }
            stringBuilder.append('\n');

            log(stringBuilder.toString());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Queue queue: scheduler.getQueues()){
            queue.setContinueSimulation(false);
        }

        log("Simulation ended.");

        double totalServiceTime = 0.0;
        for (Queue queue : scheduler.getQueues()) {
            totalServiceTime += queue.getServicePeriod().intValue();
        }
        double averageServiceTime = totalServiceTime / noClients;
        log("Average serving time: " + averageServiceTime);

        double totalWaitingTime = 0.0;
        for(Queue queue : scheduler.getQueues()) {
            totalWaitingTime += queue.getWaitingPeriod().intValue();
        }
        double averageWaitingTime = totalWaitingTime / noClients;

        if(averageWaitingTime < 0) {
            averageWaitingTime = 0.0;
        }

        log("Average waiting time: " + averageWaitingTime);

        log("Peak hour: " + peakHour);

    }



}