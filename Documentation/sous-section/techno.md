# Choix Technologiques

## 1. **Java Quarkus**
Quarkus est conçu pour les environnements cloud natifs, facilitant la gestion, le déploiement et la scalabilité 
des services. Il se distingue par sa rapidité de démarrage et sa faible utilisation de la mémoire, réduisant les coûts 
en ressources. Ces atouts sont cruciaux pour un déploiement cloud efficace et réactif. Comparé à d'autres langages 
ou frameworks, Quarkus offre un compromis optimal entre la robustesse de l'écosystème Java et la performance, ce qui 
le rend idéal pour des architectures de microservices dans le cloud.

## 2. **Prometheus**
Prometheus est conçu spécifiquement pour gérer des données chronologiques (time-series), ce qui est idéal pour 
des mesures prises à intervalles réguliers, comme les métriques de santé des utilisateurs. Contrairement à une base 
de données classique, Prometheus est optimisé pour stocker, interroger et analyser rapidement des séries temporelles, 
ce qui facilite la détection de tendances, d'anomalies et l'analyse historique des données. Cette capacité est cruciale 
pour évaluer l'état de santé général des patients et générer des alertes.

## 3. **Grafana**
Grafana est un excellent choix pour visualiser les données de santé collectées. Il permet de créer des dashboards 
interactifs et personnalisés à partir des métriques de séries temporelles stockées dans des bases de données comme 
Prometheus. Grafana permet d'afficher des données en temps réel de manière claire et visuellement engageante, ce qui 
aide les utilisateurs à analyser facilement les données. De plus, il peut être intégré directement dans une application 
front-end pour faciliter l'accès aux données.

## 4. **Go**
Go est un excellent choix pour les systèmes IoT en raison de sa performance élevée et de sa faible empreinte mémoire. 
Ces caractéristiques sont essentielles pour des appareils souvent limités en ressources. De plus, Go offre une syntaxe 
simple, facilitant la maintenance du code, et sa gestion automatique de la mémoire assure des performances optimales 
tout en réduisant la complexité du développement.

## 5. **MongoDB**
MongoDB est choisi pour sa capacité à garantir une scalabilité efficace grâce à son modèle de données orienté document. 
Cela permet une gestion indépendante des données par chaque service sans nécessiter des insertions dans d'autres tables, 
ce qui le rend idéal pour des transactions indépendantes. De plus, MongoDB favorise l'eventual consistency plutôt 
qu'une cohérence immédiate, offrant ainsi une meilleure disponibilité des services, particulièrement dans 
un environnement de microservices distribués.

## 6. **RabbitMQ**
RabbitMQ est sélectionné pour sa gestion asynchrone des files de messages, garantissant une transmission fiable 
des données même en cas de pics de trafic. Il est particulièrement adapté aux systèmes nécessitant une livraison 
garantie de messages et un traitement rapide. De plus, RabbitMQ est optimisé pour des environnements à ressources 
limitées, offrant un excellent compromis entre performance et consommation des ressources.

## 7. **Traefik**
Traefik sert de reverse proxy pour diriger le trafic vers le frontend et permettre un accès fluide à certaines 
fonctionnalités. Il simplifie la gestion des points d’entrée pour différents types d’utilisateurs grâce à un routage 
dynamique et la gestion des certificats SSL pour sécuriser les connexions. Traefik est compatible avec des environnements 
comme Docker et Kubernetes, facilitant ainsi le déploiement et la gestion du système dans le cloud, ce qui rend l’architecture évolutive.

## 8. **Keycloak**
Keycloak est utilisé pour la gestion de l'authentification et de l’autorisation basée sur les rôles des utilisateurs 
(proches, médecins, infirmiers). En centralisant la gestion des identités, Keycloak offre une solution complète, sécurisée
et modulable pour contrôler les accès aux différentes parties du système. Il garantit que seuls les utilisateurs autorisés
peuvent accéder aux informations sensibles, ce qui est crucial pour assurer la confidentialité des données de santé.
