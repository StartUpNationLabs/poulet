package AlerteDetector

import (
	"bytes"
    "encoding/json"
	"fmt"
	"net/http"
)

func SendSMS(phoneNumber, message string) {
	fmt.Printf("Sending SMS to %s: %s\n", phoneNumber, message)
}

func CheckHealthParameter(param string, value float64, phoneNumbers []string,patientID string ) bool {
	var isAbnormal bool
	var message string

	switch param {
	case "temperature":
		isAbnormal = value < 36.0 || value > 37.5 
		message = fmt.Sprintf("Alert! Abnormal %s: %.2f°C", param, value)
	case "acceleration":
		isAbnormal = value > 100
		message = fmt.Sprintf("Alert! Abnormal %s: %.2f m/s²", param, value)
	case "glucose":
		isAbnormal = value < 70 || value > 140
		message = fmt.Sprintf("Alert! Abnormal %s: %.2f mg/dL", param, value)
	case "heart beat":
		isAbnormal = value < 60 || value > 100
		message = fmt.Sprintf("Alert! Abnormal %s: %.2f BPM", param, value)
	default:
		fmt.Println("Unknown parameter:", param)
		return false
	}

	if isAbnormal {
        for _, phoneNumber := range phoneNumbers {
            SendSMS(phoneNumber, message)
        }

		alert := Alert{
            Parameter: param,
            Value:     value,
            Message:   message,
			PatientID: patientID,
        }

        if err := SendAlerts(alert); err != nil {
            fmt.Println("Error sending alert to server:", err)
        }
    }

	
	return isAbnormal
}


type Alert struct {
    Parameter string  `json:"type"`
    Value     float64 `json:"value"`
    Message   string  `json:"message"`
	PatientID string  `json:"patientId"`
}


func SendAlerts(alert Alert) error{
	alertData, err := json.Marshal(alert)
    if err != nil {
        return fmt.Errorf("error marshaling alert data: %v", err)
    }

	resp, err := http.Post("http://localhost:8080/alert", "application/json", bytes.NewBuffer(alertData))
	if err != nil {
        return fmt.Errorf("error sending alert to server: %v", err)
    }
	defer resp.Body.Close()

    if resp.StatusCode != http.StatusOK {
        return fmt.Errorf(" server returned status code %s", resp.StatusCode)
    }
    return nil
}
