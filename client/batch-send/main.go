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

	var heartRateBatch []Sample
	var fallDetectedBatch []Sample

	rand.Seed(time.Now().UnixNano()) // Seed the random number generator

	for {
		select {
		case <-reqTicker.C:
			sample := Sample{
				Time:  time.Now(),
				Value: float64(rand.Intn(41) + 60), 
			}
			heartRateBatch = append(heartRateBatch, sample)
			log.Println("Heart rate sample added to batch: ", sample) 

			fallSample := Sample{
				Time:  time.Now(),
				Value: float64(isFallDetected()), 
			}
			fallDetectedBatch = append(fallDetectedBatch, fallSample)
			log.Println("Fall detection sample added to batch: ", fallSample)

		case <-batchTicker.C:
			// On each tick of the 30-second ticker, send the batches of samples
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
						Sample: heartRateBatch,
					},
					{
						Labels: []Label{
							{
								Name:  "__name__",
								Value: "fall_detected",
							},
							{
								Name:  "patient_id",
								Value: "1",
							},
						},
						Sample: fallDetectedBatch,
					},
				},
			})
			if err != nil {
				log.Fatal(err)
			}

			log.Println("Batch sent: ", resp) // Log the response after sending the batch

			heartRateBatch = []Sample{}
			fallDetectedBatch = []Sample{}
		}
	}
}

func isFallDetected() int {
	return rand.Intn(2)
	/*accelerationX := rand.Float64() * 10  
	accelerationY := rand.Float64() * 10  
	accelerationZ := rand.Float64() * 10  

	const fallThreshold = 5.0  

	if accelerationX > fallThreshold || accelerationY > fallThreshold || accelerationZ > fallThreshold {
		return 1 
	}
	return 0 */
}

