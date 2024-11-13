# Quickstart

This project is composed of 3 parts :

- Sensors
- Gateway
- WebApp

**Launch the Webapp** by deploying the 2 docker compose :

- [Prometheus stack](../../docker-compose.yaml)
- [Services](../../cloud/backend/docker-compose.yml)

**Launch the webapp portail** directly on the [frontend directory](../../cloud/frontend/)

```
npm install
```

pour installer les dépendances

```
npm run start
```

pour lancer le serveur de développement

Access the frontend at [http://localhost:8080](http://localhost:8080) - Register a new patient - Connect to the mongoDB and retrieve his gateway ID

Replace the gateway id in the corresponding environment variables in the [gateway docker compose](../../gateway/docker-compose.yml)
**Launch the gatway** by deploying the following docker compose :

- [Gateway](../../gateway/docker-compose.yml)

After this two steps you can execute the simulate.py script that will emulate data to the gateway adapter as if you were using sensors. The gateway will then compute the measures and send it to the prometheus server.

You can now explore you're metrics directly on the prometheus server :
[http://localhost:9090](http://localhost:9090)

---

## Deploying with kubernetes locally

### Create Kind cluster

```bash
kind create cluster --config kind-cluster.yml
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
