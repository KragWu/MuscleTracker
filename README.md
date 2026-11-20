# MuscleTracker

Application de préparation et de suivi de séances de musculation.

- **Sur mobile**, pendant la séance : saisir les séries réalisées.
- **Sur ordinateur ou tablette** : consulter son historique et, plus tard, l'évolution de sa progression.

> Le projet a été **réinitialisé**. L'ancienne implémentation reste consultable dans la branche [`legacy`](../../tree/legacy).

## Périmètre du MVP

1. **Bibliothèque d'exercices** : mouvements prédéfinis, avec groupes musculaires et équipements nécessaires.
2. **Programme de séance** (le planning viendra plus tard).
3. **Suivi de séance**.
4. **Historique**.

Hors MVP : graphiques de progression, planning, fonctionnement hors ligne (prévu au second incrément).

## Démarche

- **TDD** (outside-in) et **architecture hexagonale** sur chaque composant.
- Démarche **pédagogique** : chaque choix d'outil ou d'architecture est expliqué dans un [ADR](docs/adr/README.md).

## Organisation du dépôt

```
docs/        Documentation (ADR, schémas d'architecture)
backend/     API : Kotlin, Spring Boot MVC, PostgreSQL (à venir)
web/         PWA : Angular (à venir)
```

Un client Flutter pourra s'ajouter plus tard, à côté.

## Documentation

- [Décisions d'architecture (ADR)](docs/adr/README.md)

## Suivi du projet

- Issues et projet GitHub « Muscle Tracker Project ».
- Branches : `feature/issue-<n>/<description-en-anglais>`.
- Commits en anglais, préfixés par un gitmoji.
- Versions : [Semantic Versioning](https://semver.org).

## Licence

Voir [LICENSE](LICENSE).
