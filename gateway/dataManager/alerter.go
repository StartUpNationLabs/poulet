package main

type Alerter struct {
	downSampler *DownSampler
}

func (alerter *Alerter) init(downSampler *DownSampler) {
	alerter.downSampler = downSampler
}

func (alerter *Alerter) sendSample(metric string, sample Sample) {
	alerter.downSampler.addSample(metric, sample)
}