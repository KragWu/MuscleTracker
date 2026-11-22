# Feuille de route

> **Statut : mise à jour le 2026-11-22.** Le suivi précis des tâches est dans les **issues GitHub** et le projet « Muscle Tracker Project » ; cette page ne donne que la vue d'ensemble. Les dates ne sont pas fixées : la progression se mesure en jalons.

## Jalons du MVP

| Jalon | Contenu | Détail |
|---|---|---|
| `v0.1.0 - Fondations` | Squelettes back-end et PWA, contrat OpenAPI, pile locale, intégration continue, contrôles locaux | [Lot 1](backlog/lot-01-fondations.md) |
| `v0.2.0 - Bibliothèque d'exercices` | Catalogue prédéfini, consultation, filtres, traductions | [Lot 2](backlog/lot-02-bibliotheque-d-exercices.md) |
| `v0.3.0 - Identité utilisateur` | Connexion, utilisateur interne, profil, protection de l'API et du catalogue | [Lot 3](backlog/lot-03-gestion-utilisateur.md) |
| `v0.4.0 - Préparer une séance` | Créer une séance, choisir les exercices avec leurs **cibles** (séries, répétitions, charge), dupliquer | Lot 4 à réécrire |
| `v0.5.0 - Suivi de séance` | Saisir les séries réalisées, afficher les **dernières valeurs connues** | À rédiger |
| `v0.6.0 - Historique` | Consulter les séances passées | À rédiger |
| `v1.0.0 - MVP` | Export et suppression de compte, politique de confidentialité définitive, déploiement | À rédiger |

## Après le MVP (sans ordre figé)

| Chantier | Idée | Note |
|---|---|---|
| Programmes d'entraînement | Suite de séances dans le temps avec objectifs et annulation de séance | [Programme multidisciplinaire](analyses/2026-11-programme-multidisciplinaire.md) |
| Séances d'endurance | Import de fichiers de montres (FIT, puis GPX et TCX) pour course, vélo, natation | [Import des données de montres](analyses/2026-11-import-donnees-montres.md) |
| Programme multidisciplinaire | Mélanger musculation et endurance dans un programme | idem |
| Analyses et progression | Graphiques de tendance ; fenêtre d'analyse paramétrable | [Modèle économique](analyses/2026-11-modele-economique.md) |
| Fonctionnement hors ligne | Saisie de séance sans réseau, puis synchronisation | ADR à rédiger |
| Client Flutter | Enregistrement GPS en direct, meilleur hors ligne | [ADR-0002](adr/0002-pwa-puis-client-flutter.md) |
| Interface d'administration | Saisie des exercices sans passer par les fichiers | Après le catalogue de départ |
| Modèle économique | Offre gratuite et payante, **à décider avec de vrais usages** | [Modèle économique](analyses/2026-11-modele-economique.md) |

## Vocabulaire

Voir le [glossaire](glossaire.md). En particulier, « programme » désigne une suite de séances dans le temps, et non le plan d'une séance.
