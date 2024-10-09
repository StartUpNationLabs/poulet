package main

import (
	"context"
	"log"
	"math/rand"
	"os"
	"time"
)

func main() {
	if os.Getenv("PROMETHEUS_SERVER") == "" {
		log.Fatal("PROMETHEUS_SERVER environment variable is not set")
	}

	// read is the hostname of the Prometheus server from environment variable

	client := NewClient(os.Getenv("PROMETHEUS_SERVER"))

	// Create a ticker that ticks every 200ms (5 requests per second)
	reqTicker := time.NewTicker(200 * time.Millisecond)
	// Create a ticker that ticks every 30 seconds
	batchTicker := time.NewTicker(10 * time.Second)

	// Create a batch to hold the samples
	var batch []Sample

	rand.Seed(time.Now().UnixNano()) // Seed the random number generator

	for {
		select {
		case <-reqTicker.C:
			// On each tick, create a new sample with a random heartbeat value and add it to the batch
			sample := Sample{
				Time:  time.Now(),
				Value: float64(rand.Intn(41) + 60), // Generate a random heartbeat value between 60 and 100
			}
			batch = append(batch, sample)
			log.Println("Sample added to batch: ", sample) // Log the sample added
		case <-batchTicker.C:
			// On each tick of the 30-second ticker, send the batch of samples
			resp, err := client.Write(context.Background(), &WriteRequest{
				TimeSeries: []TimeSeries{
					{
						Labels: []Label{
							{
								Name:  "__name__",
								Value: "heartbeat",
							},
							{
								Name:  "patient_id",
								Value: "1",
							},
						},
						Sample: batch,
					},
				},
			})
			if err != nil {
				log.Fatal(err)
			}

			log.Println("Batch sent: ", resp) // Log the response after sending the batch

			// Clear the batch for the next round of samples
			batch = []Sample{}
		}
	}
}
