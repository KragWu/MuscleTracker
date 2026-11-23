# Glossaire

> **Statut : validé le 2026-11-22.** Ce glossaire fixe le **langage commun** du projet : un terme ne doit avoir qu'un seul sens dans la documentation, les issues, l'interface et le code.
> La documentation est en français, le code en anglais (ADR-0009) : chaque terme indique donc son équivalent dans le code. La colonne « Périmètre » dit si le concept est dans le MVP ou réservé.

## Utilisateur et profil

| Terme | Code | Définition | Périmètre |
|---|---|---|---|
| **Utilisateur** | `User` | Personne ayant un compte. Son identifiant interne est généré par l'application et distinct de celui du fournisseur d'authentification (ADR-0007). | MVP |
| **Préférences** | `Preferences` | Langue et unité de charge de l'utilisateur. | MVP |
| **Unité de charge** | `WeightUnit` (`KG`, `LB`) | Kilogrammes ou livres. Une charge garde l'unité avec laquelle elle a été saisie. | MVP |
| **Connexion** | `AuthSession` | Période pendant laquelle l'utilisateur est authentifié (jeton). **Sans rapport avec une séance d'entraînement.** | MVP, hors domaine métier |

## Catalogue

| Terme | Code | Définition | Périmètre |
|---|---|---|---|
| **Exercice** | `Exercise` | Mouvement du catalogue, avec un **identifiant stable** (par exemple `barbell-squat`), des groupes musculaires et des équipements. | MVP |
| **Groupe musculaire** | `MuscleGroup` | Muscle sollicité, **principal** ou **secondaire**. | MVP |
| **Équipement** | `Equipment` | Matériel nécessaire à un exercice. | MVP |
| **Catalogue** (ou bibliothèque) | `Catalog` | Ensemble prédéfini des exercices, chargé depuis des fichiers versionnés. | MVP |
| **Exercice désactivé** | `Exercise.active = false` | Exercice retiré du catalogue : jamais supprimé, toujours lisible par l'historique, mais non ajoutable à une nouvelle séance. | MVP |

## Séance de musculation

| Terme | Code | Définition | Périmètre |
|---|---|---|---|
| **Séance** (terme générique) | `Workout` | Moment d'entraînement. Terme **réservé** : il désigne le tronc commun des séances de musculation et des séances d'endurance (`StrengthWorkout` et `EnduranceWorkout`). Sans objet tant qu'un seul type existe. | Réservé |
| **Séance de musculation** | `StrengthWorkout` | Ensemble d'exercices à réaliser ou réalisés lors d'un entraînement. **Créée au moment où l'on veut la faire**, et dupliquable pour la fois suivante. | MVP |
| **État d'une séance** | `WorkoutStatus` | Prévue, réalisée ou annulée. Liste exacte à fixer dans l'ADR du lot concerné ; l'annulation prendra tout son sens avec les programmes. | À préciser |
| **Exercice de séance** | `WorkoutExercise` | Un exercice du catalogue dans une séance, avec sa **cible** et ses **séries réalisées**. | MVP |
| **Cible** | `Target` | Ce que l'on se propose de faire pour un exercice : nombre de séries, répétitions par série et charge. | MVP |
| **Série** | `WorkoutSet` | Enchaînement de répétitions sans pause. Réalisée, elle porte les **répétitions réussies** et la **charge**. | MVP |
| **Répétitions réussies** | `completedReps` | Nombre de répétitions effectivement accomplies dans une série. | MVP |
| **Charge** | `Load` | Valeur et unité de la charge (kilogrammes ou livres). | MVP |
| **Dernières valeurs connues** | `LastPerformance` | Résultats de la **dernière réalisation de l'exercice par l'utilisateur, dans n'importe quelle séance**. Servent de repère à battre. | MVP (avec l'exécution) |
| **Duplication** | `duplicate` | Création d'une nouvelle séance à partir d'une précédente. | MVP |

> **Note de code.** `Set` désignerait la collection du langage ; on écrit `WorkoutSet`. `Session` désignerait la connexion : on écrit `Workout`.

## Bloc d'exercices (après le MVP, concept réservé)

| Terme | Code | Définition | Périmètre |
|---|---|---|---|
| **Bloc** | `ExerciseBlock` | Regroupement d'exercices d'une séance qui s'enchaînent sans repos entre eux ; le repos vient après le bloc. | Après MVP, réservé |
| **Superset** | `Superset` | Bloc de **deux** exercices (terme d'usage ; au-delà, on parle de bloc ou de série géante). | Après MVP, réservé |
| **Tour de bloc** | `Round` | Un passage complet dans le bloc : une série de chaque exercice. **À ne pas confondre** avec le tour (`Lap`) d'une séance d'endurance. | Après MVP, réservé |
| **Temps de travail / de repos** | `workDuration`, `restDuration` | Durées facultatives d'un bloc, qui pourront alimenter un **minuteur** avec bips. | Après MVP, réservé |

> **Note.** Le concept est seulement **réservé** : un exercice de séance pourra appartenir à un bloc facultatif, sans rupture des données. Rien n'est implémenté avant le lot dédié.

## Séance d'endurance (après le MVP)

| Terme | Code | Définition | Périmètre |
|---|---|---|---|
| **Séance d'endurance** | `EnduranceWorkout` | Course à pied, vélo ou natation : durée, distance, allure, dénivelé, fréquence cardiaque. Saisie à la main ou **importée** d'un fichier de montre. | Après MVP |
| **Sport** | `Sport` | Discipline de la séance d'endurance (course, vélo, natation). | Après MVP |
| **Tour** | `Lap` | Portion d'une séance d'endurance (automatique ou manuelle). | Après MVP |
| **Tracé** | `Track` | Suite de positions GPS. **Donnée de localisation sensible** : conservée seulement si l'utilisateur le souhaite. | Après MVP |
| **Import** | `WorkoutImport` | Chargement d'une séance d'endurance depuis un fichier exporté d'une montre (FIT d'abord, puis GPX et TCX). | Après MVP |

> **Note de vocabulaire.** « Activité » est le mot des fichiers et des plateformes (type de message FIT, Strava, Garmin). Il reste cantonné à l'**adaptateur d'import**, qui le traduit en séance d'endurance : le domaine n'emploie que « séance ».

## Programme (après le MVP)

| Terme | Code | Définition | Périmètre |
|---|---|---|---|
| **Programme** | `Program` | **Suite de séances dans le temps**, avec une durée, des objectifs de performance et un nombre théorique de séances par semaine. Il peut mêler plusieurs disciplines. | Après MVP |
| **Objectif de programme** | `ProgramGoal` | Résultat visé par l'utilisateur : maintenir ou augmenter sa performance, exprimé par exercice ou par discipline. | Après MVP |
| **Séance annulée** | `WorkoutStatus.CANCELLED` | Séance prévue par un programme que l'utilisateur ne peut pas faire ; elle reste visible. | Après MVP |
| **Modèle de séance** | `WorkoutTemplate` | Séance type à intégrer dans un programme. **Utilité à vérifier** après usage de la duplication. | Après MVP, si utile |

## Termes à éviter

| À éviter | Raison | À utiliser |
|---|---|---|
| **Programme de séance** | Ancien nom du plan d'une séance ; le mot « programme » désigne désormais une suite de séances dans le temps. | **Séance** (prévue) |
| **Session** | Ambigu avec la connexion. | **Séance** (code : `Workout`) |
| **Planning** | Recouvre deux idées. | **Programme** (suite de séances) ou **calendrier** (jours choisis) |
