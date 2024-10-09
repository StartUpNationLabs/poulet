// main.go
package main


func main() {
    client := &RabbitMQClient{}
    client.init()
	client.consume("sensor.raw.#")
    client.close()
}

