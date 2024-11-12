# Sécurité des données

## Risque :
##### Perte des données : 
**Coupure internet :**  
En cas de coupure de la connexion entre la gateway et le serveur, la gateway ne peut plus déverser les mesures prises dans le serveur. La solution implémentée embarque un broker [RabbitMQ](https://www.rabbitmq.com/). Ce broker permet de stocker les métriques récoltées sur le disque de la gateway en s'assurant de leur transmission avant de les supprimer du disque.

**Incident côté serveur :**  
Grâce à la technologie cloud et à la réplication possible avec le serveur Prometheus, les données des patients peuvent être sauvegardées en différents endroits, garantissant qu'en cas de perte d'un serveur, le deuxième puisse prendre le relais sans perte de données.

Des sauvegardes de la base de données sont également réalisées toutes les 4 heures.

##### Vol des données :
**Côté gateway :**  
Les données des patients sont stockées sur la gateway de manière anonyme. Chaque patient se voit attribuer une gateway possédant son propre identifiant. Les données sont remontées au serveur avec l’identifiant de la gateway et non celui du patient. Le lien entre l'identité du patient et les données récoltées n'est fait qu'au moment de la visualisation.

**Pendant l'envoi :**  
Les envois de données sont réalisés de manière sécurisée avec le protocole HTTPS, qui permet un envoi chiffré des données.