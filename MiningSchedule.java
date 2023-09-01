import java.util.*;
class Truck {
    int capacity;
    double emptySpeed;  
    double loadedSpeed;
    float totaltime; 
    
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

    public void settotaltime(float time){
        this.totaltime = time;
    }
}

class Shovel {
    int productionSpeed; 
    List<UnloadingPoint> unloadingPoints;
    float s_productiontime;
    
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

    public void Setproductiontime(float time){
        this.s_productiontime = time;

    }
}

class UnloadingPoint {
    int distance; // in km
    
    public UnloadingPoint(int distance) {
        this.distance = distance;
    }
}

public class MiningSchedule {
    public static void main(String[] args) {
        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck(56, 50, 39));
        trucks.add(new Truck(56, 50, 39));
        trucks.add(new Truck(56, 50, 39));
        trucks.add(new Truck(56, 50, 39));
        
        List<Shovel> shovels = new ArrayList<>();
        
        List<UnloadingPoint> shovel1UnloadingPoints = new ArrayList<>();
        shovel1UnloadingPoints.add(new UnloadingPoint(2));
        shovel1UnloadingPoints.add(new UnloadingPoint(3));
        
        List<UnloadingPoint> shovel2UnloadingPoints = new ArrayList<>();
        shovel2UnloadingPoints.add(new UnloadingPoint(1));
        shovel2UnloadingPoints.add(new UnloadingPoint(2));
        
        shovels.add(new Shovel(1600, shovel1UnloadingPoints));
        shovels.add(new Shovel(1600, shovel2UnloadingPoints));
        
        PriorityQueue<Shovel> shovelQueue = new PriorityQueue<>(Comparator.comparingInt(s -> s.productionSpeed));
        shovelQueue.addAll(shovels);

        Queue<Truck> truckQueue = new LinkedList<>(trucks);
        
        System.out.println(" Available_Trucks "+" Available_Shovels "+"  Assign_Truck "+"       To_Shovel "+"    Unload_at_Unloading_Point "+"   Total_Time   "+"             Total_Production ");

        Queue<Truck> workingtrucks = new LinkedList<>();
        Queue<Shovel> workingshovel = new LinkedList<>();


        for (int minute = 0; minute < 100; minute++) {
            Shovel currentShovel = shovelQueue.poll();
            Truck currentTruck = truckQueue.poll();
            
            
            System.out.println("Minute " + minute + ":");
            
            if (currentTruck != null) {
                UnloadingPoint selectedUnloadingPoint = currentShovel.selectBestUnloadingPoint(currentTruck);
                double travelTimeToUnloading = currentTruck.getTravelTime(selectedUnloadingPoint.distance, true);
                double productionTime = currentShovel.productionSpeed > 0 ? currentTruck.capacity / currentShovel.productionSpeed : 0;
                double returnTime = currentTruck.getTravelTime(selectedUnloadingPoint.distance, true); // Unloading time
                double totalProduction = currentShovel.productionSpeed > 0 ? currentShovel.productionSpeed * (productionTime - returnTime) : 0;

                double totalTime = travelTimeToUnloading + productionTime + returnTime;
                
                System.out.println("         "+truckQueue.size()+"            "+shovelQueue.size()+"              "+currentTruck+"       "+currentShovel+"         "+selectedUnloadingPoint.distance+"                "+totalTime+"         "+totalProduction);
                // System.out.println("Available Shovels: " + shovelQueue.size());
                // System.out.println("Assign Truck " + currentTruck + " to Shovel " + currentShovel);
                // System.out.println("Unload at Unloading Point " + selectedUnloadingPoint.distance);
                // //System.out.println("Travel Time to Unloading Point: " + travelTimeToUnloading + " hrs");
                // //System.out.println("Production Time: " + productionTime + " hrs");
                // //System.out.println("Return Time: " + returnTime + " hrs");
                // System.out.println("Total Time: " + totalTime + " hrs");
                // System.out.println("Total Production: " + totalProduction + " tons");
        
                shovelQueue.offer(currentShovel);
                truckQueue.offer(currentTruck);
            }
        }
    }
    
    public static Truck getAvailableTruck(List<Truck> trucks) {
        for (Truck truck : trucks) {
            if (truck != null) {
                trucks.remove(truck);
                return truck;
            }
        }
        return null;
    }
}