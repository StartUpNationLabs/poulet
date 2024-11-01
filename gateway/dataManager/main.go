package main

import(
    "log"
    "os"
    "github.com/newrelic/go-agent/v3/newrelic"
    "time"

)

func main() {
    app, err := newrelic.NewApplication(
        newrelic.ConfigAppName("DataManager"),
        newrelic.ConfigLicense("eu01xx66792eeb67035b3ccc1fb4ee54FFFFNRAL"),
        newrelic.ConfigAppLogForwardingEnabled(true),
    )
    if err != nil {
		log.Println("Error:","Newrelic: %w", err)
		return
	}

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

	rabbitMQClient.consume("sensor.#", app)

   // prometheusClient.close()
    rabbitMQClient.close()

    app.Shutdown(10 * time.Second)
}

