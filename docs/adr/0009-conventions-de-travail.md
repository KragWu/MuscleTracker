# ADR-0009 : Conventions de travail

- **Statut** : Acceptée
- **Date** : 2026-11-20

## Contexte

Le projet est réinitialisé sur la branche `dev/kragwu/reborn`. L'ancien code reste consultable dans la branche `legacy`. Le suivi se fait sur GitHub (dépôt `KragWu/MuscleTracker` et projet « Muscle Tracker Project »). Il faut fixer des règles simples et durables.

## Décision

**Langues**
- Documentation : **français**. Des fichiers par langue pourront être ajoutés plus tard.
- Code (noms, commentaires) et messages de commit : **anglais**.

**Dépôt**
- Racine : `docs/` (ADR et architecture), `backend/` (Kotlin, Spring Boot), `web/` (PWA Angular). Les clients futurs viendront à côté.

**Branches et commits**
- Branches : `feature/issue-<n>/<description-en-anglais>`, une par issue.
- Commits : préfixés par un **gitmoji**, en anglais.
- Les modifications arrivent dans `master` par pull request liée à son issue.

**Versions**
- Les applications suivent la **semantic version** (tags et releases `vX.Y.Z`).

**Suivi des issues**
- Labels existants : `Enhancement` (fonctionnalité), `Technical Recit` (découpage technique), `Bug`, `Documentation`, `Question`, etc.
- Colonnes du projet : **New** (ébauche), **Backlog** (rédigée, à prioriser), **Ready** (priorisée dans l'itération ou le jalon), **In progress**, **In review**, **Done** (fusionnée dans `master`), **Release** (version figée et déployée).
- Les issues sont rédigées par l'assistant et relues par le développeur avec une **revue statique** (technique ISTQB) avant création : clarté, critères d'acceptation, testabilité, taille (INVEST), dépendances.

## Conséquences

- Les anciennes issues (38) ont été fermées avec le label `Abandonned` et un renvoi vers `legacy`.
- Les jalons trimestriels existants seront remplacés par des jalons alignés sur les incréments du MVP.
- Ces conventions pourront être complétées par d'autres ADR (stratégie de test, intégration continue).

## Pour aller plus loin

- ADR-0001 (documentation des décisions).
- [Gitmoji](https://gitmoji.dev) et [Semantic Versioning](https://semver.org).
