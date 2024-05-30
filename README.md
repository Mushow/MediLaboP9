# Environnement de développement
## Prérequis

- JDK 17
- Maven
- MySQL
- MongoDB
- Docker

## Ports utilisés
Assurez-vous que les ports nécessaires sur votre machine sont disponibles.

```java
10001 - Gateway
10002 - PatientService
10003 - NoteService
10004 - RiskService
10005 - ClientFrontService
27017 - Default MongoDB
3306 - Default MySQL
```

## Démarrage de l'Application

Placez vous dans le dossier racine du projet et exécutez la commande suivante pour démarrer l'application:

```bash
docker-compose up -d
```

## Connexion à l'application

Différents rôles: `doctor`, `organizer`
Mot de passe commun: `rootroot`

## Recommandations Green Code

### 1. Éco-Conception Logicielle
#### Réutilisabilité et Modularité:

- Adopter une architecture modulaire pour permettre la réutilisation des composants et éviter le code redondant.

### 2. Gestion de MongoDB
#### Indexation:

- Utiliser des index pour optimiser les requêtes de lecture et réduire la charge sur le serveur MongoDB.
- Surveiller et maintenir les index pour s'assurer qu'ils sont pertinents et performants.

### 3. Utilisation Efficiente de la Mémoire:

- Préférer l’utilisation de structures de données adaptées pour minimiser la consommation de mémoire.
- Utiliser des bibliothèques légères et éviter les dépendances inutiles.