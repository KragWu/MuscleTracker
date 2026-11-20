# ADR-0001 : Documenter les décisions d'architecture

- **Statut** : Acceptée
- **Date** : 2026-11-20

## Contexte

MuscleTracker est réinitialisé avec une démarche pédagogique : chaque choix d'outil ou d'architecture doit pouvoir être expliqué, relu et remis en question. Le projet est mené par une seule personne, ce qui rend la mémoire des décisions fragile : on oublie vite pourquoi une option a été écartée.

## Options étudiées

1. **Aucune trace formelle** : rapide, mais les raisons se perdent.
2. **Un wiki ou un outil externe** : confortable, mais séparé du code et de son historique.
3. **Des ADR (Architecture Decision Records) en Markdown dans le dépôt** : une décision par fichier, versionnée avec le code, relue comme n'importe quelle modification.

## Décision

Nous utilisons des **ADR en Markdown** dans `docs/adr/`, selon le modèle `0000-template.md`.

- Un fichier par décision, nommé `NNNN-titre-en-minuscules.md`, numéroté dans l'ordre de création.
- Une décision acceptée n'est **jamais réécrite** : si elle change, on crée un nouvel ADR qui la remplace, et on met à jour le statut de l'ancien.
- Les schémas sont écrits en **Mermaid**, directement dans le Markdown (rendus par GitHub).
- La documentation est rédigée en **français**. Des versions par langue pourront être ajoutées plus tard dans des fichiers séparés.

## Conséquences

- L'historique des décisions est consultable avec `git log`, au même endroit que le code.
- Chaque choix demande un petit effort de rédaction, mais il sert aussi de support d'apprentissage.
- La liste des ADR est tenue à jour dans `docs/adr/README.md`.

## Pour aller plus loin

- Michael Nygard, « Documenting Architecture Decisions » (format d'origine).
- MADR (Markdown Architectural Decision Records), dont ce modèle s'inspire.
