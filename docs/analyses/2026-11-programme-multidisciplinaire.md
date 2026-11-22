# Programme multidisciplinaire

> **Statut : réflexion du 2026-11-22. Aucune décision prise, hors MVP.**

## L'idée

Un **programme** est une suite de séances dans le temps, avec une durée, des objectifs de performance (par exercice ou par discipline) et un nombre théorique de séances par semaine, qui se répète jusqu'à la fin du programme. L'utilisateur réalise chaque séance le jour qui lui convient et peut **annuler** une séance s'il ne peut pas tenir son rythme.

Question posée : un programme peut-il **mêler** course à pied, natation, cyclisme et renforcement musculaire ? Réponse : oui, c'est cohérent avec la définition, sous réserve des points ci-dessous.

## Ce que cela implique

- **Une séance est polymorphe.** Elle a des attributs communs (date, état prévue, réalisée ou annulée, ressenti, notes) et un contenu propre à sa discipline : séries, répétitions et charge pour la musculation ; distance, durée, allure ou fréquence cardiaque pour l'endurance ; longueur de bassin et coups de bras pour la natation.
- **Les objectifs diffèrent par discipline** : une charge à atteindre en squat, une allure ou une distance en course. Un programme porte des objectifs de plusieurs natures.
- **Comparer des disciplines** demande une mesure commune. La référence classique est la **charge perçue de séance** (durée multipliée par l'effort perçu, ou RPE). Les montres fournissent leurs propres indicateurs de charge, non comparables à la musculation. Rien n'est à construire maintenant.
- **Modèles de séance** : séances types à intégrer dans un programme. Leur utilité est à vérifier ; la **duplication** (MVP) couvre déjà la plupart du besoin.
- **Contenu des séances d'endurance** : voir [l'analyse de l'import des données de montres](2026-11-import-donnees-montres.md).

## Ordre proposé

1. MVP : séances de musculation (préparer, exécuter, historique, duplication).
2. Programmes de musculation.
3. Import de séances d'endurance (FIT d'abord).
4. Programme multidisciplinaire.

Chaque étape reste utilisable seule. Le vocabulaire est réservé dans le [glossaire](../glossaire.md) et la découpe en modules est proposée dans l'[ADR-0011](../adr/0011-modules-et-vocabulaire-metier.md).

## Points à traiter plus tard

- États d'une séance et règle d'annulation.
- Recalcul du programme quand une séance est annulée ou décalée.
- Mesure commune de charge entre disciplines (RPE).
- Données de localisation et de santé : minimisation, export, suppression, information de l'utilisateur.
