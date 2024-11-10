# Use Case

Dans cette section, nous décrivons les cas d'utilisation sur lesquels repose notre étude, en identifiant les actions, 
les rôles des utilisateurs et les objectifs pour chaque fonctionnalité du système. Chaque cas d'utilisation est conçu 
pour répondre aux besoins spécifiques des différents acteurs (médecins, infirmiers, proches, patients, développeurs 
et système IoT) et contribue à la prise en charge et à la surveillance efficace des personnes âgées.

## 1. Médecin
- **En tant que médecin**, je veux enregistrer un rapport de consultation, afin de suivre l'évolution du patient 
et partager des informations avec d'autres professionnels de santé.
- **En tant que médecin**, je veux visualiser les rapports médicaux détaillés, afin de disposer d'une vue complète 
sur l'historique de santé du patient.
- **En tant que médecin**, je veux assigner des capteurs à un patient, afin de surveiller en temps réel ses signes 
vitaux et recevoir des alertes en cas d'incidents.
- **En tant que médecin**, je veux gérer des patients, afin de pouvoir créer, supprimer et modifier les comptes 
pour organiser le suivi.

## 2. Médecin ou Infirmier
- **En tant que médecin ou infirmier**, je veux gérer les proches des patients, afin de leur donner la possibilité 
de suivre l'état de santé du patient.
- **En tant que médecin ou infirmier**, je veux être notifié lorsque les capteurs cessent d'envoyer des données 
pendant une période prolongée, afin de pouvoir intervenir et éviter toute perte d'information ou problème de santé non détecté.

## 3. Infirmier
- **En tant qu'infirmier**, je veux enregistrer un rapport de santé du patient, afin de mettre à jour 
les informations médicales régulièrement.
- **En tant qu'infirmier**, je veux pouvoir renseigner des mesures manuelles dans le système, afin d’ajouter 
des informations supplémentaires non collectées par les capteurs, comme le taux de glucose.
- **En tant qu'infirmier**, je veux pouvoir visualiser les données des capteurs, afin de suivre l'état de santé 
de la personne de manière proactive.

## 4. Proche du Patient
- **En tant que membre de la famille**, je veux visualiser l'état général du patient, afin d’avoir un aperçu de 
sa santé sans accéder aux données médicales sensibles.
- **En tant que membre de la famille ou infirmier**, je veux être notifié en cas d'urgence, afin d'intervenir 
le plus tôt possible si nécessaire.

## 5. Patient
- **En tant que patient**, je veux remplir un formulaire de ressenti sur mon état de santé, afin de partager 
mon vécu quotidien et mes symptômes avec les professionnels de santé pour un suivi plus personnalisé.

## 6. Développeur
- **En tant que développeur**, je veux administrer les utilisateurs, afin d'assurer un accès sécurisé et limité 
aux informations sensibles.
- **En tant que développeur**, je veux configurer et mettre à jour le système, afin de garantir la bonne intégration 
des nouveaux capteurs et la sécurité des données.

## 7. Système IoT
- **En tant que système IoT**, je veux remonter automatiquement les données, afin de surveiller en temps réel l'état 
du patient.
- **En tant que système IoT**, je veux notifier automatiquement les utilisateurs en cas de dysfonctionnement ou d'alerte, 
afin qu'ils puissent réagir rapidement en cas de problème.
