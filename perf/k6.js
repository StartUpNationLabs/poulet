import {check, sleep} from 'k6';
import remote from 'k6/x/remotewrite';

export let options = {
    vus: 500,
    duration: '120s',
};

const client = new remote.Client({
    url: "http://localhost:9090/api/v1/write"
});

export default function () {
    let res = client.store([{
        "labels": [
            {"name": "__name__", "value": `heartrate${__VU}`},
            {"name": "service", "value": "bar"}
        ],
        "samples": [
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
            {"value": Math.random() * 100,},
        ]
    },
        {
            "labels": [
                {"name": "__name__", "value": `glucose${__VU}`},
                {"name": "service", "value": "bar"}
            ],
            "samples": [
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
            ]
        }
        ,
        {
            "labels": [
                {"name": "__name__", "value": `thermometre${__VU}`},
                {"name": "service", "value": "bar"}
            ],
            "samples": [
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
            ]
        }
        ,
        {
            "labels": [
                {"name": "__name__", "value": `accelerometre${__VU}`},
                {"name": "service", "value": "bar"}
            ],
            "samples": [
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
                {"value": Math.random() * 100,},
            ]
        }
    ]);
    sleep(1)
}