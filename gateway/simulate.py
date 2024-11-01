import threading
import requests
import random
import time
import math

ADAPTER_URL = "localhost:8088"

# URLs of the four endpoints
endpoints = [
    "http://"+ADAPTER_URL+"/api/data/temperature",
    "http://"+ADAPTER_URL+"/api/data/heartrate",
    "http://"+ADAPTER_URL+"/api/data/glucose",
    "http://"+ADAPTER_URL+"/api/data/acceleration",
]



value_ranges = [
    (0, 100),    # acceleration range
    (50, 120),   # heartrate range
    (35, 42),    # temperature range (in Celsius, e.g., 35-42)
    (70, 180)    # glucose range (mg/dL)
]

# Time intervals for each endpoint (in seconds)
intervals = [10, 5, 15, 1]

baseline_values = {
    "temperature": 37.0,  # average human body temperature
    "heartrate": 75,      # average resting heart rate
    "glucose": 90,        # normal blood glucose level
    "acceleration": 10    # arbitrary baseline for body movement
}

# Fluctuation functions
def get_temperature():
    # Simulate temperature with minor daily variations
    return baseline_values["temperature"] + 0.3 * math.sin(time.time() / 300)

def get_heartrate():
    # Simulate resting heart rate with periodic increases
    baseline = baseline_values["heartrate"]
    variation = random.choice([0, 5, -3])  # occasional changes to simulate activity
    return baseline + variation + random.gauss(0, 2)

def get_glucose():
    # Simulate blood glucose rising and falling throughout the day
    baseline = baseline_values["glucose"]
    # simulate peaks post-meals roughly every 4 hours
    peak = 50 if (int(time.time()) % (4 * 3600)) < 600 else 0
    return baseline + peak + random.gauss(0, 10)

def get_acceleration():
    # Minor random variations with occasional spikes for movement
    return baseline_values["acceleration"] + random.gauss(0, 1) + (5 * random.choice([0, 1]) if random.random() < 0.1 else 0)

data_generators = [get_temperature, get_heartrate, get_glucose, get_acceleration]

# Function to send POST request to an endpoint with a random number
def send_post_request(url, interval, data_function):
    while True:
        # Generate a random number for the payload
        payload = {"data": str(round(data_function(), 2))}
        try:
            response = requests.post(url, json=payload)
            print(f"Sent to {url}: {payload}, Response: {response.status_code}")
        except Exception as e:
            print(f"Error sending to {url}: {e}")
        
        # Wait for the specified interval before sending the next request
        time.sleep(interval)

# Start a thread for each endpoint with its own interval
for i in range(4):
    thread = threading.Thread(target=send_post_request, args=(endpoints[i], intervals[i], data_generators[i]))
    thread.start()
