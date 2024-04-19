package org.example;

import java.awt.*;
import java.util.List;

public interface Strategy {
    public void addClient(List<Queue> queues, Client client);
}
