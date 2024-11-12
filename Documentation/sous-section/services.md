# Description des Services du Système

Dans cette section, nous détaillons les principaux services du système, chacun jouant un rôle spécifique dans la gestion,
le traitement et la transmission des données de santé des patients. Ces services assurent une surveillance 
en temps réel ainsi que la gestion des alertes.

## Côté Client

1. **[Détection Alerte Service](../../gateway/dataManager/alerter.go)**  
   Ce service est essentiel pour identifier les situations critiques en détectant des valeurs anormales dans les données
    de santé des patients. Lorsqu'une valeur critique est repérée, une alerte est envoyée par SMS au numéro de téléphone
    d'un proche stocké localement. Le service utilise une clé 4G pour garantir la transmission des alertes, assurant ainsi 
    une notification immédiate aux proches en cas d'urgence.

   Ce service se situe avant la phase de downsampling pour garantir que les utilisateurs, tels que les proches ou les 
    soignants, sont informés de tout problème de santé potentiel dès qu'il survient. En détectant et en envoyant 
    une alerte immédiatement, nous maximisons la réactivité et la sécurité. En plus de l'envoi automatique du SMS, 
    il est recommandé de procéder à une vérification supplémentaire, comme un appel au patient ou à son proche 
    pour confirmer la situation.

2. **[Down-sampling Service](../../gateway/dataManager/downsampler.go)**  
   Ce service optimise la transmission des informations vers le cloud en réduisant la quantité de données envoyées. 
   En filtrant les informations pour ne conserver que les données pertinentes, le service minimise la consommation des ressources réseau et diminue les risques de fausses alertes. Ainsi, seules les données essentielles sont transmises pour un suivi de qualité.

3. **[PushGateway Service](../../gateway/dataManager/compacter.go)**  
   Ce service transmet les données vers le cloud après avoir atteint un seuil de collecte prédéfini. Le PushGateway 
   permet une transmission contrôlée et périodique des données, facilitant ainsi la centralisation et l'archivage des 
   informations de santé pour une accessibilité future.

## Côté Cloud

1. **[GestionAlertService](../../cloud/backend/alert-management/)**  
   Ce service gère l’ensemble des alertes, les stocke dans une base de données dédiée, et permet diverses opérations :
    - Récupération d’une alerte spécifique
    - Récupération des alertes liées à un gateway spécifique
    - Consultation de toutes les alertes stockées
    - Filtrage des alertes par gravité et par état (traitées ou non traitées)
    - Création de nouvelles alertes
    - Mise à jour ou suppression des alertes existantes
    - Notification automatique des médecins pour les alertes non traitées

   Ce service assure une gestion et un traitement efficaces des alertes pour un suivi rigoureux des incidents de santé.

2. **[AlertManager](../../cloud/config/alertmanager/)**  
   Ce composant gère les alertes générées par Prometheus pour le suivi de la glycémie, du rythme cardiaque et de la 
   température corporelle du patient. Les alertes peuvent être basées sur la détection de variations rapides 
   (alertes dérivées) ou sur des prédictions de niveaux futurs (alertes prédictives). 

   AlertManager centralise la gestion des alertes pour réduire les notifications redondantes et améliorer la réactivité 
   face aux situations critiques. Il permet de configurer des niveaux de gravité, de regrouper des alertes similaires et 
   de les envoyer de manière ciblée. 

    Une fois générées Les alertes sont ensuite envoyées au service de gestion des alertes pour être stockées 
   dans la base de données, assurant une traçabilité et facilitant l’analyse des tendances à long terme.

3. **[Service Analyse Haut Niveau](../../cloud/backend/analyse-haut-niveau-management/)**  
   Ce service réalise un traitement avancé des données stockées afin de détecter des comportements inhabituels. 
   Il transforme les données brutes en valeurs plus lisibles et compréhensibles pour les médecins, infirmiers et proches, 
   facilitant ainsi leur interprétation et la prise de décisions médicales.

4. **[Service Gestion Dossiers Patients](../../cloud/backend/patient-management/)**  
   Ce service assure la gestion des dossiers patients pour une organisation précise des informations de chaque patient. 
   Les principales fonctionnalités incluent :
       - Récupération d'un patient par ID, gatewayID, ou nom et prénom
       - Création de nouveaux dossiers patients
       - Suppression des dossiers patients

5. **[Service Gestion Consultation Patient](../../cloud/backend/patient-management/)**  
   Ce service facilite la gestion des consultations des médecins et des infirmiers, permettant de conserver un historique
   détaillé des interactions médicales. Ses principales fonctionnalités incluent :
       - Récupération d'une consultation par ID, par patientID, par DoctorID, ou par nurseID
       - Création d'une nouvelle consultation
       - Suppression d'une consultation
