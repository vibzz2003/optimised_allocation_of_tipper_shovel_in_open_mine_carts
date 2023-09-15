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

public class EpochAllocation {
    public static void main(String[] args) {
        System.out.println("            Assign_Truck "+"            To_Shovel "+"      Unload_at_Unld_Point "+"     Total_Production ");
        int j=0;
        while(j<100){
            List<Truck> trucks = new ArrayList<>();
            trucks.add(new Truck(56, 50, 39));
            trucks.add(new Truck(90, 45, 32));
            trucks.add(new Truck(72, 52, 37));
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

            List<TruckQStr> TruckL = new ArrayList<>();
            TruckL.add(new TruckQStr(0, trucks.get(0), shovel1UnloadingPoints.get(0)));
            TruckL.add(new TruckQStr(0, trucks.get(1), shovel1UnloadingPoints.get(0)));
            TruckL.add(new TruckQStr(0, trucks.get(2), shovel1UnloadingPoints.get(0)));
            TruckL.add(new TruckQStr(0, trucks.get(3), shovel1UnloadingPoints.get(0)));

            List<ShovelQstr> ShovelL = new ArrayList<>();
            ShovelL.add(new ShovelQstr(0, shovels.get(0)));
            ShovelL.add(new ShovelQstr(0, shovels.get(1)));
            int totalProduction=0;
            int minute=0;
            while (minute <=100) {
                int f=0;
                ShovelQstr currentShovel = getRandomAndRemoveValue(ShovelL);
                TruckQStr currentTruck = getRandomAndRemoveValue(TruckL);
                
                if(currentTruck.TruckTime<=minute) {
                    System.out.println("Minute " + minute + ":");
                    f=1;
                    double travelTimeToShovel = currentTruck.Truck.getTravelTime(currentTruck.Upt.distance, true);
                    double productionTime = currentTruck.Truck.capacity / currentShovel.Shovel.productionSpeed;
                    UnloadingPoint selectedUnloadingPoint = currentShovel.Shovel.selectBestUnloadingPoint(currentTruck.Truck);
                    double travelTimeToUnloading = currentTruck.Truck.getTravelTime(selectedUnloadingPoint.distance, true);
                    double Sfinal = currentTruck.TruckTime+travelTimeToShovel+productionTime;
                    totalProduction = totalProduction + currentTruck.Truck.capacity;

                    double TruckTime = currentTruck.TruckTime+travelTimeToShovel+(2*productionTime)+travelTimeToUnloading;
                    
                    System.out.println("           "+currentTruck+"       "+currentShovel+"         "+selectedUnloadingPoint.distance+"                "+"         "+totalProduction);
                    
                    Sfinal=Math.ceil(Sfinal);
                    TruckTime=Math.ceil(TruckTime);
                    ShovelL.add(new ShovelQstr(Sfinal,currentShovel.Shovel));
                    TruckL.add(new TruckQStr(TruckTime,currentTruck.Truck, selectedUnloadingPoint));
                }
                else{
                    ShovelL.add(currentShovel);
                    TruckL.add(currentTruck);
                    minute++;
                }
            }
        j++;
        System.out.println("-------------------------------------------------------------------------------------------------------");
        }
    }

    public static <T> T getRandomAndRemoveValue(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("null");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        T randomValue = list.remove(randomIndex);
        return randomValue;
    }
    
    
}