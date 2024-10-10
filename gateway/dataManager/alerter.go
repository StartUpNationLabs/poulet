package main

type Alerter struct {
	compacter *Compacter
}

func (alerter *Alerter) init(compacter *Compacter) {
	alerter.compacter = compacter
}

func (alerter *Alerter) sendSample(metric string, sample Sample) {
	alerter.compacter.addSample(metric, sample)
}