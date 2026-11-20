# ADR-0004 : Kotlin et Spring Boot MVC pour le back-end

- **Statut** : Acceptée
- **Date** : 2026-11-20

## Contexte

Le back-end doit être **fiable, sécurisé et performant**, et se prêter au TDD et à l'architecture hexagonale. Le développeur connaît Java et Spring Boot. L'ancien projet utilisait Spring WebFlux (programmation réactive).

## Options étudiées

Comparaison qualitative (aucune mesure de performance : la charge visée, quelques centaines d'utilisateurs, est très inférieure à la capacité de chacune des options) :

| Option | Points forts | Points faibles |
|---|---|---|
| **Java + Spring Boot** | Écosystème immense, sécurité éprouvée, Testcontainers, ArchUnit | Verbeux |
| **Kotlin + Spring Boot** | Même écosystème, nullabilité sûre, code concis (`data class`) | Nouveauté légère |
| C# + ASP.NET Core | Très bon, riche | Changement d'écosystème sans gain décisif ici |
| Go | Léger, démarrage rapide | Moins de cadres intégrés, TDD hexagonal plus manuel |
| TypeScript + NestJS | Même langage que le front | Surface d'attaque npm, typage seulement à la compilation |
| Rust + Axum | Meilleure performance et sûreté mémoire | Coût d'apprentissage élevé, gain invisible à cette échelle |

Pour le modèle de programmation : **WebFlux** (réactif) ou **Spring MVC** classique avec **threads virtuels** (Java 21 et suivants).

## Décision

- Langage : **Kotlin**.
- Cadre : **Spring Boot MVC**, avec les **threads virtuels**. WebFlux n'est pas repris : le MVC est plus simple à tester et à lire, et suffisant pour la charge visée.
- Tests : JUnit, MockK, Testcontainers ; **ArchUnit** pour vérifier les frontières de l'architecture hexagonale.

## Conséquences

- On garde l'écosystème Java connu, avec une petite nouveauté (Kotlin) pour répondre à la curiosité du développeur.
- Le code d'accès aux données est bloquant, ce qui simplifie le raisonnement et les tests.
- À réexaminer si une charge très élevée ou des flux longs (diffusion en continu) apparaissent.

## Pour aller plus loin

- ADR-0005 (architecture), ADR-0006 (persistance).
