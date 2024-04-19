package org.example;

public class Client {
    private int id;
    private int arrivalTime;
    private int serviceTime;

    public Client() {
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }


}
