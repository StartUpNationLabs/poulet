package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
	"time"
	//"log"
	//"os"
	//"github.com/joho/godotenv"
)

type Alert struct {
	Parameter string    `json:"type"`
	Value     float64   `json:"value"`
	Message   string    `json:"message"`
	GatewayID string    `json:"GatewayID"`
	Time      time.Time `json:"time"`
}

type Alerter struct {
	downSampler *DownSampler
}

func (alerter *Alerter) init(downSampler *DownSampler) {
	alerter.downSampler = downSampler
}

func (alerter *Alerter) sendSample(metric string, sample Sample) {
	CheckHealthParameter(metric, sample)
	alerter.downSampler.addSample(metric, sample)
}

func SendSMS(phoneNumber, message string) {
	fmt.Printf("Sending SMS to %s: %s\n", phoneNumber, message)
}

func CheckHealthParameter(param string, sample Sample) bool {
	var isAbnormal bool
	var message string
	fmt.Println(" check health ")
	fmt.Println(" param ", param)
	fmt.Println(" sample  ", sample)
	/*enverr := godotenv.Load("../.env")
	if enverr != nil {
		log.Fatal("Error loading .env file")
	}

	var gateway = os.Getenv("GATEWAY_ID")*/
	var gateway = "670fd29e2be2690715bd0e55"
	fmt.Println(" id ", gateway)
	var phoneNumbers, err = getPhoneNumbers(gateway)

	for _, phoneNumber := range phoneNumbers {
		fmt.Println("Phone number: ", phoneNumber)
	}
	

	if err == nil {
		fmt.Println("Error getting phone numbers:", err)
		return false
	}
	
	switch param {
		case "temperature":
			isAbnormal = sample.Value < 36.0 || sample.Value > 37.5
			message = fmt.Sprintf("Alert! Abnormal %s: %.2f°C", param, sample.Value)
		case "acceleration":
			isAbnormal = sample.Value > 100
			message = fmt.Sprintf("Alert! Abnormal %s: %.2f m/s²", param, sample.Value)
		case "glucose":
			isAbnormal = sample.Value < 70 || sample.Value > 140
			message = fmt.Sprintf("Alert! Abnormal %s: %.2f mg/dL", param, sample.Value)
		case "heartrate":
			isAbnormal = sample.Value < 60 || sample.Value > 100
			message = fmt.Sprintf("Alert! Abnormal %s: %.2f BPM", param, sample.Value)
		default:
			fmt.Println("Unknown parameter:", param)
			return false
	}

	if isAbnormal {
		//for _, phoneNumber := range phoneNumbers {
			SendSMS(phoneNumbers, message)
		//}

		alert := Alert{
			Parameter: param,
			Value:     sample.Value,
			Time:      sample.Time,
			Message:   message,
			GatewayID: gateway,
		}

		if err := SendAlerts(alert); err != nil {
			fmt.Println("Error sending alert to server:", err)
		}
	}

	return isAbnormal
}

func SendAlerts(alert Alert) error {
	alertData, err := json.Marshal(alert)
	if err != nil {
		return fmt.Errorf("error marshaling alert data: %v", err)
	}

	resp, err := http.Post("http://localhost:8082/alert", "application/json", bytes.NewBuffer(alertData))
	if err != nil {
		return fmt.Errorf("error sending alert to server: %v", err)
	}
	defer resp.Body.Close()

	return nil
}

func getPhoneNumbers(gatewayID string) (string, error) {
	fmt.Println("Phone numbers")
	resp, err := http.Get("http://localhost:8083/patient/gateway/" + gatewayID)
	if err != nil {
		return "", fmt.Errorf("error getting patient data: %v", err)
	}
	defer resp.Body.Close()

	var patient struct {
		//EmergencyContactPhoneNumber []string `json:"emergencyContactPhoneNumber"`
		EmergencyContactPhoneNumber string `json:"emergencyContactPhoneNumber"`
	}
	if err := json.NewDecoder(resp.Body).Decode(&patient); err != nil {
		return "", fmt.Errorf("error decoding patient data: %v", err)
	}
	fmt.Println("patient : ", patient)
	fmt.Println("Phone numbers: ", patient.EmergencyContactPhoneNumber)
	return patient.EmergencyContactPhoneNumber, nil
}
