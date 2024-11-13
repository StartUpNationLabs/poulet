# Choix Technologiques

## 1. **Java Quarkus**
On a utilisé Java quarkus pour implémenter nos micro-service pour le management de patient, d'alertes et de metrics.
Quarkus est conçu pour les environnements cloud natifs, facilitant la gestion, le déploiement et la scalabilité 
des services. Il se distingue par sa rapidité de démarrage et sa faible utilisation de la mémoire, réduisant les coûts 
en ressources. Ces atouts sont cruciaux pour un déploiement cloud efficace et réactif. Comparé à d'autres langages 
ou frameworks, Quarkus offre un compromis optimal entre la robustesse de l'écosystème Java et la performance, ce qui 
le rend idéal pour des architectures de microservices dans le cloud. Il permet aussi de diminuer le nombre de ressources nécessaires pour maintenir un grand nombre de services, réduisant ainsi l'empreinte carbone de l’infrastructure.

## 2. **Prometheus**
Prometheus est conçu spécifiquement pour gérer des données chronologiques (time-series), ce qui est idéal pour 
des mesures prises à intervalles réguliers, comme les métriques de santé des utilisateurs (accélération, glucose, temperature et la fréquence cardiaque). Contrairement à une base 
de données classique, Prometheus est optimisé pour stocker, interroger et analyser rapidement des séries temporelles, 
ce qui facilite la détection de tendances, d'anomalies et l'analyse historique des données. Cette capacité est cruciale 
pour évaluer l'état de santé général des patients et générer des alertes.  Dans notre projet, AlertManager prend en charge différents types d’alertes pour le suivi de la glycémie, du rythme cardiaque et de la température corporelle du patient. Ces alertes peuvent être basées sur la détection des variations rapides de ces valeurs (alertes dérivées) ou sur les prédictions de niveaux futurs (alertes prédictives). 
L’alert manager centralise la gestion des alertes, ce qui réduit le risque de notifications redondantes et améliore la réactivité du système face aux situations critiques. Il permet de configurer des niveaux de gravité, de regrouper des alertes similaires et de les envoyer de manière ciblée.
Une fois générées, les alertes sont envoyées au service de gestion des alertes (alertManagement), qui se charge de les stocker dans la base de données pour assurer une traçabilité et faciliter l’analyse des tendances à long terme.


## 3. **Grafana**
Grafana est un excellent choix pour visualiser les données de santé collectées. Il permet de créer des dashboards 
interactifs et personnalisés à partir des métriques de séries temporelles stockées dans des bases de données comme 
Prometheus. Grafana permet d'afficher des données en temps réel de manière claire et visuellement engageante, ce qui 
aide les utilisateurs à analyser facilement les données. De plus, il peut être intégré directement dans une application 
front-end pour faciliter l'accès aux données.

## 4. **Go**
Du côté gateway on a utilisé go pour implémenter la pipeline contenant la détection des alertes critiques, le downsampling et l'envoie des donnée à prometheus.
Go est un excellent choix pour les systèmes IoT en raison de sa performance élevée et de sa faible empreinte mémoire. 
Ces caractéristiques sont essentielles pour des appareils souvent limités en ressources. De plus, Go offre une syntaxe 
simple, facilitant la maintenance du code, et sa gestion automatique de la mémoire assure des performances optimales 
tout en réduisant la complexité du développement.

## 5. **MongoDB** 
MongoDB est choisi pour sa capacité à garantir une scalabilité efficace grâce à son modèle de données orienté document. 
Cela permet une gestion indépendante des données par chaque service sans nécessiter des insertions dans d'autres tables, 
ce qui le rend idéal pour des transactions indépendantes. De plus, MongoDB favorise l'eventual consistency plutôt 
qu'une cohérence immédiate, offrant ainsi une meilleure disponibilité des services, particulièrement dans 
notre environnement de microservices distribués.

## 6. **RabbitMQ**
RabbitMQ est sélectionné pour délivrer les messages directement aux services concernés. En implémentant une politique de multi-ack, il nous permet de recevoir un ensemble de messages et de valider leur réception en lot, en attendant leur envoi vers le serveur cloud. RabbitMQ est open-source et permet aussi de conserver les messages sur disque en cas de surcharge. Cela libère alors la mémoire RAM et permet de stocker un grand nombre de messages en cas de panne.

## 7. **Traefik**
Traefik sert d'API Gateway pour diriger le trafic vers les services. Notre usage permet d'utiliser la version open-source. Traefik est compatible avec des environnements comme Docker ou Kubernetes. Traefik est très populaire et répandu sur le marché.

## 8. **Keycloak**
Keycloak est utilisé pour la gestion de l'authentification et de l’autorisation basée sur les rôles des utilisateurs (proches, médecins, infirmiers). En centralisant la gestion des identités, Keycloak offre une solution complète, sécurisée et modulable pour contrôler les accès aux différentes parties du système. Il a été choisi pour son caractère open-source et populaire. De plus, c'est une technologie maîtrisée par les membres de l'équipe, ce qui facilite sa mise en place.
