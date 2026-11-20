# ADR-0003 : Angular pour la PWA

- **Statut** : Acceptée
- **Date** : 2026-11-20

## Contexte

La PWA (ADR-0002) a besoin d'un framework front. Le développeur utilise déjà Angular, et souhaite réserver son envie de nouveauté au futur client Flutter. Apprendre deux technologies nouvelles à la fois diluerait l'objectif principal, la méthode (TDD, architecture hexagonale, documentation).

## Options étudiées

1. **Angular** : connu du développeur, TypeScript, tests intégrés, support PWA officiel (service worker). Plus verbeux, mais structurant.
2. **React** : très répandu, plus léger, mais demande plus de choix d'outils (routage, état, PWA).
3. **Vue ou Svelte** : agréables à écrire, mais nouveauté supplémentaire à apprendre.

## Décision

Nous utilisons **Angular** (dernière version stable, composants autonomes) pour la PWA, avec le support PWA officiel.

## Conséquences

- Aucun coût d'apprentissage sur le front : l'effort porte sur la méthode.
- Les tests unitaires s'appuient sur l'outillage fourni par Angular, conformément à la démarche TDD.
- La logique d'accès à l'API est isolée derrière des **ports** côté front également, pour faciliter les tests et la cohabitation avec le futur client Flutter.
- Le client React archivé dans la branche `legacy` n'est pas repris.

## Pour aller plus loin

- ADR-0002 (PWA), ADR-0005 (architecture hexagonale).
