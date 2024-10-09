package main

import (
  "context"
  "log"
  "time"
  "strings"

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
	var err error
	client.conn, err = amqp.Dial("amqp://guest:guest@localhost:5672/")
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

    log.Printf("Sent %s in %s", message, topic)
}


func (client *RabbitMQClient) consume(topic string){
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

    q, err := ch.QueueDeclare(
        "",    // name
        false, // durable
        false, // delete when unused
        true,  // exclusive
        false, // no-wait
        nil,   // arguments
    )
    failOnError(err, "Failed to declare a queue")

    err = ch.QueueBind(
        q.Name,       // queue name
        topic,            // routing key
        "sensor", // exchange
        false,
        nil)
    failOnError(err, "Failed to bind a queue")
    

    msgs, err := ch.Consume(
        q.Name, // queue
        "sensor",     // consumer
        true,   // auto ack
        false,  // exclusive
        false,  // no local
        false,  // no wait
        nil,    // args
    )
    failOnError(err, "Failed to register a consumer")

    var forever chan struct{}

    go func() {
        for d := range msgs {
            log.Printf(" [x] %s in %s", d.Body, d.RoutingKey)
            var metricName string = strings.Split(d.RoutingKey, ".")[2]    

            //isValid = analyser.isValid(metricName, d.Body)

			newKey := "sensor.clean." + metricName
			client.publish(newKey, string(d.Body))
        }
    }()

    log.Printf(" [*] Waiting for logs. To exit press CTRL+C")
    <-forever
}

func (client *RabbitMQClient) close(){
	defer client.conn.Close()
}

//func main(){
//	client := &RabbitMQClient{}
//	client.init()
//	client.publish("/data/test", 4)
//}