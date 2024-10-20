package main

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

	if len(compacter.data[metric]) >= 5 {
		isSent := compacter.prometheusClient.Write(metric, compacter.data[metric])
		if isSent {
			compacter.data[metric] = []Sample{}
		}
		return isSent
	}
	return true
}