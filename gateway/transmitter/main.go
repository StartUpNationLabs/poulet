// main.go
package main


func main() {
    client := &RabbitMQClient{}
    client.init()
	client.consume("sensor.clean.#")
    client.close()
}

