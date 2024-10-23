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

func (rabbitMQClient *RabbitMQClient) init() {
  if os.Getenv("RABBITMQ_SERVER") == "" {
		log.Fatal("RABBITMQ_SERVER environment variable is not set")
		return
	}
	endpoint := os.Getenv("RABBITMQ_SERVER")
  if os.Getenv("RABBITMQ_USERNAME") == "" {
		log.Fatal("RABBITMQ_USERNAME environment variable is not set")
		return
	}
	username := os.Getenv("RABBITMQ_USERNAME")
  if os.Getenv("RABBITMQ_PASSWORD") == "" {
		log.Fatal("RABBITMQ_PASSWORD environment variable is not set")
		return
	}
	password := os.Getenv("RABBITMQ_PASSWORD")
	var err error
	rabbitMQClient.conn, err = amqp.Dial("amqp://"+username+":"+password+"@"+endpoint)
	failOnError(err, "Failed to connect to RabbitMQ")
}

func (rabbitMQClient *RabbitMQClient) publish(topic string, message string) {
	var err error
	ch, err := rabbitMQClient.conn.Channel()
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

func (rabbitMQClient *RabbitMQClient) close(){
	defer rabbitMQClient.conn.Close()
}

//func main(){
//	rabbitMQClient := &RabbitMQClient{}
//	rabbitMQClient.init()
//	rabbitMQClient.publish("/data/test", 4)
//}