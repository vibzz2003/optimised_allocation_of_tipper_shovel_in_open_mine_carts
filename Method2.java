import java.util.ArrayList;
import java.util.List;

class Truck{
    public
    double total_capacity;
    double x_capacity;
    double loadedspeed;
    double emptyspeed;
    double x_loadspeed = loadedspeed -((x_capacity/100)*(loadedspeed - emptyspeed));
   
    Truck(){};
    public Truck(double total_capacity, double x_capacity, double loadedspeed, double emptyspeed ){
        this.total_capacity = total_capacity;
        this.x_capacity = x_capacity;
        this.loadedspeed = loadedspeed;
        this.emptyspeed = emptyspeed;
    }

    public double getTraveltime(double distance, boolean fullloaded){
        if(fullloaded){
            return distance/loadedspeed;
        }else{
            return distance/x_loadspeed;
        }
    }

    public double getreturntime(double distance,double emptyspeed){
        return distance/emptyspeed;
    }
}

class Shovel extends Truck{
    public
    double NoOfcycles;
     double shovelcapacity; //capacity of a shovel at one time
    double x_time; //time taken to dig and dump into the shovel one at a time
    Shovel(){};
    public Shovel(double shovelcapacity, double x_time){
        this.shovelcapacity = shovelcapacity;
        this.x_time = x_time;
        
    }
    double cycle(){
        NoOfcycles = total_capacity/shovelcapacity;
        return NoOfcycles;
    }
    double totaltimetoload(boolean full){
        if(full){
            return x_time*NoOfcycles;
        }else{
            return NoOfcycles*x_capacity*x_time;
        }
    }
}
public class Method2 {
    public static void main(String args[]){
      List<Truck> trucks  = new ArrayList<>();
      trucks.add(new Truck(45.00, 35.00, 50.00, 60.00));
      Truck obj = new Truck();
      System.out.println(obj.getTraveltime(100.00, false));

      List<Shovel> shovels = new ArrayList<>();
      shovels.add(new Shovel(5.00, 2.00));
      Shovel obj1 = new Shovel();
      System.out.println(obj1.cycle());
      System.out.println(obj1.totaltimetoload(false));

    }
}

