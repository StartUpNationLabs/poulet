package main

import(
	"log"
)

type Compacter struct {
    data             map[string][]Sample
    prometheusClient *PrometheusClient
}

func (compacter *Compacter) init(prometheusClient *PrometheusClient) {
    compacter.data = make(map[string][]Sample)
	compacter.prometheusClient = prometheusClient
}

func (compacter *Compacter) addSample(metric string, sample Sample) bool {
	compacter.data[metric] = append(compacter.data[metric], sample)

	if len(compacter.data[metric]) >= 50 {
		isSent := compacter.prometheusClient.Write(metric, compacter.data[metric])
		if isSent {
			compacter.data[metric] = []Sample{}
			log.Print("Sent to prometheus")
		}
		return isSent
	}
	return true
}