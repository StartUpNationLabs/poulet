package AlerteDetector

import (
	"fmt"
)

// MockSendSMS simulates sending an SMS when a bad health indicator is detected
func SendSMS(phoneNumber, message string) {
	fmt.Printf("Sending SMS to %s: %s\n", phoneNumber, message)
}

// CheckHealthParameter checks if the provided parameter value is abnormal
// If the value is abnormal, it triggers an SMS notification
func CheckHealthParameter(param string, value float64, phoneNumbers []string) bool {
	var isAbnormal bool
	var message string

	switch param {
	case "temperature":
		isAbnormal = value < 36.0 || value > 37.5 // Normal body temperature range
		message = fmt.Sprintf("Alert! Abnormal %s: %.2f°C", param, value)
	case "acceleration":
		isAbnormal = value > 9.8 // Abnormal if acceleration exceeds 9.8 m/s²
		message = fmt.Sprintf("Alert! Abnormal %s: %.2f m/s²", param, value)
	case "glucose":
		isAbnormal = value < 70 || value > 140 // Normal fasting glucose level
		message = fmt.Sprintf("Alert! Abnormal %s: %.2f mg/dL", param, value)
	case "heart beat":
		isAbnormal = value < 60 || value > 100 // Normal resting heart rate
		message = fmt.Sprintf("Alert! Abnormal %s: %.2f BPM", param, value)
	default:
		fmt.Println("Unknown parameter:", param)
		return false
	}

	if isAbnormal {
        for _, phoneNumber := range phoneNumbers {
            MockSendSMS(phoneNumber, message)
        }
    }

	return isAbnormal
}
