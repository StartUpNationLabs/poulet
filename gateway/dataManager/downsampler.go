package main

import(
	"log"
)

type DownSampler struct {
    data             map[string][]Sample
    compacter *Compacter
}

var downSamplingConf = map[string]int{
	"acceleration": 50,
	"heartrate": 4,
	"temperature": 1,
	"glucose": 1,
}

func (downSampler *DownSampler) init(compacter *Compacter) {
    downSampler.data = make(map[string][]Sample)
	downSampler.compacter = compacter
}

func (downSampler *DownSampler) addSample(metric string, sample Sample) bool {
	downSampler.data[metric] = append(downSampler.data[metric], sample)

	if len(downSampler.data[metric]) >= downSamplingConf[metric] {

		sum := float64(0)
		for _, num := range downSampler.data[metric] {
			sum += num.Value
		}
		metricMean := float64(sum) / float64(len(downSampler.data[metric])) 
		sample.Value = metricMean

		isSent := downSampler.compacter.addSample(metric, sample)
		if isSent {
			downSampler.data[metric] = []Sample{}
			log.Print("Sent to prometheus")
		}
		return isSent
	}
	return true
}