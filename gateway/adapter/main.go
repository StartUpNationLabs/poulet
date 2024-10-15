// main.go
package main


func main() {
    rabbitMQClient := &RabbitMQClient{}
    rabbitMQClient.init()
    StartServer(rabbitMQClient)

    rabbitMQClient.close()
}

