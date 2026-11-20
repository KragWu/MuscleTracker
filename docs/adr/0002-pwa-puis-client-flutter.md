# ADR-0002 : Application web progressive (PWA) d'abord, client Flutter ensuite

- **Statut** : Acceptée
- **Date** : 2026-11-20

## Contexte

L'application sert dans deux situations : sur **mobile** pendant une séance, et sur **ordinateur ou tablette** pour consulter l'évolution. Le développeur est seul, souhaite apprendre de nouvelles technologies, mais ne veut pas supporter pour l'instant les démarches et les frais de publication dans les magasins d'applications. Le suivi de séance devra à terme fonctionner **hors ligne**.

## Options étudiées

1. **PWA** : un seul code web, responsive, installable sur l'écran d'accueil. Déploiement immédiat, sans magasin. Limites : fonctions natives restreintes, stockage local moins fiable sur iOS (Safari peut purger les données d'un site peu utilisé).
2. **Application multiplateforme** (Flutter, React Native, Kotlin Multiplatform) : rendu et accès matériel meilleurs, base locale robuste (SQLite). Contraintes : outillage, signature, publication, vue ordinateur moins naturelle (hors Flutter Web).
3. **Applications natives** (Kotlin et Swift) : meilleur rendu, mais deux bases de code en plus d'un client ordinateur.

## Décision

Nous réalisons d'abord une **PWA**. Un **client Flutter** pourra être ajouté ensuite, en consommant la même API.

- L'API est le contrat central : elle est documentée avec **OpenAPI** et ne contient aucune logique cachée dans les clients.
- Les opérations de synchronisation seront **idempotentes** : les identifiants des ressources créées hors ligne sont générés côté client (UUID).

## Conséquences

- Un seul client à maintenir pour les trois usages (mobile, tablette, ordinateur) au démarrage.
- Pas de frais ni de validation de magasin pour le MVP.
- Le hors ligne s'appuiera sur un service worker et IndexedDB, avec les limites connues d'iOS.
- Le futur client Flutter reste un **adaptateur primaire** de plus (voir ADR-0005) : le domaine n'est pas modifié.
- À réexaminer si les limites de la PWA (notifications, stockage hors ligne) deviennent bloquantes.

## Pour aller plus loin

- ADR-0003 (Angular), ADR-0005 (architecture hexagonale).
