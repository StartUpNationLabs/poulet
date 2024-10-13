package main

import (
  "context"
  "log"
  "time"
  "os"

  amqp "github.com/rabbitmq/amqp091-go"
)
type RabbitMQClient struct {
    conn *amqp.Connection
}

func failOnError(err error, msg string) {
	if err != nil {
	  log.Panicf("%s: %s", msg, err)
	}
}

func (client *RabbitMQClient) init() {
  if os.Getenv("RABBITMQ_SERVER") == "" {
		log.Fatal("RABBITMQ_SERVER environment variable is not set")
		return
	}
	
	endpoint := os.Getenv("RABBITMQ_SERVER")
	var err error
	client.conn, err = amqp.Dial("amqp://guest:guest@"+endpoint)
	failOnError(err, "Failed to connect to RabbitMQ")
}

func (client *RabbitMQClient) publish(topic string, message string) {
	var err error
	ch, err := client.conn.Channel()
    failOnError(err, "Failed to open a channel")
    defer ch.Close()

    err = ch.ExchangeDeclare(
                "sensor", // name
                "topic",      // type
                true,         // durable
                false,        // auto-deleted
                false,        // internal
                false,        // no-wait
                nil,          // arguments
	)
    failOnError(err, "Failed to declare an exchange")

    ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
    defer cancel()
    err = ch.PublishWithContext(ctx,
                "sensor",  // exchange
                topic, // routing key
                false, // mandatory
                false, // immediate
                amqp.Publishing{
                        ContentType: "text/plain",
                        Body:        []byte(message),
                })
    failOnError(err, "Failed to publish a message")
}

func (client *RabbitMQClient) close(){
	defer client.conn.Close()
}

//func main(){
//	client := &RabbitMQClient{}
//	client.init()
//	client.publish("/data/test", 4)
//}