## Deploying cloud

### Create Kind cluster
```bash
kind create cluster --config kind-cluster.yaml
```

### Install Manifests 
```bash
kubectl apply -k kind/
```

NB: The command will fail on the first run because the crds are not yet created. Run the command again to apply the manifests.

### Add the following entries to /etc/hosts
```bash
127.0.0.1 keycloak.al.apoorva64.com prometheus.al.apoorva64.com grafana.al.apoorva64.com patient-management.al.apoorva64.com alert-management.al.apoorva64.com
```