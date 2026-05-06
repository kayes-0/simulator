# Space Launch Simulator

Un simulateur de lancement spatial en Java permettant de composer des fusées et de simuler différentes missions spatiales.

---

## Table des matières

- [Aperçu](#aperçu)
- [Fonctionnalités](#fonctionnalités)
- [Architecture du projet](#architecture-du-projet)
- [Catalogue des composants](#catalogue-des-composants)
- [Règles de simulation](#règles-de-simulation)
- [Installation et lancement](#installation-et-lancement)
- [Utilisation](#utilisation)
- [Persistance des données](#persistance-des-données)
- [Structure des fichiers](#structure-des-fichiers)
- [Déclaration IA](#déclaration-ia)

---

## Aperçu

Space Launch Simulator est une application console interactive qui permet à l'utilisateur de :

1. Composer une fusée en choisissant un lanceur, une capsule et des boosters.
2. Choisir une mission parmi plusieurs destinations (Orbite, ISS, Lune, Mars, Pluton).
3. Simuler le lancement avec validation des contraintes techniques et un taux de panne aléatoire.
4. Consulter l'historique des lancements, persisté dans un fichier texte.

Le projet illustre les principes orientés objet en Java : héritage, polymorphisme, pattern Singleton, gestion d'exceptions personnalisées et sérialisation fichier.

---

## Fonctionnalités

- Sélection interactive de composants depuis des catalogues prédéfinis
- Calcul dynamique de la masse totale, du carburant requis et du coût de la mission
- Validation des contraintes : capacité carburant, charge utile, nombre de boosters, compatibilité équipage
- Taux de panne aléatoire de 5 % simulant des anomalies techniques imprévues
- Sauvegarde et chargement automatique de l'historique des lancements (`history.txt`)
- Pattern Singleton pour le Simulateur, garantissant une instance unique

---

## Architecture du projet

```
simulator/
└── models/
    ├── main.java                        # Point d'entrée
    ├── simulator.java                   # Logique principale (Singleton)
    ├── rocket.java                      # Composition de la fusée
    ├── launch.java                      # Enregistrement d'un lancement
    │
    ├── Launcher/
    │   ├── launcher.java                # Classe abstraite Launcher
    │   ├── ariane5.java
    │   ├── falcon9.java
    │   ├── saturneV.java
    │   └── sls.java
    │
    ├── Pod/
    │   ├── pod.java                     # Classe abstraite Pod
    │   ├── apollo.java
    │   ├── crewdragon.java
    │   ├── cargodragon.java
    │   └── orion.java
    │
    ├── Booster/
    │   ├── booster.java                 # Classe abstraite Booster
    │   ├── be3.java
    │   ├── eapariane.java
    │   └── srbshuttle.java
    │
    ├── Mission/
    │   ├── mission.java                 # Classe abstraite Mission
    │   ├── orbit.java
    │   ├── iss.java
    │   ├── moon.java
    │   ├── mars.java
    │   └── pluto.java                   # Mission personnelle
    │
    └── Exception/
        └── NotEnoughFuelException.java  # Exception
```

---

## Catalogue des composants

### Lanceurs

| Nom        | Équipage   | Boosters max | Carburant max (t) | Charge utile max (t) | Prix (M EUR) | Poussée (kN) |
|------------|----------- |:------------:|:-----------------:|:--------------------:|:------------:|:------------:|
| Saturne V  | oui        | 0            | 2 700             | 140                  | 1 500        | 3 500        |
| Ariane 5   | non        | 2            | 700               | 20                   | 180          | 2 500        |
| Falcon 9   | oui        | 0            | 500               | 22                   | 60           | 1 500        |
| SLS        | oui        | 2            | 2 600             | 130                  | 2 000        | 2 500        |

### Capsules (Pods)

| Nom          | Équipage   | Passagers max | Masse (t) | Prix (M EUR) |
|--------------|------------|:-------------:|:---------:|:------------:|
| Orion        | oui        | 4             | 10,4      | 300          |
| Crew Dragon  | oui        | 7             | 12,0      | 150          |
| Apollo       | oui        | 3             | 5,6       | 200          |
| Cargo Dragon | non        | 0             | 9,5       | 100          |

### Boosters

| Nom           | Poussée max (kN) | Masse (t) | Prix (M EUR) |
|---------------|:----------------:|:---------:|:------------:|
| EAP (Ariane)  | 6 470            | 270       | 30           |
| SRB (Shuttle) | 12 500           | 590       | 55           |
| BE-3          | 490              | 25        | 12           |

### Missions

| Destination | Équipage requis   | Distance (km)   | Coefficient    |
|-------------|-------------------|:---------------:|:--------------:|
| Orbit       | non               | 400             | 1,0            |
| ISS         | oui               | 400             | 1,2            |
| Moon        | oui               | 400 000         | 0,005          |
| Mars        | oui               | 225 000 000     | 0,000015       |
| Pluto       | oui               | 480 000 000     | 0,00067        |

---

## Règles de simulation

Le simulateur vérifie plusieurs contraintes avant de valider un lancement. Un **échec** est retourné si l'une d'elles est violée :

| Contrainte                            | Exception / Raison                                    |
|---------------------------------------|-------------------------------------------------------|
| Carburant requis > capacité lanceur   | `NotEnoughFuelException` → "Not enough fuel capacity" |
| Masse totale > charge utile max       | "Payload limit exceeded"                              |
| Nombre de boosters > maximum autorisé | "Too many boosters"                                   |
| Mission avec équipage + capsule cargo | "Pod incompatible with crewed mission"                |
| Panne aléatoire (5 % de probabilité)  | "Unexpected technical anomaly"                        |

### Calcul du carburant requis

```
fuel = (masse_totale × distance × coefficient) / 1000
```

### Calcul du coût total

```
coût = prix_lanceur + prix_capsule + Σ prix_boosters + (carburant × 1200 / 1 000 000)  [M EUR]
```

---

## Installation et lancement

### Prérequis

- Java 17 ou supérieur
- Un terminal / IDE compatible Java

### Compilation

```bash
cd simulator/models
javac -d . *.java Launcher/*.java Pod/*.java Booster/*.java Mission/*.java Exception/*.java
```

### Exécution

```bash
java main
```

---

## Utilisation

Au démarrage, le menu principal s'affiche :

```
=== Space Launch Simulator ===
1. Start a launch
2. Show history
0. Quit
```

Processus de lancement (option 1) :

1. Choisir un lanceur dans la liste
2. Choisir une capsule dans la liste
3. Indiquer le nombre de boosters souhaités et les choisir un par un
4. Choisir une mission dans la liste
5. Le simulateur affiche le résultat immédiatement

---

## Persistance des données

L'historique est automatiquement sauvegardé dans le fichier `history.txt`.

```
<date>;<nom_fusée>;<mission>;<succès>;<raison>;<coût>
```

Le fichier est rechargé automatiquement à chaque démarrage du simulateur.

---

## Structure des fichiers

| Fichier / Dossier       | Rôle                                                            |
|-------------------------|-----------------------------------------------------------------|
| `main.java`             | Point d'entrée, instancie et démarre le Simulateur              |
| `simulator.java`        | Orchestration complète : menus, sélection, simulation, I/O      |
| `rocket.java`           | Composition de la fusée (lanceur + capsule + boosters)          |
| `launch.java`           | Représentation d'un lancement (sérialisation / désérialisation) |
| `Launcher/launcher.java`| Classe abstraite définissant un lanceur                         |
| `Pod/pod.java`          | Classe abstraite définissant une capsule                        |
| `Booster/booster.java`  | Classe abstraite définissant un booster                         |
| `Mission/mission.java`  | Classe abstraite définissant une mission                        |
| `Exception/`            | Exception `NotEnoughFuelException`                              |
| `history.txt`           | Historique des lancements                                       |

---

## Déclaration IA

L'intelligence artificielle a surtout été utilisé pour régler des petits problèmes récurrents dans certains fichiers, comme par exemple des différences de noms entre les classes, ou pour ajouter et comprendre des utilisations des différentes imports que l'on doit utiliser en java (typiquement le import 'java.time.LocalDateTime' dans le fichier launch.java).