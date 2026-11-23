# Notes d'analyse

Ce dossier conserve les **réflexions et recherches** menées pendant la construction du projet, avant qu'elles ne deviennent des décisions. Ce ne sont **pas** des références : chaque note est un **instantané daté**.

| Différence | ADR (`docs/adr/`) | Note d'analyse (ici) |
|---|---|---|
| Rôle | Fixe **une décision** | Garde le **contexte et les recherches** |
| Durée de vie | Durable (jamais réécrit une fois accepté) | Datée : prix, outils et formats évoluent |
| Sources | Citées pour la décision | Citées et **à revérifier** avant usage |

Quand une note débouche sur une décision, l'ADR correspondant la cite.

| Note | Sujet | Date |
|---|---|---|
| [Modèle économique](2026-11-modele-economique.md) | Limite d'analyse, offres des concurrents, pistes | 2026-11-22 |
| [Import des données de montres](2026-11-import-donnees-montres.md) | FIT contre JSON (course, vélo), export par marque | 2026-11-22 |
| [Programme multidisciplinaire](2026-11-programme-multidisciplinaire.md) | Programme mêlant musculation et endurance | 2026-11-22 |
| [Supersets et minuteur](2026-11-supersets-et-minuteur.md) | Blocs d'exercices, minuteur avec bips, limites de la PWA | 2026-11-23 |

## Règle sur les données personnelles

Les fichiers d'exemple exportés d'une montre (tracé GPS, fréquence cardiaque, numéro de série) restent dans `local-data/`, **ignoré par Git** : le dépôt est public. Un jeu de test anonymisé et réduit sera créé le moment venu.
