# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Vue d'ensemble

MuscleTracker : application de préparation et de suivi de séances de musculation (mobile pendant la séance, ordinateur ou tablette pour consulter l'évolution). Le projet a été **réinitialisé** sur la branche `dev/kragwu/reborn` ; l'ancienne implémentation est conservée dans la branche `legacy` (ne pas s'en servir comme référence d'architecture).

Le dépôt est en cours de remise en place : `backend/` et `web/` ne contiennent pour l'instant qu'un `README.md`. Il n'y a **pas encore de commandes de build ou de test** : ne pas en inventer, et mettre ce fichier à jour dès que les projets seront générés.

## Source de vérité : les ADR

Les décisions d'architecture et d'outils sont dans `docs/adr/` (index : `docs/adr/README.md`). Les lire avant de proposer un choix technique, et créer un nouvel ADR (jamais réécrire un ADR accepté) quand une décision change. Résumé :

- PWA Angular d'abord, client Flutter plus tard, via la même API (OpenAPI d'abord).
- Back-end Kotlin, Spring Boot MVC (threads virtuels, pas de WebFlux), PostgreSQL avec Spring Data JDBC et Liquibase.
- Architecture hexagonale en monolithe modulaire (modules `catalog`, `programs`, `sessions`, `users`), frontières vérifiées par ArchUnit. Pas de micro-services.
- Authentification : Supabase Auth (CLI en local), le back-end est un resource server qui valide des JWT OIDC. Une table d'utilisateurs interne relie l'identifiant interne au `sub` du jeton : ne jamais utiliser l'identifiant du fournisseur comme clé métier.
- Hébergement : présélection seulement (ADR-0008 « Proposée »), à confirmer au premier déploiement. Ne pas présenter ces choix comme définitifs.

## Périmètre du MVP

Bibliothèque d'exercices prédéfinie, programme de séance, suivi de séance, historique. Hors MVP : graphiques de progression, planning, hors ligne (second incrément, mais l'architecture doit le permettre : identifiants générés côté client, opérations idempotentes).

## Méthode de travail

- **TDD outside-in** (Red → Green → Refactor) : écrire d'abord un test d'acceptation sur un port d'entrée, puis descendre vers le domaine. Domaine sans dépendance au cadre.
- Pyramide de tests, outils et contrats : voir ADR-0010. L'outil de test du front reste à choisir à la génération du projet Angular, puis à consigner dans l'ADR.
- Démarche pédagogique : expliquer les choix, documenter dans `docs/`.

## Conventions

- Documentation en **français**, avec les accents. Code (identifiants, commentaires) et messages de commit en **anglais**.
- Commits préfixés par un gitmoji (ex. `:sparkles:`, `:memo:`).
- Branches : `feature/issue-<n>/<description-en-anglais>`. Intégration par pull request liée à l'issue.
- Versions : Semantic Versioning (tags et releases `vX.Y.Z`).
- Schémas en Mermaid dans le Markdown.

## Suivi GitHub

Dépôt `KragWu/MuscleTracker`, projet « Muscle Tracker Project » (n° 8). Statuts : New, Backlog, Ready, In progress, In review, Done, Release. Labels principaux : `Enhancement` (fonctionnalité), `Technical Recit` (découpage technique), `Bug`, `Documentation`.

- Les issues sont rédigées par l'assistant et **relues par le développeur (revue statique ISTQB) avant toute création** sur GitHub. Ne jamais créer, fermer ou modifier des issues sans accord explicite.
- Ne pas pousser ni publier sans demande explicite.
