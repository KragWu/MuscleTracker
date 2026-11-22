# Modèle économique : limiter l'analyse

> **Statut : analyse du 2026-11-22. Aucune décision prise.** Pour le MVP, **rien n'est limité** : le développeur veut pouvoir utiliser l'application sans restriction.
> Les prix et limites des concurrents proviennent de blogs de comparaison, souvent écrits par des concurrents ou des affiliés. **À revérifier sur les fiches officielles** avant tout usage.

## L'idée de départ

Limiter ce qu'un utilisateur gratuit peut analyser, parce que les données apportent de la valeur et qu'il faut environ **trois mois** pour percevoir une évolution. L'idée initiale d'un plafond de **12 séances** reposait sur une erreur de calcul : deux mois à trois séances par semaine font **24 séances**.

Le développeur penche plutôt pour une limite sur la **fenêtre d'analyse** (par exemple deux mois) que sur la saisie.

## Ce que font les concurrents

| Application | Gratuit | Payant |
|---|---|---|
| **Hevy** | 4 routines, 7 exercices personnalisés, **3 mois d'historique dans les graphiques** | 2,99 $/mois, 23,99 $/an, 74,99 $ à vie |
| **Strong** | Séances illimitées, 3 modèles personnalisés | 4,99 $/mois, 29,99 $/an, 99,99 $ à vie |
| **JEFIT** | Gratuit avec publicité | Elite : 12,99 $/mois ou 69,99 $/an (une source indique 6,99 $/mois) |
| **Fitbod** | Pas de gratuit durable (essai) | Environ 15,99 $/mois ou 95,99 $/an |
| **FitNotes** | Totalement gratuit (Android seulement) | Application « soutien » facultative |
| **Liftosaur** | Saisie et programmes de base | Abonnement ou achat à vie (environ 70 à 100 $) |

**Constats**
- Schéma dominant : **saisie illimitée et gratuite**, avec des limites sur les modèles (3 à 4), les exercices personnalisés et **la profondeur de l'historique des graphiques**.
- Hevy limite précisément la fenêtre d'analyse, à **trois mois**. Une fenêtre de deux mois serait plus restrictive et plus courte que l'horizon de trois mois voulu.
- Le **paiement à vie** existe chez plusieurs acteurs.

## Approches possibles

- **A. Fenêtre glissante d'analyse** : toutes les données restent enregistrées, seules les analyses affichent les N derniers mois. Pas de perte de données ; l'envie de payer naît avec le temps. Risque de perception de données « cachées » : l'historique brut et l'export devraient rester accessibles. La portabilité des données (RGPD) est à vérifier auprès d'une source fiable ; ce n'est pas un avis juridique.
- **B. Essai complet de quelques mois**, puis fenêtre limitée : la durée d'essai correspond au temps nécessaire pour voir la valeur. Plus complexe (dates, statuts).
- **C. Limites par fonctionnalité** : nombre de programmes actifs, de modèles, d'exercices personnalisés.
- **D. Plafond sur le nombre de séances** : déconseillé (il bloque le cœur du produit).

## Orientation (non décidée)

A + C, avec une fenêtre d'au moins **trois mois**, l'export brut toujours gratuit, et des limites sur les programmes et modèles plutôt que sur la saisie. C'est une hypothèse à tester ; aucune donnée de conversion n'existe.

## Conséquences d'architecture

- Les cas d'usage d'analyse prendront une **période en paramètre** : cela permettra de plafonner plus tard sans réécrire.
- Aucun quota ni notion de plan au MVP. Une exception pour le propriétaire se réglera par un indicateur de compte.
- Un modèle économique se décide mieux avec de vrais usages.

## Sources

- [Hevy : avis 2026, limites gratuit et Pro (AI Tools Bakery)](https://aitoolsbakery.com/blog/hevy-review/)
- [Hevy : aide, limites du palier gratuit](https://help.hevyapp.com/hc/en-us/articles/35119778922263)
- [Strong : avis 2026, gratuit contre PRO (Sensai)](https://www.sensai.fit/blog/strong-app-review-2026)
- [JEFIT contre Strong (JEFIT)](https://www.jefit.com/blog/jefit-vs-strong)
- [Tarifs des applications de fitness 2026 (Sensai)](https://www.sensai.fit/blog/fitness-app-pricing-free-tier-comparison)
- [Applications gratuites de suivi comparées (SetGraph)](https://setgraph.app/articles/app-to-track-workouts-free)
