package publisher

import (
	"log"

	"github.com/streadway/amqp"
)

type RabbitMQPublisher struct {
	conn *amqp.Connection
	ch   *amqp.Channel
	name string
}

func NewRabbitMQPublisher(rabbitMQURL, queueName string) (*RabbitMQPublisher, error) {
	conn, err := amqp.Dial(rabbitMQURL)
	if err != nil {
		return nil, err
	}

	ch, err := conn.Channel()
	if err != nil {
		return nil, err
	}

	// Declare a queue
	_, err = ch.QueueDeclare(
		queueName, // name
		true,      // durable
		false,     // delete when unused
		false,     // exclusive
		false,     // no-wait
		nil,       // arguments
	)
	if err != nil {
		return nil, err
	}

	return &RabbitMQPublisher{conn: conn, ch: ch, name: queueName}, nil
}

func (p *RabbitMQPublisher) Publish(message []byte) error {
	err := p.ch.Publish(
		"",            // exchange
		p.name,       // routing key (queue name)
		false,        // mandatory
		false,        // immediate
		amqp.Publishing{
			ContentType: "text/plain",
			Body:        message,
		},
	)
	if err != nil {
		return err
	}
	log.Printf("Message published to RabbitMQ queue: %s", p.name)
	return nil
}

func (p *RabbitMQPublisher) Close() {
	p.ch.Close()
	p.conn.Close()
}
