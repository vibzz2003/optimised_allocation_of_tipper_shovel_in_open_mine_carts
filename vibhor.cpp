#include<iostream>
#include<deque>
#include<utility>
#include<vector>
#include<numeric>
#include<limits>
#include<list>
using namespace std;

class Truck{
    public:
    int capacity;
    double emptyspeed;
    double loadedspeed;
    float totaltime;
    

    Truck(int capacity, double emptyspeed, double loadedspeed) {
    this->capacity = capacity;
    this->emptyspeed = emptyspeed;
    this->loadedspeed = loadedspeed;
    }

    double getTravelTime(double distance, bool loaded) {
    if(loaded){
        return distance/loadedspeed;
    }else{
        return distance/emptyspeed;
    }
   }

   public void settotaltime(float time){
    this->totaltime = time;
   }
};

class Shovel{
    public:
    int production_speed;
    float productiontime;
    
    std::vector<UnloadingPoint>unloadingPoints;

    Shovel(int production_speed, std::vector<UnloadingPoint>unloadingPoints) {
        this->production_speed = production_speed;
        this->unloadingPoints = unloadingPoints;
    }

    public void setproductiontime(float time){
        this->productiontime = time;
    }

    UnloadingPoint selectBestUnloadingPoint(Truck truck) {
        UnloadingPoint bestPoint = unloadingPoints[0];
        double minTotalTime = std::numeric_limits<double>::max();
        for (UnloadingPoint point : unloadingPoints) {
            double travelTimeToUnloading = truck.getTravelTime(point.distance, true);
            double productionTime = production_speed > 0 ? truck.capacity / production_speed : 0;
            double returnTime = truck.getTravelTime(point.distance, false);
            double totalTime = travelTimeToUnloading + productionTime + returnTime;
            if (totalTime < minTotalTime) {
                minTotalTime = totalTime;
                bestPoint = point;
            }
        }
        return bestPoint;
    }

};

class UnloadingPoint {
    public:
    int distance;
    UnloadingPoint(int distance){
        this->distance = distance;
    }
};

int main(){
    int trucknumber;
    cout<<"WHAT IS THE NUMBER OF TRUCKS AVAILABLE?"<<endl;
    cin>>trucknumber;
    std::vector<Truck> trucklist;
    std::vector<Shovel> shovellist;
    std::vector<Truck> workingtrucks;
    std::vector<Shovel> workingshovels;
    double prevwaittime = 0;
    double currwaittime = 0;
    for(int i=0; i<trucknumber;i++){
        trucklist.insert(new Truck(56,50,39));
    }
    while(1){
        int unloadingpoint1,unloadingpoint2,count;
        std::vector<UnloadingPoint> unloadingpoints;
        cout<<"ENTER THE DISTANCE TO THE TWO UNLOADING POINTS"<<endl;
        cin>>unloadingpoint1;
        cin>>unloadingpoint2;
        unloadingpoints.insert(new UnloadingPoint(unloadingpoint1));
        unloadingpoints.insert(new UnloadingPoint(unloadingpoint2));
        shovellist.insert(new Shovel(1600,unloadingpoints));
        count = shovellist.size();

        

        for (int i = 1; i < count+1; i++)
        {
            if(trucklist!=NULL && shovellist!=NULL){
                Truck currenttruck = trucklist.pop_back();
                Shovel currentshovel = shovellist.pop_back();
                UnloadingPoint selectedUnloadingPoint = currentshovel.selectBestUnloadingPoint(currenttruck);
                double travelTimeToUnloading = currenttruck.getTravelTime(selectedUnloadingPoint.distance, true);
                double productionTime = currentshovel.productionSpeed > 0 ? currenttruck.capacity / currentshovel.productionSpeed : 0;
                double returnTime = currenttruck.getTravelTime(selectedUnloadingPoint.distance, false);
                double totalTime = travelTimeToUnloading + productionTime + returnTime;
                currenttruck.settotaltime(totalTime);
                workingtrucks.push_back(currenttruck);
                workingshovels.push_back(currentshovel);
            }
            else{
               switch(true){
                case (trucklist==NULL && shovellist==NULL):
                currwaittime += (totalTime - productionTime);
                break;
                case (trucklist==NULL && shovellist!=NULL):
                currwaittime += totalTime + (totalTime - productionTime);
                break;
                case (trucklist!=NULL && shovellist==NULL):
                currwaittime += productionTime;
               }
            }

        }

        if(currwaittime>prevwaittime && count!=1){
            break;
        }
        prevwaittime = currwaittime;

       
    }
}

