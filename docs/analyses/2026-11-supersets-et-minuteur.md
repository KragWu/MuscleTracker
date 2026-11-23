# Supersets et minuteur

> **Statut : analyse du 2026-11-23. Décisions de planning prises** (voir la fin) ; aucune décision technique définitive. Les comportements d'iOS viennent de forums et de rapports de bogues : **à revérifier sur un vrai téléphone** avant de s'y engager.

## L'idée

Afficher et saisir des **supersets** : des exercices qui s'enchaînent sans repos entre eux, le repos venant après le groupe. Y associer un **minuteur** avec bips : par exemple 3 minutes pour deux exercices d'une série de 10 répétitions, puis un temps de repos.

## Modèle

- Un **bloc** regroupe des exercices d'une séance ; un **superset** est un bloc de deux exercices ; un **tour de bloc** est une série de chaque exercice (voir le [glossaire](../glossaire.md)).
- L'enregistrement **série par série** reste inchangé : chaque série est saisie par exercice, le bloc ne change que l'ordre de présentation (série de A, série de B, repos, puis on recommence).
- Les **dernières valeurs connues** restent par exercice.
- Un bloc chronométré porte un nombre de tours et, facultativement, une durée de travail et une durée de repos.
- Le concept est **réservé** dès maintenant (champ facultatif, sans rupture de données) et implémenté plus tard.

## Le minuteur dans une PWA

Ce que montrent les sources consultées :
- Sur iOS, le JavaScript est **suspendu quand l'écran se verrouille ou que l'application passe en arrière-plan**, y compris une PWA installée. Un minuteur ne se termine donc pas de façon fiable téléphone verrouillé.
- L'API de blocage de la mise en veille (*Wake Lock*) : un rapport WebKit de 2023 indiquait qu'elle ne fonctionnait pas pour une page ajoutée à l'écran d'accueil sur iOS 16.4. Le rapport est marqué résolu, sans version précisée.
- Le **son en arrière-plan** est peu fiable (témoignages d'arrêt de l'audio après quelques dizaines de secondes).
- Les notifications locales ne sont pas disponibles pour une PWA d'après un fil de discussion Apple ; les notifications poussées le sont.
- Rien trouvé sur la vibration ni sur les restrictions du son web sur iOS : **non vérifié**.

**Conséquence** : une PWA peut afficher un minuteur **exact au retour** (calcul à partir de l'**heure de fin enregistrée**, jamais en comptant des ticks) et émettre des bips **tant que l'écran reste allumé**. Les bips téléphone verrouillé en poche relèvent du **client Flutter** (ADR-0002).

## Décisions de planning (2026-11-23)

1. **Lot dédié « Supersets et minuteur » après le MVP.**
2. **Minuteur** : PWA au mieux (écran allumé), bips écran verrouillé avec Flutter.
3. **Concept de bloc réservé** dans l'ADR du lot 5 (T1) et dans l'issue #101 du lot 4.

## Sources

- [JavaScript en arrière-plan sur mobile (Frontend Masters)](https://frontendmasters.com/courses/background-javascript/observing-mobile-background-javascript)
- [Empêcher JavaScript de s'arrêter dans Safari en arrière-plan (forum Apple)](https://developer.apple.com/forums/thread/777860)
- [Audio sur l'écran verrouillé dans une PWA iOS (forum Apple)](https://developer.apple.com/forums/thread/762582)
- [Garder un minuteur actif en arrière-plan (forum Apple)](https://developer.apple.com/forums/thread/687587)
- [Notifications en arrière-plan pour les PWA sur Safari (forum Apple)](https://developer.apple.com/forums/thread/735402)
- [Audio qui s'arrête quand l'application autonome quitte le premier plan (WebKit)](https://bugs.webkit.org/show_bug.cgi?id=198277)
