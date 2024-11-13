# Structure du Code

La structure du code est organisée en deux principaux repositories : [**gateway**](../../gateway) et [**cloud**](../../cloud).

## Repository Gateway

Ce repository contient les éléments suivants :

- **Code d'adaptateur** : Responsable de l'intégration et de la gestion des communications entre les appareils de santé connectés et le système. Ce module veille à ce que les données soient correctement formatées et transmises.

- **Code de Data Manager** : Inclut plusieurs fonctionnalités essentielles :
    - **Détection d'alerte** 
    - **Down-sampling**
    - **Push Gateway**

## Repository Cloud

Ce repository est structuré en plusieurs dossiers et services :

- **Frontend** : Contient le code de l'interface utilisateur.

- **Backend** : Composé de trois services principaux :
    - **Gestion des alertes (alert-management)** 
    - **Service d'analyse haut niveau (analyse-haut-niveau-management)**
    - **Service de gestion des dossiers de patient (patient-management)**

- **Configuration**

