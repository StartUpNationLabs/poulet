// main.go
package main

func main() {
    rabbitMQClient := &RabbitMQClient{}
    prometheusClient := &PrometheusClient{}
    compacter := &Compacter{}

    rabbitMQClient.init()
    prometheusClient.init()
    compacter.init(prometheusClient)

	rabbitMQClient.consume("sensor.clean.#", compacter)

   // prometheusClient.close()
    rabbitMQClient.close()
}

