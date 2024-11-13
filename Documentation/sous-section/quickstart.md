# Quickstart

## Déploiement avec Docker Compose en local

### Déploiement des services et des api

Le projet a 3 docker compose :

- cloud/backend/docker-compose.yml : contient les services du backend
- gateway/docker-compose.yml : contient le gateway
- docker-compose.yml : contient les services Prometheus, Grafana, Keycloak

Creer le network :

```bash
docker network create poulet_internal
```

Lancer les docker compose :

```bash
docker-compose -f cloud/backend/docker-compose.yml up -d
docker-compose -f gateway/docker-compose.yml up -d
docker-compose up -d
```

### Lancement du frontend

**Launch the webapp portail** directly on the [frontend directory](../../cloud/frontend/)

```
npm install
```

pour installer les dépendances

```
npm run start
```

pour lancer le serveur de développement

Accédez à l'interface utilisateur à [http://localhost:8080](http://localhost:8080) - Enregistrez un nouveau patient -
Connectez-vous à la base de données MongoDB et récupérez son ID de Gateway.

Remplacez l'ID de Gateway dans les variables d'environnement correspondantes dans le fichier docker-compose de la
Gateway :

- [Gateway](../../gateway/docker-compose.yml)

**Lancez la Gateway** en déployant le docker-compose suivant :

- [Gateway](../../gateway/docker-compose.yml)

Après ces deux étapes, vous pouvez exécuter le script `simulate.py` qui émule des données vers l'adaptateur de la
Gateway comme si vous utilisiez des capteurs. La Gateway calculera ensuite les mesures et les enverra au serveur
Prometheus.

Vous pouvez maintenant explorer vos métriques directement sur le serveur Prometheus :
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

NB : La commande échouera lors de la première exécution, car les CRDs ne sont pas encore créés. Exécutez à nouveau la
commande pour appliquer les manifestes.

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

Dans la console d’administration de Keycloak, accédez au poulet_realm, allez dans les "client scopes" et recherchez "
roles". Cochez la case "Inclure dans le scope du token". Ensuite, allez dans l'onglet Mappers, cliquez sur le mapper "
client roles", et cochez toutes les cases.

### Accéder au frontend à l’adresse [http://doctor.al.apoorva64.com](http://doctor.al.apoorva64.com)

### Acceder au prometheus à l’adresse [http://prometheus.al.apoorva64.com](http://prometheus.al.apoorva64.com)

# Lancer les APIs en mode Développement

Les services `alert-management`, `analyse-haut-niveau-management`, et `patient-management` sont des APIs basées sur
Quarkus et se trouvent dans les dossiers suivants :

- `cloud/backend/alert-management`
- `cloud/backend/analyse-haut-niveau-management`
- `cloud/backend/patient-management`

## Prérequis

1. **Assurez-vous d'avoir** installé les dépendances nécessaires pour Quarkus.
2. **Vérifiez que Docker est opérationnel** pour les dépendances des bases de données et autres services en
   arrière-plan. (mongodb, keycloak, prometheus, etc.)

## Étapes pour lancer chaque API en mode Développement

1. **Accédez au répertoire du service souhaité**. Par exemple, pour le service `alert-management` :
   ```bash
   cd cloud/backend/alert-management
   ```

2. **Installer les dépendances** (si ce n'est pas déjà fait) en exécutant :
   ```bash
   ./mvnw install
   ```

3. **Lancer le serveur en mode développement** avec la commande suivante :
   ```bash
   ./mvnw quarkus:dev
   ```

   Cette commande démarre le serveur Quarkus en mode développement, ce qui permet une recharge à chaud des changements.

## Accéder aux APIs

Une fois les serveurs démarrés, les APIs seront accessibles aux URL suivantes :

- **Alert Management** : [http://localhost:8081](http://localhost:8081)
- **Analyse Haut Niveau Management** : [http://localhost:8082](http://localhost:8082)
- **Patient Management** : [http://localhost:8083](http://localhost:8083)

## Remarques

- **Variables d'environnement** : Si ces APIs nécessitent des configurations spécifiques (par exemple, des identifiants
  ou des URLs de base de données), assurez-vous que les variables d'environnement requises sont définies avant de lancer
  le serveur. Vous pouvez les spécifier dans un fichier `.env` ou directement dans le terminal avant l'exécution de la
  commande `./mvnw quarkus:dev`.

- **Ports** : Les ports peuvent être personnalisés en modifiant les configurations Quarkus dans le fichier
  `application.properties` de chaque API.


