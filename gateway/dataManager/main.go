package main

import(
    "log"
    "os"
)

func main() {
    gatewayID := os.Getenv("GATEWAY_ID")
    if gatewayID == "" {
        log.Fatal("GATEWAY_ID environment variable is not set")
    }

    rabbitMQClient := &RabbitMQClient{}
    prometheusClient := &PrometheusClient{}
    compacter := &Compacter{}
    downSampler := &DownSampler{}
    alerter := &Alerter{}

    prometheusClient.init(gatewayID)
    compacter.init(prometheusClient)
    downSampler.init(compacter)
    alerter.init(downSampler)
    rabbitMQClient.init(alerter)

	rabbitMQClient.consume("sensor.#")

   // prometheusClient.close()
    rabbitMQClient.close()
}

