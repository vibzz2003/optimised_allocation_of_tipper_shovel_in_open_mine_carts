import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Truck {
     public double total_capacity;
    public double x_capacity;
    public double loadedspeed;
    public double emptyspeed;
    public double x_loadspeed;

    // Corrected constructor to initialize x_loadspeed
    Truck(double total_capacity, double x_capacity, double loadedspeed, double emptyspeed) {
        this.total_capacity = total_capacity;
        this.x_capacity = x_capacity;
        this.loadedspeed = loadedspeed;
        this.emptyspeed = emptyspeed;
        this.x_loadspeed = loadedspeed - ((x_capacity) * (loadedspeed - emptyspeed));
    }

    double getTraveltime(double distance, boolean fullloaded) {
        if (fullloaded) {
            return distance / loadedspeed;
        } else {
            return distance / x_loadspeed;
        }
    }

    double returnTime(double distance){
        double Rt = distance/emptyspeed;
        return Rt;
    }
}

class Shovel extends Truck {
    public double NoOfcycles;
    public double shovelcapacity;
    public double x_time;

    // Corrected constructor to call the superclass constructor
    Shovel(double total_capacity, double x_capacity, double loadedspeed, double emptyspeed, double shovelcapacity, double x_time) {
        super(total_capacity, x_capacity, loadedspeed, emptyspeed);
        this.shovelcapacity = shovelcapacity;
        this.x_time = x_time;
    }

    double cycle() {
        NoOfcycles = total_capacity / shovelcapacity;
        return NoOfcycles;
    }

    double totaltimetoload(boolean full) {
        if (full) {
            return x_time * NoOfcycles;
        } else {
            return NoOfcycles * x_capacity * x_time;
        }
    }

    double Haultime(){
        double haultime = returnTime(100.00) + totaltimetoload(false) + getTraveltime(100.00, false);
        return haultime;
    }
}

public class Frame {
    // Create a list to store truck objects
    private static List<Truck> trucks = new ArrayList<>();

    public static void main(String[] args) {
        // Create a JFrame (window)
        JFrame frame = new JFrame("Simple GUI Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Create a JButton for calculating truck time
        JButton truckButton = new JButton("Calculate Truck Time");
        truckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform calculations using Truck class
                Truck truck = new Truck(45.00, 0.35, 50.00, 60.00);
                double truckTime = truck.getTraveltime(100.00, false);
                JOptionPane.showMessageDialog(frame, "Truck Time: " + truckTime + " hours");
            }
        });

        // Create a JButton for calculating shovel time
        JButton shovelButton = new JButton("Calculate Shovel Time");
        shovelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform calculations using Shovel class
                Shovel shovel = new Shovel(45.00, 0.35, 50.00, 60.00, 5.00, 2.00);
                double cycleCount = shovel.cycle();
                double totalLoadTime = shovel.totaltimetoload(false);
                double haulTime = shovel.Haultime();
                JOptionPane.showMessageDialog(frame, "Cycle Count: " + cycleCount + "\nTotal Load Time: " + totalLoadTime + " hours\nHaul Time: " + haulTime + " hours");
            }
        });

        // Create a JButton for taking the number of trucks as input
        JButton inputTruckButton = new JButton("Enter Number of Trucks");
        inputTruckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter the number of trucks:");
                try {
                    int numTrucks = Integer.parseInt(input);
                    JOptionPane.showMessageDialog(frame, "Number of Trucks entered: " + numTrucks);

                    // Add the specified number of truck objects to the list
                    for (int i = 0; i < numTrucks; i++) {
                        trucks.add(new Truck(45.00, 0.35, 50.00, 60.00));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
                }
            }
        });

        // Add the buttons to the frame's content pane
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add(truckButton);
        contentPane.add(shovelButton);
        contentPane.add(inputTruckButton);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }
}