// webServer.go
package main

import (
    "encoding/json"
    "fmt"
    "io/ioutil"
    "log"
    "net/http"
    "os"
)

type RequestData struct {
    Value string `json:"data"`
}

// handleData handles the incoming POST requests at /api/data
func handleData(metric string, client *RabbitMQClient) http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
        if r.Method == http.MethodPost {
            body, err := ioutil.ReadAll(r.Body)
            if err != nil {
                http.Error(w, "Cannot read body", http.StatusBadRequest)
                return
            }

            var data RequestData
            err = json.Unmarshal(body, &data)
            if err != nil {
                http.Error(w, "Invalid JSON", http.StatusBadRequest)
                return
            }

            // Log the value of the body
            log.Printf("%s as %s\n", metric, data.Value)

            w.WriteHeader(http.StatusOK)
            w.Write([]byte("Data received"))

            var queueName string = "sensor.raw." + metric
            client.publish(queueName, data.Value)

        } else {
            http.Error(w, "Invalid request method", http.StatusMethodNotAllowed)
        }
    }
}

// StartServer starts the HTTP server
func StartServer(client *RabbitMQClient) {
    metrics := []string{
        "acceleration",
        "heartrate",
        "temperature",
        "glucose",
    }

    for _, value := range metrics {
		http.HandleFunc("/api/data/"+value, handleData(value, client))
	}

    serverPort := "8081"
    if os.Getenv("SERVER_PORT") != "" {
        serverPort = os.Getenv("SERVER_PORT") 
	}
    fmt.Println("Server is running on port "+serverPort+"...")
    log.Fatal(http.ListenAndServe(":"+serverPort, nil))
}

