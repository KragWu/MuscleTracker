# ADR-0006 : PostgreSQL et Spring Data JDBC

- **Statut** : Acceptée
- **Date** : 2026-11-20

## Contexte

Les données (exercices, programmes, séances, séries) sont fortement relationnelles. Le développeur utilise déjà PostgreSQL. En architecture hexagonale, la persistance est un adaptateur : elle ne doit pas contaminer le domaine.

## Options étudiées

**Base de données** : PostgreSQL (robuste, sécurisé, Testcontainers) ; SQLite ou H2 (inadaptés à un back-end multi-utilisateurs, tests non représentatifs) ; NoSQL (peu justifié pour des données relationnelles).

**Accès aux données** (comparaison qualitative) :

| Option | Adéquation à l'hexagonal | Remarque |
|---|---|---|
| Spring Data JPA (Hibernate) | Faible : les entités contaminent le domaine, requêtes N+1, état géré | Connu, mais piège pédagogique |
| **Spring Data JDBC** | Bonne : un agrégat correspond à une table, mapping prévisible | Simple, sans session ni chargement paresseux |
| jOOQ | Très bonne : SQL typé, généré depuis le schéma | Étape de génération de code |
| Exposed (Kotlin) | Bonne | Intégration Spring moins répandue |

## Décision

- Base : **PostgreSQL**, avec migrations versionnées (**Liquibase**, comme dans l'ancien projet).
- Accès : **Spring Data JDBC** dans les adaptateurs de persistance, derrière des ports.
- **jOOQ** pourra être ajouté pour des requêtes précises (historique, progression) sans modifier le domaine.
- Les tests d'intégration de persistance utilisent **Testcontainers**.

## Conséquences

- Mapping simple et prévisible, proche de la notion d'agrégat.
- Les objets du domaine restent indépendants du cadre : les entités de persistance sont des classes distinctes dans l'adaptateur.
- Moins de fonctions « magiques » que JPA : certaines requêtes devront être écrites à la main.

## Pour aller plus loin

- ADR-0005 (architecture).
