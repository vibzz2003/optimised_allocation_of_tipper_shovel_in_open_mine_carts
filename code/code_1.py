class Truck:
    def __init__(self, capacity, emptySpeed, loadedSpeed):
        self.capacity = capacity
        self.emptySpeed = emptySpeed
        self.loadedSpeed = loadedSpeed
    
    def getTravelTime(self, distance, loaded):
        if loaded:
            return distance / self.loadedSpeed
        else:
            return distance / self.emptySpeed

class Shovel:
    def __init__(self, productionSpeed, unloadingPoints):
        self.productionSpeed = productionSpeed
        self.unloadingPoints = unloadingPoints
    
    def selectBestUnloadingPoint(self, truck):
        bestPoint = self.unloadingPoints[0]
        minTotalTime = float('inf')
        for point in self.unloadingPoints:
            travelTimeToUnloading = truck.getTravelTime(point.distance, True)
            productionTime = truck.capacity / self.productionSpeed if self.productionSpeed > 0 else 0
            returnTime = truck.getTravelTime(point.distance, False)
            totalTime = travelTimeToUnloading + productionTime + returnTime
            if totalTime < minTotalTime:
                minTotalTime = totalTime
                bestPoint = point
        return bestPoint
    
class UnloadingPoint:
    def __init__(self, distance):
        self.distance = distance

class TruckQStr:
    def __init__(self, TruckTime, Truck, Upt):
        self.TruckTime = TruckTime
        self.Truck = Truck
        self.Upt = Upt

class ShovelQstr:
    def __init__(self, ShovelV, Shovel):
        self.ShovelV = ShovelV
        self.Shovel = Shovel

from typing import List

class Scheduler:
    def __init__(self):
        pass

    def main(self):
        truckcount: int
        print("ENTER THE NUMBER OF TRUCKS")
        truckcount = int(input())
        trucks: List[Truck] = []
        for i in range(1, truckcount+1):
            trucks.append(Truck(56, 50.00, 39.00))
        shovels: List[Shovel] = []
        shovel1UnloadingPoints: List[UnloadingPoint] = []
        shovel1UnloadingPoints.append(UnloadingPoint(0))
        shovel1UnloadingPoints.append(UnloadingPoint(2))
        shovel1UnloadingPoints.append(UnloadingPoint(3))
        shovel2UnloadingPoints: List[UnloadingPoint] = []
        shovel2UnloadingPoints.append(UnloadingPoint(0))
        shovel2UnloadingPoints.append(UnloadingPoint(1))
        shovel2UnloadingPoints.append(UnloadingPoint(2))

        shovels.append(Shovel(26, shovel1UnloadingPoints))
        shovels.append(Shovel(26, shovel2UnloadingPoints))
        customComparator = lambda pair1, pair2: pair1.ShovelV - pair2.ShovelV
        ShovelQueue = []
        for i in range(len(shovels)):
           ShovelQueue.append(ShovelQstr(0, shovels[i]))
        