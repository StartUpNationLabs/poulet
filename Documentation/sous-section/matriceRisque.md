# Matrice des Risques

Dans le cadre de notre projet, voici une analyse des risques potentiels. Cette matrice identifie les risques majeurs, leur probabilité et leur impact, ainsi que les mesures de mitigation envisagées pour réduire leur occurrence ou leurs effets.

| **Risque** | **Description** | **Probabilité** | **Impact** | **Stratégie de Mitigation** |
|------------|-----------------|-----------------|------------|-----------------------------|
| Envoi de fausses alertes par SMS| L'algorithme de détection côté client peut déclencher des alertes en fonction de valeurs erronées, générant ainsi de fausses alertes. | Élevée| Moyen| Difficulté de contrôle côté client en raison de ressources limitées. Les utilisateurs peuvent vérifier manuellement l'alerte en appelant la personne âgée pour s'assurer de sa situation.|
| Envoi des alertes SMS à la mauvaise personne | Le numéro d’urgence est récupéré via un appel au service patient. En cas d’indisponibilité de ce service n'aurait pas le possiblite de recuperere le numéro| Moyenne| Élevé| Conserver une copie locale du numéro de téléphone d’urgence avec l'ID du gateway pour un envoi de secours en cas de panne du service patient.|
| Indisponibilité d’un service| Certains services peuvent devenir momentanément indisponibles ou surchargés, affectant le système global.| Moyenne| Élevé| Héberger plusieurs instances de chaque service avec un load balancer pour répartir la charge, assurant une haute disponibilité et limitant les interruptions en cas de surcharge.|
| Problème de connexion client-cloud| Si Prometheus devient indisponible pour surcharge ou autre problème, les données peuvent ne pas être enregistrées correctement.| Moyenne| Élevé| Utiliser un broker de messages pour stocker temporairement les données en file d’attente, permettant leur transmission une fois la connexion rétablie.                                          |
| Indisponibilité de la base de données| Si la base de données MongoDB est indisponible en raison d’une surcharge ou d'un problème de serveur, cela peut entraîner des interruptions. | Moyenne | Élevé | Déployer plusieurs instances de la base de données avec une synchronisation continue pour garantir la disponibilité et répartir les requêtes. |
| Coupure d’électricité| Une coupure de courant au niveau du gateway pourrait interrompre le fonctionnement du système de détection d’alerte.| Faible| Élevé| Installer une batterie de secours dans le gateway pour garantir une alimentation continue en cas de coupure électrique.                                                                        |
| Risques de sécurité| Des vulnérabilités peuvent affecter les communications entre microservices, exposant le système aux attaques.| Moyenne| Élevé| Assurer une communication sécurisée en utilisant HTTPS entre les microservices pour protéger les données contre les interceptions et les attaques.|
| Mauvaise gestion des accès| Un accès non sécurisé pourrait compromettre l'intégrité et la confidentialité des données du système.| Moyenne| Élevé| Mettre en place un système d'authentification robuste et sécurisé pour garantir la sécurité des accès au sein de l’application.|
| Non-conformité réglementaire| Le non-respect des réglementations sur la protection des données personnelles peut exposer l’organisation à des sanctions.| Moyenne| Élevé| Adopter une politique de protection des données en conformité avec le RGPD ou autres réglementations locales. Effectuer des audits de conformité réguliers et former le personnel.|