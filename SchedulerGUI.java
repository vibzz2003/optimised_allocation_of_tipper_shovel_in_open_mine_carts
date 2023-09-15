import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SchedulerGUI {

    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea outputTextArea;
    private JTextField shovelsField;
    private JTextField trucksField;
    private JTextField unloadingPointsField;
    private List<TruckPanel> truckPanels;
    private List<ShovelPanel> shovelPanels;

    private List<Truck> trucks;
    private List<Shovel> shovels;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SchedulerGUI().initialize();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initialize() {
        frame = new JFrame("Shovel & Truck Scheduler");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        outputTextArea = new JTextArea(10, 40);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        createHomePage();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void createHomePage() {
        mainPanel.removeAll();
        mainPanel.repaint();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        JLabel shovelsLabel = new JLabel("Number of Shovels:");
        shovelsField = new JTextField();
        JLabel trucksLabel = new JLabel("Number of Trucks:");
        trucksField = new JTextField();
        JLabel unloadingPointsLabel = new JLabel("Number of Unloading Points:");
        unloadingPointsField = new JTextField();

        JButton nextPageButton = new JButton("Next");
        nextPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int numShovels = Integer.parseInt(shovelsField.getText());
                    int numTrucks = Integer.parseInt(trucksField.getText());
                    int numUnloadingPoints = Integer.parseInt(unloadingPointsField.getText());

                    trucks = new ArrayList<>();
                    shovels = new ArrayList<>();
                    truckPanels = new ArrayList<>();
                    shovelPanels = new ArrayList<>();

                    createTruckPage(numTrucks);
                    createShovelPage(numShovels, numUnloadingPoints);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for shovels, trucks, and unloading points.");
                }
            }
        });

        inputPanel.add(shovelsLabel);
        inputPanel.add(shovelsField);
        inputPanel.add(trucksLabel);
        inputPanel.add(trucksField);
        inputPanel.add(unloadingPointsLabel);
        inputPanel.add(unloadingPointsField);
        inputPanel.add(new JLabel()); // Empty space for formatting
        inputPanel.add(nextPageButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        frame.revalidate();
    }

    public void createTruckPage(int numTrucks) {
        mainPanel.removeAll();
        mainPanel.repaint();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Truck Details");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(titleLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        truckPanels.clear();

        for (int i = 0; i < numTrucks; i++) {
            TruckPanel truckPanel = new TruckPanel(i + 1);
            inputPanel.add(truckPanel);
            truckPanels.add(truckPanel);
        }

        JButton nextPageButton = new JButton("Next");
        nextPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    trucks.clear();
                    for (TruckPanel truckPanel : truckPanels) {
                        int capacity = truckPanel.getCapacity();
                        double emptySpeed = truckPanel.getEmptySpeed();
                        double loadedSpeed = truckPanel.getLoadedSpeed();
                        trucks.add(new Truck(capacity, emptySpeed, loadedSpeed));
                    }
                    createShovelPage(shovels.size(), shovels.isEmpty() ? 0 : shovels.get(0).unloadingPoints.size());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid truck details.");
                }
            }
        });

        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(nextPageButton);

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        frame.revalidate();
    }

    public void createShovelPage(int numShovels, int numUnloadingPoints) {
        mainPanel.removeAll();
        mainPanel.repaint();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Shovel Details");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(titleLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        shovelPanels.clear();

        for (int i = 0; i < numShovels; i++) {
            ShovelPanel shovelPanel = new ShovelPanel(i + 1, numUnloadingPoints);
            inputPanel.add(shovelPanel);
            shovelPanels.add(shovelPanel);
        }

        JButton scheduleButton = new JButton("Schedule");
        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    shovels.clear();
                    for (ShovelPanel shovelPanel : shovelPanels) {
                        int productionSpeed = shovelPanel.getProductionSpeed();
                        List<Integer> unloadingPoints = shovelPanel.getUnloadingPoints();
                        List<UnloadingPoint> unloadingPointList = new ArrayList<>();
                        for (int distance : unloadingPoints) {
                            unloadingPointList.add(new UnloadingPoint(distance));
                        }
                        shovels.add(new Shovel(productionSpeed, unloadingPointList));
                    }

                    displayShovelTruckInfo();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid shovel details.");
                }
            }
        });

        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        inputPanel.add(scheduleButton);

        JScrollPane scrollPane = new JScrollPane(inputPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        frame.revalidate();
    }

    private void displayShovelTruckInfo() {
        // Create a new panel or dialog to display shovel and truck information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        // Populate the infoPanel with shovel and truck information
        for (Shovel shovel : shovels) {
            JLabel shovelLabel = new JLabel("Shovel Production Speed: " + shovel.productionSpeed);
            infoPanel.add(shovelLabel);
        }

        for (Truck truck : trucks) {
            JLabel truckLabel = new JLabel("Truck Capacity: " + truck.capacity + ", Empty Speed: " + truck.emptySpeed + ", Loaded Speed: " + truck.loadedSpeed);
            infoPanel.add(truckLabel);
        }

        // Create a dialog to display the information
        JDialog infoDialog = new JDialog(frame, "Shovel & Truck Information", true);
        infoDialog.add(infoPanel);
        infoDialog.pack();
        infoDialog.setLocationRelativeTo(frame);
        infoDialog.setVisible(true);
    }

    public static class Truck {
        int capacity;
        double emptySpeed;
        double loadedSpeed;

        public Truck(int capacity, double emptySpeed, double loadedSpeed) {
            this.capacity = capacity;
            this.emptySpeed = emptySpeed;
            this.loadedSpeed = loadedSpeed;
        }

        public double getTravelTime(double distance, boolean loaded) {
            if (loaded) {
                return distance / loadedSpeed;
            } else {
                return distance / emptySpeed;
            }
        }
    }

    public static class UnloadingPoint {
        int distance; // in km

        public UnloadingPoint(int distance) {
            this.distance = distance;
        }
    }

    public static class Shovel {
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

    private class TruckPanel extends JPanel {
        private JTextField capacityField;
        private JTextField emptySpeedField;
        private JTextField loadedSpeedField;

        public TruckPanel(int truckNumber) {
            setLayout(new GridLayout(1, 4));

            JLabel truckLabel = new JLabel("Truck " + truckNumber);
            capacityField = new JTextField();
            emptySpeedField = new JTextField();
            loadedSpeedField = new JTextField();

            add(truckLabel);
            add(capacityField);
            add(emptySpeedField);
            add(loadedSpeedField);
        }

        public int getCapacity() {
            return Integer.parseInt(capacityField.getText());
        }

        public double getEmptySpeed() {
            return Double.parseDouble(emptySpeedField.getText());
        }

        public double getLoadedSpeed() {
            return Double.parseDouble(loadedSpeedField.getText());
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

    private class ShovelPanel extends JPanel {
        private JTextField productionSpeedField;
        private List<JTextField> unloadingPointFields;

        public ShovelPanel(int shovelNumber, int numUnloadingPoints) {
            setLayout(new GridLayout(1, numUnloadingPoints + 2));

            JLabel shovelLabel = new JLabel("Shovel " + shovelNumber);
            productionSpeedField = new JTextField();
            unloadingPointFields = new ArrayList<>();

            add(shovelLabel);
            add(productionSpeedField);

            for (int i = 0; i < numUnloadingPoints; i++) {
                JTextField distanceField = new JTextField();
                unloadingPointFields.add(distanceField);
                add(distanceField);
            }
        }

        public int getProductionSpeed() {
            return Integer.parseInt(productionSpeedField.getText());
        }

        public List<Integer> getUnloadingPoints() {
            List<Integer> distances = new ArrayList<>();
            for (JTextField distanceField : unloadingPointFields) {
                distances.add(Integer.parseInt(distanceField.getText()));
            }
            return distances;
        }
    }
}
