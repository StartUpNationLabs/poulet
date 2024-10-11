package main

import (
	"fmt"
	"AlerteDetector/test"
)

func main() {

	phoneNumbers := []string{"123456789", "987654321"}
	patientID := "P123456"

	// Exemple de paramètre de santé à vérifier
	parameter := "temperature"
	value := 38.2


	isAbnormal := AlerteDetector.CheckHealthParameter(parameter, value, phoneNumbers, patientID)
	
	if isAbnormal {
		fmt.Println("Alerte envoyée avec succès.")
	} else {
		fmt.Println("Les paramètres de santé sont normaux.")
	}
}
