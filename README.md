
Structure Globale du Projet
Le projet est une architecture basée sur des microservices utilisant Spring Boot et Maven. Il se compose de plusieurs microservices, chacun responsable d'une fonctionnalité spécifique. Les principaux composants sont :

Service discovery : Gère l'enregistrement et la découverte des services via Eureka.

Service de Passerelle : Sert de point d'entrée unique pour les requêtes client, en les dirigeant vers les microservices appropriés.

Serveur de Configuration : Gère les configurations externes pour les microservices.

Service de Frais : Gère les frais associés aux transactions.

Service de Transfert : Gère les opérations de transfert d'argent.

