package main

import (
	"bytes"
	"context"
	"github.com/gogo/protobuf/proto"
	"github.com/golang/snappy"
	"github.com/prometheus/prometheus/prompb"
	"io"
	"net/http"
	"time"
	"os"
	"log"
)

type TimeSeries struct {
	Labels []Label
	Sample []Sample
}

type Label struct {
	Name  string
	Value string
}

type Sample struct {
	Time  time.Time
	Value float64
}

// ClientOption is used to set custom client options.
type ClientOption func(opts *clientOptions)

// HttpClient option allows configuring custom HTTP client.
func HttpClient(client *http.Client) ClientOption {
	return func(opts *clientOptions) {
		opts.httpClient = client
	}
}

type clientOptions struct {
	httpClient *http.Client
}

func (prometheusClient *PrometheusClient) init(options ...ClientOption) {
	if os.Getenv("PROMETHEUS_SERVER") == "" {
		log.Fatal("PROMETHEUS_SERVER environment variable is not set")
		return
	}
	

	endpoint := os.Getenv("PROMETHEUS_SERVER")
	prometheusClient.opts = &clientOptions{
		httpClient: &http.Client{Timeout: 30 * time.Second},
	}
	prometheusClient.endpoint = endpoint 
}

// Client is Prometheus Remote Write client.
type PrometheusClient struct {
	endpoint string
	opts     *clientOptions
}

type WriteOption func(opts *writeOptions)

// WriteHeaders allows passing custom HTTP headers. Once common use case is to pass `X-Scope-OrgID` header for Cortex tenant.
func WriteHeaders(headers map[string]string) WriteOption {
	return func(opt *writeOptions) {
		opt.headers = headers
	}
}

type writeOptions struct {
	headers map[string]string
}

type WriteRequest struct {
	TimeSeries []TimeSeries
}

type WriteResponse struct {
}

func (prometheusClient *PrometheusClient) Write(metric string, batch []Sample) (bool) {
	return prometheusClient.originWrite(&WriteRequest{
        TimeSeries: []TimeSeries{
            {
                Labels: []Label{
                    {
                        Name:  "__name__",
                        Value: metric,
                    },
                    {
                        Name:  "patient_id",
                        Value: "1",
                    },
                },
                Sample: batch,
            },
        },
    })
}

func (prometheusClient *PrometheusClient) originWrite(req *WriteRequest, options ...WriteOption) (bool) {
	opts := writeOptions{}
	for _, opt := range options {
		opt(&opts)
	}
	// Marshal proto and compress.
	pbBytes, err := proto.Marshal(&prompb.WriteRequest{
		Timeseries: toProtoTimeSeries(req.TimeSeries),
	})
	if err != nil {
		log.Println("Error:","promwrite: marshaling remote write request proto: %w", err)
		return false
	}

	compressedBytes := snappy.Encode(nil, pbBytes)
	ctx := context.Background()
	// Prepare http request.
	httpReq, err := http.NewRequestWithContext(ctx, http.MethodPost, prometheusClient.endpoint, bytes.NewBuffer(compressedBytes))
	if err != nil {
		log.Println("Error:","promwrite error : %w", err)
		return false
	}
	httpReq.Header.Add("X-Prometheus-Remote-Write-Version", "0.1.0")
	httpReq.Header.Add("Content-Encoding", "snappy")
	httpReq.Header.Set("Content-Type", "application/x-protobuf")
	for k, v := range opts.headers {
		httpReq.Header.Add(k, v)
	}

	// Send http request.
	httpResp, err := prometheusClient.opts.httpClient.Do(httpReq)
	if err != nil {
		log.Println("Error:","promwrite error : %w", err)
		return false
	}
	defer httpResp.Body.Close()

	if st := httpResp.StatusCode; st/100 != 2 {
		msg, _ := io.ReadAll(httpResp.Body)
		log.Println("Error:","promwrite error : %w", msg)
		return false
	}
	return true
}

func toProtoTimeSeries(timeSeries []TimeSeries) []prompb.TimeSeries {
	res := make([]prompb.TimeSeries, len(timeSeries))
	for i, ts := range timeSeries {
		labels := make([]prompb.Label, len(ts.Labels))
		for j, lb := range ts.Labels {
			labels[j] = prompb.Label{
				Name:  lb.Name,
				Value: lb.Value,
			}
		}
		pbTs := prompb.TimeSeries{
			Labels:  labels,
			Samples: toProtoSamples(ts.Sample),
		}
		res[i] = pbTs
	}
	return res
}

func toProtoSamples(sample []Sample) []prompb.Sample {
	res := make([]prompb.Sample, len(sample))
	for i, s := range sample {
		res[i] = prompb.Sample{
			Timestamp: s.Time.UnixNano() / int64(time.Millisecond),
			Value:     s.Value,
		}
	}
	return res
}

// WriteError returned if HTTP call is finished with response status code, but it was not successful.
type WriteError struct {
	err  error
	code int
}

func (e *WriteError) Error() string {
	return e.err.Error()
}

func (e *WriteError) StatusCode() int {
	return e.code
}
