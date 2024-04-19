package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UI extends JFrame {

    private JFrame frame;
    private JLabel clientsLabel;
    private JLabel queuesLabel;
    private JLabel simulationIntervalLabel;
    private JLabel minArrivalTimeLabel;
    private JLabel maxArrivalTimeLabel;
    private JLabel minServiceTimeLabel;
    private JLabel maxServiceTimeLabel;
    private JTextField clientsTextField;
    private JTextField queuesTextField;
    private JTextField simulationIntervalTextField;
    private JTextField minArrivalTimeTextField;
    private JTextField maxArrivalTimeTextField;
    private JTextField minServiceTimeTextField;
    private JTextField maxServiceTimeTextField;
    private JButton startButton;
    private JTextArea display;
    private JScrollPane scrollPane;

    public UI() {
        Font timesNewRoman = new Font("Times New Roman", Font.PLAIN, 14);

        frame = new JFrame("Queue Manager");
        frame.setPreferredSize(new Dimension(700, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        clientsLabel = new JLabel("Clients");
        clientsLabel.setFont(timesNewRoman);
        clientsLabel.setBounds(50, 20, 150, 20);
        frame.getContentPane().add(clientsLabel);

        queuesLabel = new JLabel("Queues");
        queuesLabel.setFont(timesNewRoman);
        queuesLabel.setBounds(50, 50, 150, 20);
        frame.getContentPane().add(queuesLabel);

        simulationIntervalLabel = new JLabel("Simulation interval");
        simulationIntervalLabel.setFont(timesNewRoman);
        simulationIntervalLabel.setBounds(50, 80, 150, 20);
        frame.getContentPane().add(simulationIntervalLabel);

        minArrivalTimeLabel = new JLabel("Minimum arrival time");
        minArrivalTimeLabel.setFont(timesNewRoman);
        minArrivalTimeLabel.setBounds(50, 110, 150, 20);
        frame.getContentPane().add(minArrivalTimeLabel);

        maxArrivalTimeLabel = new JLabel("Maximum arrival time");
        maxArrivalTimeLabel.setFont(timesNewRoman);
        maxArrivalTimeLabel.setBounds(50, 140, 150, 20);
        frame.getContentPane().add(maxArrivalTimeLabel);

        minServiceTimeLabel = new JLabel("Minimum service time");
        minServiceTimeLabel.setFont(timesNewRoman);
        minServiceTimeLabel.setBounds(50, 170, 150, 20);
        frame.getContentPane().add(minServiceTimeLabel);

        maxServiceTimeLabel = new JLabel("Maximum service time");
        maxServiceTimeLabel.setFont(timesNewRoman);
        maxServiceTimeLabel.setBounds(50, 200, 150, 20);
        frame.getContentPane().add(maxServiceTimeLabel);

        clientsTextField = new JTextField();
        clientsTextField.setFont(timesNewRoman);
        clientsTextField.setBounds(200, 20, 150, 20);
        frame.getContentPane().add(clientsTextField);

        queuesTextField = new JTextField();
        queuesTextField.setFont(timesNewRoman);
        queuesTextField.setBounds(200, 50, 150, 20);
        frame.getContentPane().add(queuesTextField);

        simulationIntervalTextField = new JTextField();
        simulationIntervalTextField.setFont(timesNewRoman);
        simulationIntervalTextField.setBounds(200, 80, 150, 20);
        frame.getContentPane().add(simulationIntervalTextField);

        minArrivalTimeTextField = new JTextField();
        minArrivalTimeTextField.setFont(timesNewRoman);
        minArrivalTimeTextField.setBounds(200, 110, 150, 20);
        frame.getContentPane().add(minArrivalTimeTextField);

        maxArrivalTimeTextField = new JTextField();
        maxArrivalTimeTextField.setFont(timesNewRoman);
        maxArrivalTimeTextField.setBounds(200, 140, 150, 20);
        frame.getContentPane().add(maxArrivalTimeTextField);

        minServiceTimeTextField = new JTextField();
        minServiceTimeTextField.setFont(timesNewRoman);
        minServiceTimeTextField.setBounds(200, 170, 150, 20);
        frame.getContentPane().add(minServiceTimeTextField);

        maxServiceTimeTextField = new JTextField();
        maxServiceTimeTextField.setFont(timesNewRoman);
        maxServiceTimeTextField.setBounds(200, 200, 150, 20);
        frame.getContentPane().add(maxServiceTimeTextField);

        startButton = new JButton("Start");
        startButton.setFont(timesNewRoman);
        startButton.setBounds(50, 330, 150, 20);
        frame.getContentPane().add(startButton);

        display = new JTextArea();
        display.setFont(timesNewRoman);
        display.setEditable(false);
        display.setColumns(10);
        scrollPane = new JScrollPane(display);
        scrollPane.setBounds(400, 20, 250, 400);
        scrollPane.setVisible(true);
        frame.getContentPane().add(scrollPane);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel strategyLabel = new JLabel("Strategy:");
        strategyLabel.setFont(timesNewRoman);
        strategyLabel.setBounds(50, 230, 150, 20);
        frame.getContentPane().add(strategyLabel);

        JButton queueButton = new JButton("QUEUE");
        queueButton.setFont(timesNewRoman);
        queueButton.setBounds(200, 230, 100, 20);
        frame.getContentPane().add(queueButton);

        JButton timeButton = new JButton("TIME");
        timeButton.setFont(timesNewRoman);
        timeButton.setBounds(200, 260, 100, 20);
        frame.getContentPane().add(timeButton);

        queueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationManager.setStrategy(SelectionPolicy.SHORTEST_QUEUE);
            }
        });

        timeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationManager.setStrategy(SelectionPolicy.SHORTEST_TIME);
            }
        });


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int clients = Integer.parseInt(clientsTextField.getText());
                    int queues = Integer.parseInt(queuesTextField.getText());
                    int simulationInterval = Integer.parseInt(simulationIntervalTextField.getText());
                    int minArrivalTime = Integer.parseInt(minArrivalTimeTextField.getText());
                    int maxArrivalTime = Integer.parseInt(maxArrivalTimeTextField.getText());
                    int minServiceTime = Integer.parseInt(minServiceTimeTextField.getText());
                    int maxServiceTime = Integer.parseInt(maxServiceTimeTextField.getText());

                // Check for invalid inputs
                if (clients <= 0 || queues <= 0) {
                    JOptionPane.showMessageDialog(frame, "Number of clients and queues must be greater than 0", "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (maxArrivalTime <= minArrivalTime) {
                    JOptionPane.showMessageDialog(frame, "Max arrival time must be greater than min arrival time", "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (maxServiceTime <= minServiceTime) {
                    JOptionPane.showMessageDialog(frame, "Max service time must be greater than min service time", "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if a strategy is selected
                if (SimulationManager.selectionPolicy == null) {
                    JOptionPane.showMessageDialog(frame, "Choose a strategy", "Invalid Inputs", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                    SimulationManager simulationManager = new SimulationManager(queues, clients, simulationInterval, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, UI.this, SimulationManager.selectionPolicy);
                    Thread thread = new Thread(simulationManager);
                    thread.start();

            }
        });


    }

    public void appendText(String text) {
        display.append(text + "\n");
        display.setCaretPosition(display.getDocument().getLength());
    }


}