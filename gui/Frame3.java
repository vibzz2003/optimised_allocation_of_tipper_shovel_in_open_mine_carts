import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.lang.Math;
import java.util.*;
import javax.swing.table.DefaultTableModel;

class Truck {
    double capacity;
    double emptySpeed;  
    double loadedSpeed; 
    
    public Truck(double capacity, double emptySpeed, double loadedSpeed) {
        this.capacity = capacity;
        this.emptySpeed = emptySpeed;
        this.loadedSpeed = loadedSpeed;
    }
    
    public double getTravelTime(double distance, boolean loaded) {
        if(loaded){
            return distance/loadedSpeed;
        }else{
            return distance/emptySpeed;
        }
    }
}

class Shovel {
    int productionSpeed; 
    List<UnloadingPoint> unloadingPoints;
    
    
    public Shovel(int productionSpeed, List<UnloadingPoint> unloadingPoints) {
        this.productionSpeed = productionSpeed;
        this.unloadingPoints = unloadingPoints;
    }
    
    public UnloadingPoint selectBestUnloadingPoint(Truck truck) {
        UnloadingPoint bestPoint = unloadingPoints.get(0);
        double minTotalTime = Double.MAX_VALUE;
        
        for (UnloadingPoint point : unloadingPoints) {
            double travelTimeToUnloading = truck.getTravelTime(point.distance, true);
            double productionTime = productionSpeed > 0 ? truck.capacity / productionSpeed : 0;
            double returnTime = truck.getTravelTime(point.distance, false);
            
            double totalTime = travelTimeToUnloading + productionTime + returnTime;
            
            if (totalTime < minTotalTime) {
                minTotalTime = totalTime;
                bestPoint = point;
            }
        }
        
        return bestPoint;
    }
}

class UnloadingPoint {
    int distance; // in km
    
    public UnloadingPoint(int distance) {
        this.distance = distance;
    }
}


class TruckQStr {
    double TruckTime;
    Truck Truck;
    UnloadingPoint Upt;

    public TruckQStr(double TruckTime, Truck Truck, UnloadingPoint Upt) {
        this.TruckTime = TruckTime;
        this.Truck = Truck;
        this.Upt=Upt;
    }
}

class ShovelQstr {
    double ShovelV;
    Shovel Shovel;

    public ShovelQstr(double ShovelV, Shovel Shovel) {
        this.ShovelV = ShovelV;
        this.Shovel = Shovel;
    }
}

public class Frame3 extends JFrame {

    private static List<Truck> trucks = new ArrayList<>();

    private JTextField truckCapacityField;
    private JTextField emptySpeedField;
    private JTextField loadedSpeedField;
    private JTextField shovelProductionSpeedField;
    private JTable outputTable;
    private DefaultTableModel tableModel;
    private JButton scheduleButton;
    
    public Frame3() {
        setTitle("Urban Design Scheduler");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        
        inputPanel.add(new JLabel("Truck Capacity:"));
        truckCapacityField = new JTextField();
        inputPanel.add(truckCapacityField);

        inputPanel.add(new JLabel("Empty Speed:"));
        emptySpeedField = new JTextField();
        inputPanel.add(emptySpeedField);

        inputPanel.add(new JLabel("Loaded Speed:"));
        loadedSpeedField = new JTextField();
        inputPanel.add(loadedSpeedField);

        inputPanel.add(new JLabel("Shovel Production Speed:"));
        shovelProductionSpeedField = new JTextField();
        inputPanel.add(shovelProductionSpeedField);


        scheduleButton = new JButton("Schedule");
        scheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performScheduling();
            }
        });

    

        add(inputPanel, BorderLayout.NORTH);
        add(scheduleButton, BorderLayout.CENTER);

        tableModel = new DefaultTableModel();
        outputTable = new JTable(tableModel);
        tableModel.addColumn("Assign_Truck");
        tableModel.addColumn("To_Shovel");
        tableModel.addColumn("Unload_at_Unld_Point");
        tableModel.addColumn("Total_Production");

        JScrollPane scrollPane = new JScrollPane(outputTable);
        add(scrollPane, BorderLayout.CENTER);

        add(scheduleButton, BorderLayout.SOUTH);
    }

    private void performScheduling() {
        try {
            int truckCapacity = Integer.parseInt(truckCapacityField.getText());
            double emptySpeed = Double.parseDouble(emptySpeedField.getText());
            double loadedSpeed = Double.parseDouble(loadedSpeedField.getText());
            int shovelProductionSpeed = Integer.parseInt(shovelProductionSpeedField.getText());

            List<Truck> trucks = new ArrayList<>();
            trucks.add(new Truck(truckCapacity, emptySpeed, loadedSpeed));
            trucks.add(new Truck(truckCapacity, emptySpeed, loadedSpeed));
            trucks.add(new Truck(truckCapacity, emptySpeed, loadedSpeed));
            trucks.add(new Truck(truckCapacity, emptySpeed, loadedSpeed));

            List<Shovel> shovels = new ArrayList<>();

            List<UnloadingPoint> unloadingPoints1 = new ArrayList<>();
            unloadingPoints1.add(new UnloadingPoint(0));
            unloadingPoints1.add(new UnloadingPoint(2));
            unloadingPoints1.add(new UnloadingPoint(3));

            List<UnloadingPoint> unloadingPoints2 = new ArrayList<>();
            unloadingPoints2.add(new UnloadingPoint(0));
            unloadingPoints2.add(new UnloadingPoint(1));
            unloadingPoints2.add(new UnloadingPoint(3));

            shovels.add(new Shovel(26, unloadingPoints1));
            shovels.add(new Shovel(26, unloadingPoints2));

        List<TruckQStr> TruckL = new ArrayList<>();
        TruckL.add(new TruckQStr(0, trucks.get(0), unloadingPoints1.get(0)));
        TruckL.add(new TruckQStr(0, trucks.get(1), unloadingPoints1.get(0)));
        TruckL.add(new TruckQStr(0, trucks.get(2), unloadingPoints1.get(0)));
        TruckL.add(new TruckQStr(0, trucks.get(3), unloadingPoints1.get(0)));

        List<ShovelQstr> ShovelL = new ArrayList<>();
        ShovelL.add(new ShovelQstr(0, shovels.get(0)));
        ShovelL.add(new ShovelQstr(0, shovels.get(1)));

    //     try{
    //     if (shovels.size() >= 2) {
    //         ShovelL.add(new ShovelQstr(0, shovels.get(0)));
    //         // ShovelL.add(new ShovelQstr(0, shovels.get(1)));
    //     } else {
    //         ShovelL.add(new ShovelQstr(0, shovels.get(0)));
    //     }
    // }
    // catch(Exception e){
    //     System.out.println("Thew inbdex is out of bound");
    // }



        

            // Your scheduling logic here
            // Replace the console output with updating the outputTextArea
            // Example: outputTextArea.append("Minute " + minute + ":\n");
        Comparator<ShovelQstr> customComparator = new Comparator<ShovelQstr>() {
            @Override
            public int compare(ShovelQstr pair1, ShovelQstr pair2) {
                return Double.compare(pair1.ShovelV, pair2.ShovelV);
            }
        };

        // priority queue with the custom comparator
        PriorityQueue<ShovelQstr> ShovelQueue = new PriorityQueue<>(customComparator);

        // Add elements to the priority queue
        for(int i=0;i<shovels.size();i++){
            ShovelQueue.offer(new ShovelQstr(0, shovels.get(i)));
        }


        Comparator<TruckQStr> customtComparator = new Comparator<TruckQStr>() {
            @Override
            public int compare(TruckQStr pair1, TruckQStr pair2) {
                return Double.compare(pair1.TruckTime, pair2.TruckTime);
            }
        };

        PriorityQueue<TruckQStr> TruckQueue = new PriorityQueue<>(customtComparator);
        
        // Add elements to the priority queue
        // for(int i=0;i<trucks.size();i++){
        //     TruckQueue.offer(new TruckQStr(0,trucks.get(i),unloadingPoints.get(0)));
        // }


        for (int i = 0; i < trucks.size(); i++) {
            TruckQStr truckQStr = new TruckQStr(0, trucks.get(i), unloadingPoints1.get(0));
            TruckQueue.offer(truckQStr);
            TruckQStr truckQStr1 = new TruckQStr(0, trucks.get(i), unloadingPoints2.get(0));
            TruckQueue.offer(truckQStr1);
            System.out.println("Added to TruckQueue: " + truckQStr);
        }

        tableModel.setRowCount(0); //Clear previous Output

        // outputTextArea.append("            Assign_Truck "+"            To_Shovel "+"      Unload_at_Unld_Point "+"     Total_Production ");

        tableModel.setColumnIdentifiers(new Object[]{"Assign_Truck", "To_Shovel", "Unload_at_Unld_Point", "Total_Production"});
        
        System.out.println("            Assign_Truck "+"            To_Shovel "+"      Unload_at_Unld_Point "+"     Total_Production ");
        double totalProduction=0;
        int f=0;

        for (int minute = 0; minute < 100; minute++) {
            ShovelQstr currentShovel = ShovelQueue.poll();
            TruckQStr currentTruck = TruckQueue.poll();
            
            // outputTextArea.append("Minute " + minute + ":");
            tableModel.addRow(new Object[]{"Minute " + minute + ":"});
            System.out.println("Minute " + minute + ":");
            
            if(currentTruck != null) {
                f=1;
                double travelTimeToShovel = currentTruck.Truck.getTravelTime(currentTruck.Upt.distance, true);
                double productionTime = currentTruck.Truck.capacity / currentShovel.Shovel.productionSpeed;
                UnloadingPoint selectedUnloadingPoint = currentShovel.Shovel.selectBestUnloadingPoint(currentTruck.Truck);
                double travelTimeToUnloading = currentTruck.Truck.getTravelTime(selectedUnloadingPoint.distance, true);
                double Sfinal = travelTimeToShovel+productionTime;
                totalProduction = totalProduction + currentTruck.Truck.capacity;

                double TruckTime = travelTimeToShovel+(2*productionTime)+travelTimeToUnloading;
                
                System.out.println("           "+currentTruck+"       "+currentShovel+"         "+selectedUnloadingPoint.distance+"                "+"         "+totalProduction);
                // outputTextArea.append("           "+currentTruck+"       "+currentShovel+"         "+selectedUnloadingPoint.distance+"                "+"         "+totalProduction);
                tableModel.addRow(new Object[]{currentTruck, currentShovel, selectedUnloadingPoint.distance, totalProduction});

                // System.out.println("Available Shovels: " + shovelQueue.size());
                // System.out.println("Assign Truck " + currentTruck + " to Shovel " + currentShovel);
                // System.out.println("Unload at Unloading Point " + selectedUnloadingPoint.distance);
                // //System.out.println("Travel Time to Unloading Point: " + travelTimeToUnloading + " hrs");
                // //System.out.println("Production Time: " + productionTime + " hrs");
                // //System.out.println("Return Time: " + returnTime + " hrs");
                // System.out.println("Total Time: " + totalTime + " hrs");
                // System.out.println("Total Production: " + totalProduction + " tons");

                
                ShovelQueue.offer(new ShovelQstr(Sfinal,currentShovel.Shovel));
                TruckQueue.offer(new TruckQStr(TruckTime,currentTruck.Truck, selectedUnloadingPoint));
            }
            if(f==1){
                ShovelQueue.offer(currentShovel);
                TruckQueue.offer(currentTruck);
            }

            // showOutputWindow(outputTextArea.getText());

            showOutputWindow(tableModel);
        }



        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.");
        }
    }

    //   private void showOutputWindow(String output) {
    //     JFrame outputFrame = new JFrame("Scheduling Output");
    //     outputFrame.setSize(500, 400);

    //     JTextArea outputTextArea = new JTextArea(output);
    //     JScrollPane scrollPane = new JScrollPane(outputTextArea);

    //     outputFrame.add(scrollPane);
    //     outputFrame.setVisible(true);
    // }


    private void showOutputWindow(DefaultTableModel tableModel) {
        JFrame outputFrame = new JFrame("Scheduling Output");
        outputFrame.setSize(500, 400);
    
        JTable outputTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(outputTable);
    
        outputFrame.add(scrollPane);
        outputFrame.setVisible(true);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple GUI Example");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(300, 200);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Frame3 gui = new Frame3();
                gui.setVisible(true);
            }
        });

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
                        trucks.add(new Truck(45.00, 50.00, 60.00));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
                }
            }
        });

        Container contentPane = frame.getContentPane();

        contentPane.add(inputTruckButton);

        frame.setLocationRelativeTo(null);
    }
}
