// main.go
package main

import(
    "github.com/newrelic/go-agent/v3/newrelic"
    "log"
    "time"
)

func main() {

    app, err := newrelic.NewApplication(
        newrelic.ConfigAppName("DataManager"),
        newrelic.ConfigLicense("eu01xx66792eeb67035b3ccc1fb4ee54FFFFNRAL"),
        newrelic.ConfigAppLogForwardingEnabled(true),
    )
    if err != nil {
		log.Println("Error:","Newrelic: %w", err)
		return
    }
     

    rabbitMQClient := &RabbitMQClient{}
    rabbitMQClient.init()
    StartServer(rabbitMQClient, app)

    rabbitMQClient.close()
    app.Shutdown(10 * time.Second)
}

