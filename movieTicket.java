package movieTicket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class movieTicket extends JFrame implements ActionListener {
    private List<Queue<String>> ticketQueues;
    private int currentQueueIndex;

    private JButton bookButton;
    private JButton processButton;
    private JButton showBookedButton;

    public movieTicket(int numQueues, int numCustomers) {
        ticketQueues = new ArrayList<>();
        currentQueueIndex = 0;

        initializeQueues(numQueues);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Movie Ticket Booking System");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        bookButton = new JButton("Book a Ticket");
        bookButton.addActionListener(this);

        processButton = new JButton("Process Next Ticket");
        processButton.addActionListener(this);

        showBookedButton = new JButton("Show Booked Tickets");
        showBookedButton.addActionListener(this);

        mainPanel.add(bookButton);
        mainPanel.add(processButton);
        mainPanel.add(showBookedButton);

        add(mainPanel);
    }

    private void initializeQueues(int numQueues) {
        for (int i = 0; i < numQueues; i++) {
            ticketQueues.add(new LinkedList<>());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookButton) {
            String customerName = JOptionPane.showInputDialog(this, "Enter the name of the customer:");
            Queue<String> currentQueue = ticketQueues.get(currentQueueIndex);
            currentQueue.offer(customerName);
            JOptionPane.showMessageDialog(this, "Ticket booked successfully for " + customerName);
            currentQueueIndex++;
            if (currentQueueIndex >= ticketQueues.size()) {
                currentQueueIndex = 0;
            }
        } else if (e.getSource() == processButton) {
            Queue<String> currentQueue = ticketQueues.get(currentQueueIndex);
            if (currentQueue.isEmpty()) {
                currentQueueIndex++;
                if (currentQueueIndex >= ticketQueues.size()) {
                    currentQueueIndex = 0;
                }
                currentQueue = ticketQueues.get(currentQueueIndex);
            }

            String customerName = currentQueue.poll();
            JOptionPane.showMessageDialog(this, "Ticket processed for " + customerName);
        } else if (e.getSource() == showBookedButton) {
            StringBuilder bookedTickets = new StringBuilder();
            for (Queue<String> queue : ticketQueues) {
                for (String customer : queue) {
                    bookedTickets.append(customer).append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, "Booked Tickets:\n" + bookedTickets.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int numQueues = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of ticket queues:"));
            int numCustomers = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of customers per queue:"));

            movieTicket gui = new movieTicket(numQueues, numCustomers);
            gui.setVisible(true);
        });
    }
}