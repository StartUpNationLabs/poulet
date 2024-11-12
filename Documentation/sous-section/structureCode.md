# Structure du Code

La structure du code est organisée en deux principaux repositories : [**gateway**]() et [**cloud**]().

## Repository Gateway

Ce repository contient les éléments suivants :

- **Code d'adaptateur** : Responsable de l'intégration et de la gestion des communications entre les appareils de santé connectés et le système. Ce module veille à ce que les données soient correctement formatées et transmises.

- **Code de Data Manager** : Inclut plusieurs fonctionnalités essentielles :
    - **Détection d'alerte** : Surveille les données entrantes pour détecter des anomalies ou des valeurs critiques nécessitant une attention immédiate.
    - **Down-sampling** : Réduit la fréquence de certaines données pour les optimiser pour un stockage et une visualisation à long terme dans le cloud.
    - **Push Gateway** : Envoie les données traitées au repository cloud pour stockage et analyse ultérieure.

## Repository Cloud

Ce repository est structuré en plusieurs dossiers et services :

- **Frontend** : Contient le code de l'interface utilisateur, permettant la visualisation des données, des ressentis des patients et la gestion des dossiers médicaux. C'est ici que s'affichent les graphiques générés par Grafana et les alertes pertinentes.

- **Backend** : Composé de trois services principaux :
    - **Gestion des alertes (alert-management)** : Prend en charge la gestion et la notification des alertes en fonction des données critiques envoyées par la gateway.
    - **Service d'analyse haut niveau (analyse-haut-niveau-management)** : Analyse les données stockées dans Prometheus pour identifier des tendances ou des évolutions pertinentes dans l’état de santé des patients.
    - **Service de gestion des dossiers de patient (patient-management)** : Assure le suivi, la création et la mise à jour des dossiers médicaux, y compris la saisie des ressentis par les patients.

- **Configuration** : Dossier dédié aux fichiers de configuration pour Grafana, Prometheus (lecture des données), et Alert Manager.

