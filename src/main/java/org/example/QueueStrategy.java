package org.example;

import java.util.List;

public class QueueStrategy implements Strategy{
    @Override
    public void addClient(List<Queue> queues, Client client) {
        Queue leastBusyQueue = queues.get(0);
        int leastBusySize = leastBusyQueue.getClients().size();

        for (Queue queue : queues) {
            if (queue.getClients().size() < leastBusySize) {
                leastBusyQueue = queue;
                leastBusySize = queue.getClients().size();
            }
        }

        leastBusyQueue.addClient(client);
    }
}
