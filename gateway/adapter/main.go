// main.go
package main


func main() {
    client := &RabbitMQClient{}
    client.init()
    StartServer(client)

    client.close()
}

