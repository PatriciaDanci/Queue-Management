package org.example;

import java.util.List;
import java.util.*;

public class TimeStrategy implements Strategy {
    @Override
    public void addClient(List<Queue> queues, Client client) {
        Queue shortestQueue = queues.get(0);
        double shortestWaitingTime = shortestQueue.getServicePeriod().intValue();

        for (Queue queue : queues) {
            if (queue.getServicePeriod().intValue() < shortestWaitingTime) {
                shortestQueue = queue;
                shortestWaitingTime = queue.getServicePeriod().intValue();
            }
        }
        shortestQueue.addClient(client);
    }
}
