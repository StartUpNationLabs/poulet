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

def send_trend_data(url, interval, value_range):
    current_value = random.randint(value_range[0], value_range[1])

    while True:
        # Introduce a gradual upward trend
        trend_direction = random.choice([-1, 1])  # Randomly decide increase or decrease
        trend_value = random.randint(0, 2) * trend_direction  # Apply small random steps
        
        # Keep value within bounds
        current_value = min(max(current_value + trend_value, value_range[0]), value_range[1])
        
        payload = {"data": str(current_value)}
        try:
            response = requests.post(url, json=payload)
            print(f"Sent to {url}: {payload}, Response: {response.status_code}")
        except Exception as e:
            print(f"Error sending to {url}: {e}")
        
        # Wait for the specified interval before sending the next request
        time.sleep(interval)

# Start a thread for each endpoint with its own interval
for i in range(4):
    thread = threading.Thread(target=send_trend_data, args=(endpoints[i], intervals[i], value_ranges[i]))
    thread.start()
