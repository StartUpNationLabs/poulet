package main

import (
	"fmt"
	"log"
	"AlerteDetector/test"
	"github.com/joho/godotenv"
	"os"
)

func main() {

	phoneNumbers := []string{"123456789", "987654321"}
	patientID := "P123456"

	err := godotenv.Load("../.env")
	if err != nil {
		log.Fatal("Error loading .env file")
	}

	test  := os.Getenv("GATEWAY_ID")
	fmt.Println(" id ",test)

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
