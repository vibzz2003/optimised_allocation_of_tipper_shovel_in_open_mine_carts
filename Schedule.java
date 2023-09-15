import java.util.*;
import java.lang.Math;

class Truck {
    int capacity;
    double emptySpeed;  
    double loadedSpeed; 
    
    public Truck(int capacity, double emptySpeed, double loadedSpeed) {
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

public class Schedule {
    public static void main(String[] args) {
        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck(56, 50, 39));
        trucks.add(new Truck(56, 50, 39));
        trucks.add(new Truck(56, 50, 39));
        trucks.add(new Truck(56, 50, 39));
        
        List<Shovel> shovels = new ArrayList<>();
        
        List<UnloadingPoint> shovel1UnloadingPoints = new ArrayList<>();
        shovel1UnloadingPoints.add(new UnloadingPoint(0));
        shovel1UnloadingPoints.add(new UnloadingPoint(2));
        shovel1UnloadingPoints.add(new UnloadingPoint(3));
        
        List<UnloadingPoint> shovel2UnloadingPoints = new ArrayList<>();
        shovel1UnloadingPoints.add(new UnloadingPoint(0));
        shovel2UnloadingPoints.add(new UnloadingPoint(1));
        shovel2UnloadingPoints.add(new UnloadingPoint(2));
        
        shovels.add(new Shovel(26, shovel1UnloadingPoints));
        shovels.add(new Shovel(26, shovel2UnloadingPoints));

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
        for(int i=0;i<trucks.size();i++){
            TruckQueue.offer(new TruckQStr(0,trucks.get(i),shovel1UnloadingPoints.get(0)));
        }
        
        System.out.println("            Assign_Truck "+"            To_Shovel "+"      Unload_at_Unld_Point "+"  Total_Time   "+"Total_Production ");
        int totalProduction=0;
        int f=0;

        for (int minute = 0; minute < 100; minute++) {
            ShovelQstr currentShovel = ShovelQueue.poll();
            TruckQStr currentTruck = TruckQueue.poll();
            
            System.out.println("Minute " + minute + ":");
            
            if(currentTruck != null && currentTruck.TruckTime==minute) {
                f=1;
                double travelTimeToShovel = currentTruck.Truck.getTravelTime(currentTruck.Upt.distance, true);
                double productionTime = currentTruck.Truck.capacity / currentShovel.Shovel.productionSpeed;
                UnloadingPoint selectedUnloadingPoint = currentShovel.Shovel.selectBestUnloadingPoint(currentTruck.Truck);
                double travelTimeToUnloading = currentTruck.Truck.getTravelTime(selectedUnloadingPoint.distance, true);
                double Sfinal = travelTimeToShovel+productionTime;
                totalProduction = totalProduction + currentTruck.Truck.capacity;

                double TruckTime = travelTimeToShovel+(2*productionTime)+travelTimeToUnloading;
                
                System.out.println("           "+currentTruck+"       "+currentShovel+"         "+selectedUnloadingPoint.distance+"                "+Math.round(TruckTime)+"         "+totalProduction);
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
        }
    }
    
    
}
