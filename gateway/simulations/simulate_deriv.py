import threading
import requests
import random
import time

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
    (35, 42),    # temperature range (in Celsius)
    (70, 180)    # glucose range (mg/dL)
]

intervals = [10, 5, 15, 1]  # Time intervals for each endpoint (in seconds)

# Function to simulate rapid change for derivatives
def send_rapid_change(url, interval, value_range):
    while True:
        # Generate two random values to simulate rapid change
        start_value = random.randint(value_range[0], value_range[1])
        end_value = random.randint(value_range[0], value_range[1])
        
        # Simulate gradual increase or decrease to create a derivative effect
        step = (end_value - start_value) / 5
        current_value = start_value
        for _ in range(5):
            payload = {"data": str(current_value)}
            try:
                response = requests.post(url, json=payload)
                print(f"Sent to {url}: {payload}, Response: {response.status_code}")
            except Exception as e:
                print(f"Error sending to {url}: {e}")
            current_value += step
            time.sleep(interval / 5)

# Start a thread for each endpoint with its own interval
for i in range(4):
    thread = threading.Thread(target=send_rapid_change, args=(endpoints[i], intervals[i], value_ranges[i]))
    thread.start()
