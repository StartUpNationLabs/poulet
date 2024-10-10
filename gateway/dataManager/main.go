package main


func main() {
    rabbitMQClient := &RabbitMQClient{}
    prometheusClient := &PrometheusClient{}
    compacter := &Compacter{}
    alerter := &Alerter{}


    
    prometheusClient.init()
    compacter.init(prometheusClient)
    alerter.init(compacter)
    rabbitMQClient.init(alerter)


	rabbitMQClient.consume("sensor.clean.#")

   // prometheusClient.close()
    rabbitMQClient.close()
}

