# ADR-0011 : Modules métier et vocabulaire

- **Statut** : Acceptée
- **Date** : 2026-11-22

## Contexte

L'ADR-0005 a posé quatre modules (`catalog`, `programs`, `sessions`, `users`). Ces noms étaient une **proposition de l'assistant**, faite avant d'avoir précisé le vocabulaire du métier avec le développeur. Les échanges du 2026-11-22 ont montré :

- ce que nous appelions « programme de séance » est en réalité une **séance** ;
- un **programme** est une **suite de séances dans le temps**, avec un objectif ; il est prévu après le MVP, et pourra **mêler plusieurs disciplines** (musculation, course, vélo, natation) ;
- les **séances d'endurance** (saisies ou importées depuis une montre) sont prévues après le MVP et ont un contenu très différent d'une séance de musculation (distance et allure contre séries, répétitions et charge) : ce sont **deux types de séance**, d'où un même mot et un même suffixe `Workout` ;
- dans le code, `Session` est ambigu : l'authentification utilise déjà ce mot (la connexion).

Voir [le glossaire](../glossaire.md) et [la feuille de route](../feuille-de-route.md).

## Options étudiées

1. **Un module `sessions` générique** pour toutes les séances. Évite la duplication, mais le contenu des séances de musculation et d'endurance n'a presque rien en commun : le module serait un fourre-tout, et `sessions` reste ambigu avec la connexion.
2. **Un module par discipline, plus un module de programme** : `strength` (séances de musculation), `endurance` (séances d'endurance), `programs` (programmes). Chaque module a un vocabulaire cohérent ; le programme référence les séances par identifiant.
3. **Un unique module `workouts`** avec des sous-types. Plus compact, mais couple dès le départ des disciplines que rien ne relie encore dans le MVP.

## Décision

Nous retenons l'**option 2** :

| Module | Contenu | Périmètre |
|---|---|---|
| `catalog` | Exercices, groupes musculaires, équipements, traductions | MVP |
| `users` | Utilisateurs, préférences, correspondance avec l'identité externe | MVP |
| `strength` | Séances de musculation (`StrengthWorkout`), cibles, séries réalisées, dernières valeurs connues, duplication | MVP |
| `endurance` | Séances d'endurance (`EnduranceWorkout`), import de fichiers | Réservé |
| `programs` | Programmes (suite de séances, objectifs), qui **référencent** les séances par identifiant | Réservé |

- Le terme **`Workout`** est réservé au futur tronc commun des séances : `StrengthWorkout` et `EnduranceWorkout` en seront les deux types. Il n'est **pas** introduit tant qu'un seul type de séance existe.
- Le mot **« activité »** (type de message FIT, Strava, Garmin) n'est pas employé dans le domaine : il reste dans l'**adaptateur d'import**, qui le traduit en séance d'endurance. Le domaine ne dépend ainsi pas du vocabulaire d'un format de fichier.
- Le code évite `Session` pour une séance d'entraînement et `Set` pour une série : on écrit `Workout` et `WorkoutSet`.
- Le module `programs` de l'ADR-0005 **change de sens** : il ne désigne plus le plan d'une séance mais le futur programme.
- Les modules `endurance` et `programs` sont **créés seulement quand leur première issue démarre**, comme pour les autres modules (ADR-0005).

## Conséquences

- Le vocabulaire du code, des issues et de l'interface suit le [glossaire](../glossaire.md).
- Le lot 4 (anciennement « programmes de séance ») est reformulé autour de la **séance de musculation** et du module `strength`.
- Les dépendances entre modules iront dans un seul sens : `programs` dépend de `strength` et `endurance` (par identifiants), jamais l'inverse. Les tests ArchUnit le vérifieront.
- **ADR-0005** : sa décision (hexagonal, monolithe modulaire) est inchangée ; seule sa liste de modules est précisée par cet ADR. Le statut de l'ADR-0005 a été complété en conséquence (règle de l'ADR-0001) ; son texte d'origine n'est pas modifié.
- **Coût** : si la séance de musculation et la séance d'endurance partagent plus de comportements que prévu (calendrier, états, ressenti), nous pourrons extraire un module commun `workouts` plus tard ; cela se fera par un nouvel ADR, avec des mesures d'usage.
- À réexaminer au démarrage du premier lot sur les programmes.

## Pour aller plus loin

- [Glossaire](../glossaire.md)
- [Feuille de route](../feuille-de-route.md)
- ADR-0005 (architecture hexagonale en monolithe modulaire).
