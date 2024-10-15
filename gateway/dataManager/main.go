package main


func main() {
    rabbitMQClient := &RabbitMQClient{}
    prometheusClient := &PrometheusClient{}
    compacter := &Compacter{}
    downSampler := &DownSampler{}
    alerter := &Alerter{}

    prometheusClient.init()
    compacter.init(prometheusClient)
    downSampler.init(compacter)
    alerter.init(downSampler)
    rabbitMQClient.init(alerter)

	rabbitMQClient.consume("sensor.clean.#")

   // prometheusClient.close()
    rabbitMQClient.close()
}

