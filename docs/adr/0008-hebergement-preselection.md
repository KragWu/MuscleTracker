# ADR-0008 : Hébergement (présélection)

- **Statut** : Proposée (à confirmer au premier déploiement)
- **Date** : 2026-11-20

## Contexte

Le premier déploiement est prévu **dès le MVP**. Contraintes exprimées :
- budget d'au plus **20 à 25 €/mois** quand l'application aura des centaines d'utilisateurs (et moins avant) ;
- hébergement en **Europe** d'abord, extensible au monde ;
- **pas d'administration de serveur** (pas de VPS à entretenir) : le temps manque ;
- trois éléments à héberger : la PWA (fichiers statiques), le back-end (conteneur) et PostgreSQL.

## Options étudiées

Prix issus de sources tierces consultées le 2026-11-20, souvent contradictoires : **à reconfirmer sur les sites officiels le jour du déploiement**.

| Option | Coût indicatif | Maintenance | Remarque |
|---|---|---|---|
| Render (gratuit) | 0 € | Faible | Mise en veille, démarrage à froid d'environ une minute, base gratuite expirant après 30 jours : inutilisable en production |
| **Render (payant), région européenne** | environ 7 $/mois le service web, autant pour PostgreSQL | Faible | Système et HTTPS gérés |
| Railway | Plus de palier gratuit | Faible | Facturation à l'usage |
| Fly.io | à partir de 2,19 $/mois la plus petite machine, PostgreSQL géré à partir de 38 $/mois | Moyenne | Petite machine probablement trop juste pour une JVM |
| VPS Hetzner ou OVHcloud | environ 4 à 8 $/mois | **Élevée** | Écarté : incompatible avec la contrainte de temps (système, mises à jour et sauvegardes à gérer soi-même) |
| Google Cloud (Cloud Run, Cloud SQL), AWS | Variable | Moyenne à élevée | Excellents mais surdimensionnés, risque de facture imprévue, gestion des droits plus lourde |

Pour la PWA : **Cloudflare Pages** (bande passante illimitée selon plusieurs sources), GitHub Pages ou Netlify.

## Décision (présélection)

- **PWA** : Cloudflare Pages.
- **Back-end et PostgreSQL** : une plateforme gérée payante avec une région européenne (présélection : Render).
- **Authentification** : voir ADR-0007.
- **Développement du MVP** : entièrement en local (Docker Compose, CLI Supabase), sans hébergement.

Cet ADR sera **confirmé ou remplacé** au premier déploiement, après vérification des prix.

## Conséquences

- Aucune administration de serveur ; coût estimé d'environ 14 $/mois avec un back-end et une base payants, dans le budget.
- Le back-end est un **conteneur** : le passer sur une autre plateforme (Cloud Run, AWS, VPS) reste simple.
- Une base gérée doit avoir des **sauvegardes** : à vérifier avant la mise en production.
- La conformité RGPD impose de choisir des régions européennes pour la base et le back-end.

## Pour aller plus loin

- ADR-0005 (monolithe sans état, répliquable), ADR-0007 (authentification).
