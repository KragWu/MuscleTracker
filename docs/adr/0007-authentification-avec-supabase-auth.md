# ADR-0007 : Authentification déléguée à Supabase Auth

- **Statut** : Acceptée (choix de production à confirmer, voir ci-dessous)
- **Date** : 2026-11-20

## Contexte

L'application est multi-utilisateurs : chacun a ses programmes et son historique, la bibliothèque d'exercices est commune. Écrire son propre système de mots de passe est un risque de sécurité inutile. Le développeur est **seul** et ne peut pas assurer une maintenance mensuelle ni une astreinte de sécurité. L'ancien projet avait commencé une migration vers Keycloak.

Principe commun : le back-end est un *resource server* qui valide des **jetons JWT** (OIDC) ; les clients utilisent le flux OIDC avec PKCE.

## Options étudiées

Les données ci-dessous viennent de sources tierces consultées le 2026-11-20, parfois contradictoires. **Elles sont à reconfirmer sur les pages officielles avant tout engagement.**

| Option | Points forts | Points faibles |
|---|---|---|
| **Keycloak** (auto-hébergé) | Standard, complet, sans dépendance à un tiers | Mises à jour fréquentes (une version 26.8.2 avec 33 correctifs de sécurité début novembre 2026), hébergement lourd, la rapidité de correction dépend de notre réaction |
| Spring Authorization Server | Léger, pédagogique | Parcours de compte (inscription, réinitialisation, MFA) à écrire soi-même : risque |
| Service géré : Auth0, Clerk | Peu de maintenance, gros paliers gratuits | Dépendance forte à l'éditeur |
| Firebase Auth | Géré par Google, palier gratuit de 50 000 utilisateurs actifs | Enferme davantage |
| **Supabase Auth** | Géré, palier gratuit (50 000 utilisateurs actifs), code ouvert (MIT) donc auto-hébergeable | Plan gratuit : pas de sauvegarde automatique, pause après une semaine d'inactivité, 2 projets actifs ; ce n'est qu'une partie d'une plateforme plus large |

Des CVE existent chez tous les fournisseurs (par exemple CVE-2026-31813 sur Supabase Auth). Avec un service géré, l'éditeur déploie les correctifs ; avec l'auto-hébergement, c'est à nous de les appliquer.

## Décision

- **Supabase Auth** est le fournisseur d'identité retenu.
- **Développement et tests d'intégration** : pile locale via la **CLI Supabase**. Les tests unitaires n'ont pas besoin de fournisseur : ils utilisent des jetons signés en test.
- **Production** : le choix exact (Supabase hébergé, plan payant, auto-hébergement ou Firebase) est **différé au premier déploiement** et fera l'objet d'un ADR de confirmation.
- Le back-end ne dépend que d'un **jeton OIDC standard** : l'authentification est un adaptateur derrière un port.
- **Table d'utilisateurs interne** : le domaine n'utilise jamais l'identifiant du fournisseur comme clé. Une table de correspondance relie l'identifiant interne au sujet (`sub`) du jeton. Changer de fournisseur ne touche alors pas aux données métier.

## Conséquences

- Pas de maintenance de sécurité mensuelle côté fournisseur d'identité en production gérée.
- Migration possible entre fournisseurs (Firebase vers Supabase documenté ; sens inverse plausible via bcrypt), au prix d'un changement d'identifiants et de sessions invalidées, absorbé par la table de correspondance.
- Points à vérifier par un petit essai avant de confirmer : validation des jetons côté Spring (clés de signature asymétriques via JWKS plutôt qu'un secret partagé) et politique de sauvegarde des comptes.
- Risque assumé : pause d'un projet gratuit inactif, absence de sauvegarde automatique ; à traiter par un plan payant ou des exports planifiés en production.

## Pour aller plus loin

- [Migrer de Firebase Auth vers Supabase](https://supabase.com/docs/guides/platform/migrating-to-supabase/firebase-auth)
- [Tarifs Supabase](https://supabase.com/pricing)
- [Mise en pause des projets Supabase](https://supabase.com/docs/guides/platform/free-project-pausing)
- [CVE-2026-31813](https://nvd.nist.gov/vuln/detail/CVE-2026-31813)
- ADR-0005 (architecture), ADR-0008 (hébergement).
