package main

import (
  "context"
  "log"
  "time"
  "strings"
  "strconv"

  amqp "github.com/rabbitmq/amqp091-go"
)
type RabbitMQClient struct {
    conn *amqp.Connection
    alerter *Alerter
}

func failOnError(err error, msg string) {
	if err != nil {
	  log.Panicf("%s: %s", msg, err)
	}
}

func (rabbitMQClient *RabbitMQClient) init(alerter *Alerter) {
	var err error
	rabbitMQClient.conn, err = amqp.Dial("amqp://guest:guest@localhost:5672/")
	failOnError(err, "Failed to connect to RabbitMQ")
    rabbitMQClient.alerter = alerter
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

    log.Printf("Sent %s in %s", message, topic)
}


func (rabbitMQClient *RabbitMQClient) consume(topic string){
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

            metric := strings.Split(d.RoutingKey, ".")[2]
            value, _ := strconv.ParseFloat(string(d.Body), 64)

            sample := Sample{Time: time.Now(), Value: value}

            rabbitMQClient.alerter.sendSample(metric, sample)
        }
    }()

    log.Printf(" [*] Waiting for logs. To exit press CTRL+C")
    <-forever
}

func (rabbitMQClient *RabbitMQClient) close(){
	defer rabbitMQClient.conn.Close()
}

//func main(){
//	rabbitMQClient := &RabbitMQClient{}
//	rabbitMQClient.init()
//	rabbitMQClient.publish("/data/test", 4)
//}