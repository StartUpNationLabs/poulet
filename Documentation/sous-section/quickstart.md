# Quickstart

## Deploiement avec Docker Compose

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

Access the frontend at [http://localhost:8080](http://localhost:8080) - Register a new patient - Connect to the mongoDB
and retrieve his gateway ID

Replace the gateway id in the corresponding environment variables in
the [gateway docker compose](../../gateway/docker-compose.yml)
**Launch the gatway** by deploying the following docker compose :

- [Gateway](../../gateway/docker-compose.yml)

After this two steps you can execute the simulate.py script that will emulate data to the gateway adapter as if you were
using sensors. The gateway will then compute the measures and send it to the prometheus server.

You can now explore you're metrics directly on the prometheus server :
[http://localhost:9090](http://localhost:9090)

---
## Déploiement avec Kubernetes en local

### Créer un cluster Kind

```bash
kind create cluster --config kind-cluster.yml
```

### Installer les Manifestes

```bash
kubectl apply -k kind/
```

NB : La commande échouera lors de la première exécution, car les CRDs ne sont pas encore créés. Exécutez à nouveau la commande pour appliquer les manifestes.

### Ajouter les entrées suivantes dans /etc/hosts

```bash
127.0.0.1 keycloak.al.apoorva64.com prometheus.al.apoorva64.com grafana.al.apoorva64.com patient-management.al.apoorva64.com alert-management.al.apoorva64.com 
adapter.al.apoorva64.com doctor.al.apoorva64.com
```

### Installer le Chart Helm

```bash
helm install poulet ./charts/poulet --set keycloak.auth.adminPassword=admin
```

### Appliquer Terraform
Appliquez la configuration Terraform pour configurer Keycloak.

```bash
cd terraform
terraform init
terraform apply
```

Suivez les instructions pour configurer le realm et le client Keycloak.

### Correctif rapide pour Keycloak
Dans la console d’administration de Keycloak, accédez au poulet_realm, allez dans les "client scopes" et recherchez "roles". Cochez la case "Inclure dans le scope du token". Ensuite, allez dans l'onglet Mappers, cliquez sur le mapper "client roles", et cochez toutes les cases.

### Accéder au frontend à l’adresse [http://doctor.al.apoorva64.com](http://doctor.al.apoorva64.com)
### Acceder au prometheus à l’adresse [http://prometheus.al.apoorva64.com](http://prometheus.al.apoorva64.com)