# # Define parameters
# N_shovels = 3
# N_trucks = 5
# load_time = 10  # minutes
# truck_capacity = 30  # tons
# shovel_capacity = 60  # tons
# pit_to_plant_time = 20  # minutes
# available_material = 1000  # tons
# cycle_time = 90  # minutes

# # Create a queue of trucks and calculate due dates
# truck_queue = list(range(N_trucks))
# due_dates = [cycle_time - pit_to_plant_time for _ in range(N_trucks)]
# truck_queue.sort(key=lambda truck: due_dates[truck])

# # Initialize variables
# total_time = 0
# material_loaded = 0

# # Iterate through the queue and load trucks
# for truck in truck_queue:
#     remaining_capacity = truck_capacity
#     while remaining_capacity > 0:
#         # Calculate the amount of material to load on the current cycle
#         material_to_load = min(remaining_capacity, shovel_capacity, available_material - material_loaded)
        
#         # Update variables
#         remaining_capacity -= material_to_load
#         material_loaded += material_to_load
        
#         # Calculate cycle time with loading and transport
#         cycle_time_with_transport = load_time + pit_to_plant_time
        
#         # Update total time and due date for the truck
#         total_time += cycle_time_with_transport
#         due_dates[truck] -= cycle_time_with_transport
        
#         # Check if cycle time is available for the current truck
#         if due_dates[truck] >= 0:
#             pass  # Continue loading
#         else:
#             break  # Truck missed cycle due to time constraint
        
#         # Update available material
#         available_material -= material_to_load

# print(f"Total time for the whole process: {total_time:.2f} minutes")

import simpy
import random

# Define parameters
N_shovels = 3
N_trucks = 5
load_time = 10  # minutes
truck_capacity = 30  # tons
shovel_capacity = 60  # tons
pit_to_plant_time = 20  # minutes
available_material = 1000  # tons
cycle_time = 90  # minutes
simulation_duration = 480  # minutes

def truck_process(env, truck_id):
    while True:
        # Truck arrives at an unloading point
        print(f"Truck {truck_id} arrived at unloading point at {env.now:.2f} minutes")
        
        # Determine the best shovel for this truck
        # Implement your logic for shovel assignment here
        
        # Simulate time to reach the assigned shovel
        time_to_reach_shovel = random.randint(5, 15)  # Simulated time
        yield env.timeout(time_to_reach_shovel)
        
        # Simulate loading time at the shovel
        yield env.timeout(load_time)
        
        # Simulate time to return to unloading point
        yield env.timeout(pit_to_plant_time)
        
        print(f"Truck {truck_id} unloaded at plant at {env.now:.2f} minutes")

def shovel_process(env, shovel_id):
    while True:
        # Simulate filling material at the shovel
        fill_time = shovel_capacity / 10  # Simulated fill speed
        yield env.timeout(fill_time)
        
        print(f"Shovel {shovel_id} finished filling at {env.now:.2f} minutes")
        
        # Simulate shovel becoming idle
        idle_time = random.randint(5, 15)  # Simulated idle time
        yield env.timeout(idle_time)

# Create SimPy environment
env = simpy.Environment()

# Create processes for each shovel
for shovel_id in range(N_shovels):
    env.process(shovel_process(env, shovel_id))

# Create processes for each truck
for truck_id in range(N_trucks):
    env.process(truck_process(env, truck_id))

# Run simulation
env.run(until=simulation_duration)
