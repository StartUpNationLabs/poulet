package AlerteDetector

import (
	"fmt"
)

func SendSMS(phoneNumber, message string) {
	fmt.Printf("Sending SMS to %s: %s\n", phoneNumber, message)
}

func CheckHealthParameter(param string, value float64, phoneNumbers []string) bool {
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
    }

	return isAbnormal
}
