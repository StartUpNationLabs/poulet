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
notre environnement de microservices distribués.

## 6. **RabbitMQ**
RabbitMQ est sélectionné pour délivrer les messages directement au services concernés. Implémantant une police de multi-ack, il nous permet de recevoir un ensemble de messgaes et de valider leur réception en batch en attendant leur envoi sur le seveur cloud. RabbitMQ est open-source et permet aussi de conserver les messages sur disque en cas de surcharge. RabbitMQ libère alors la mémoire RAM et permet de conserver un grand nombre de messages en cas de panne.

## 7. **Traefik**
Traefik sert d'API Gateway pour diriger le trafic vers les services. Notre usage permet d'utiliser la version open-source. Traefik est compatible avec des environnements 
comme Docker ou Kubernetes. Traefik est trés populaire et répandu sur le marché .

## 8. **Keycloak**
Keycloak est utilisé pour la gestion de l'authentification et de l’autorisation basée sur les rôles des utilisateurs 
(proches, médecins, infirmiers). En centralisant la gestion des identités, Keycloak offre une solution complète, sécurisée
et modulable pour contrôler les accès aux différentes parties du système.Il a été choisi pour son caractère open-source et populaire. De plus c'est une technologie maîtrisé par les membres de l'équipe ce qui facilite sa mise en place.
