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
    (35, 42),    # temperature range (in Celsius, e.g., 35-42)
    (70, 180)    # glucose range (mg/dL)
]

# Time intervals for each endpoint (in seconds)
intervals = [10, 5, 15, 1]



# Function to send POST request to an endpoint with a random number
def send_post_request(url, interval, value_range):
    while True:
        # Generate a random number for the payload
        payload = {"data": str(random.randint(value_range[0], value_range[1]))}
        try:
            response = requests.post(url, json=payload)
            print(f"Sent to {url}: {payload}, Response: {response.status_code}")
        except Exception as e:
            print(f"Error sending to {url}: {e}")
        
        # Wait for the specified interval before sending the next request
        time.sleep(interval)

# Start a thread for each endpoint with its own interval
for i in range(4):
    thread = threading.Thread(target=send_post_request, args=(endpoints[i], intervals[i], value_ranges[i]))
    thread.start()
