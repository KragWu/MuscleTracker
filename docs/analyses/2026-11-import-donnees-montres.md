# Import des données de montres : formats et marques

> **Statut : analyse du 2026-11-22. Aucune décision prise** ; l'ADR sur le format d'import sera rédigé quand l'epic « endurance » démarrera, et citera cette note.
> Les informations sur les marques viennent de pages d'aide et de sources tierces, **à revérifier avec de vrais fichiers**. Une seule marque (Suunto) a été testée.

## Contexte

Le développeur veut suivre aussi ses courses, sorties vélo et séances de natation. Le **GPS du téléphone** n'est pas fiable dans une PWA (WebKit ne prend pas en charge l'exécution d'un site en arrière-plan écran éteint sur iOS ; des développeurs signalent des mises à jour de position qui s'arrêtent à l'écran verrouillé, d'autres sources de seconde main affirment le contraire sur Safari, non confirmé). L'**import de fichiers exportés d'une montre** est donc plus fiable, et l'enregistrement en direct reviendrait au client Flutter (ADR-0002).

## Fichiers analysés (montre Suunto)

Les fichiers restent dans `local-data/` (ignoré par Git : tracé GPS, fréquence cardiaque, données de santé). Les valeurs personnelles ne sont volontairement pas reproduites ici : seuls les **ordres de grandeur** et la **structure** comptent.

| | Course | Vélo |
|---|---|---|
| Distance | environ 8 km | environ 15 km |
| Durée (chronomètre) | moins d'une heure | moins d'une heure |
| Dénivelé | quelques dizaines de mètres | quelques dizaines de mètres |
| Fréquence cardiaque, calories | mesurées (minimum, moyenne, maximum) | mesurées (minimum, moyenne, maximum) |
| Tours (FIT) | une dizaine | 2 |
| Points GPS | plus de 2 000 | plus de 2 000 |
| Poids FIT / JSON | 71,5 Ko / 3,1 Mo | 60 Ko / 1,4 Mo |

### FIT

- Format **documenté et standard**. Même structure pour course et vélo : un message de session, des tours, des enregistrements à la seconde (fréquence cardiaque, vitesse, altitude, température, distance, position), des événements.
- La session contient aussi des indicateurs de la montre : charge d'entraînement, effet d'entraînement, temps de récupération, temps dans les zones cardiaques ; en course seulement : foulées, cadence, zones de vitesse, VO2max estimée.
- Unités directes : battements par minute, degrés Celsius, mètres par seconde. Horodatage en heure universelle, avec le décalage local fourni à part.
- Aucun numéro de série de la montre dans le message d'identification.

### JSON Suunto

- **Non documenté** dans ce qui a été trouvé : la page d'aide de Suunto ne mentionne que GPX et FIT. Le format peut changer sans prévenir.
- **43 fois plus lourd** que le FIT pour la course.
- Mêmes 44 champs d'en-tête pour la course et le vélo ; les essentiels (distance, durée, dénivelé, zones cardiaques, VO2max, ressenti) sont identiques au FIT.
- **Unités du système international** : fréquence cardiaque en hertz, température en kelvins, énergie en joules : des conversions sont nécessaires.
- **Uniquement dans le JSON** : âge de forme, fréquence cardiaque maximale personnelle, **limites des zones**, réglages de la montre, objectifs, durée de pause, état de la batterie, **numéro de série**.

### Non vérifié

- **Natation** : longueur de bassin, coups de bras, style (le JSON a des champs prévus, le FIT n'a pas été testé).
- **Puissance et cadence de pédalage** : aucun capteur n'était utilisé pendant la sortie de vélo (réglages `BikePodUsed` et `PowerPodUsed` à faux). Ces champs sont absents du fichier, ce qui ne prouve rien sur leur forme avec un capteur.
- **Nombre de tours** : le JSON a plus de fenêtres que le FIT n'a de tours (11 contre 9 en course, 4 contre 2 en vélo) ; la différence n'a pas été élucidée.

## Export par marque

| Marque | Export d'une séance | Export en masse | Remarques |
|---|---|---|---|
| **Suunto** | GPX et FIT (support officiel) | non étudié | Le JSON obtenu n'est pas documenté |
| **Garmin** | « Export Original » (FIT) depuis la page web de l'activité ; GPX et TCX possibles avec des limites | Outils communautaires ; une source signale en 2026 qu'une protection Cloudflare les a cassés | Page d'aide officielle non trouvée : sources tierces |
| **Apple Watch** | Aucune fonction officielle confirmée ; applications tierces (GPX, TCX, FIT, JSON) | Non vérifié | La Santé (« tout exporter ») produirait un XML et des tracés GPX d'après des connaissances générales, **non vérifié** |
| **Polar** | GPX, TCX, CSV, FIT ou ZIP depuis Polar Flow (multisport : GPX, FIT, TCX) | Export complet du compte (account.polar.com), deux semaines pour télécharger | L'export complet exclut les données calculées |
| **Coros** | Menu « Exporter » : FIT, GPX, TCX, KML (blog tiers) | Training Hub sur ordinateur : FIT ou TCX reçus par e-mail | Pas d'export CSV officiel |
| **Fitbit (Google)** | TCX officiel par activité, **seulement avec GPS** (vide sinon) | Contenu de Google Takeout contradictoire selon les sources | Pas de GPX natif |

Non couvertes : Samsung, Huawei, Amazfit.

## Orientation (non décidée)

1. **FIT** comme format d'import principal (le plus petit dénominateur commun), puis **GPX**, puis **TCX**. Le GPX est plus pauvre (position, heure, fréquence cardiaque).
2. Le **JSON Suunto** n'est pas retenu, sauf besoin précis des limites de zones.
3. Chaque format est un **adaptateur d'import** derrière un même port.
4. **Minimisation** : l'import ne retient qu'un sous-ensemble (résumé, tours, fréquence cardiaque) ; le **tracé** n'est conservé que si l'utilisateur le souhaite, avec export et suppression.
5. Intégration directe avec l'API de Suunto : à étudier plus tard (un communiqué de 2019 mentionne un accès sur demande ; conditions actuelles non vérifiées).

## Import technique : pistes (non décidé)

L'ADR de l'epic « endurance » tranchera. Le décodeur FIT est un **adaptateur** derrière un port d'analyse de fichier : on peut en changer sans toucher au domaine.

**Briques disponibles**

| Brique | État |
|---|---|
| Choix d'un fichier dans la PWA | Champ fichier standard du navigateur |
| Lecture du FIT côté serveur | SDK Java de Garmin sur Maven Central (`com.garmin:fit`) |
| Lecture du FIT côté PWA | SDK JavaScript de Garmin (`@garmin/fitsdk`), utilisable dans un navigateur ; pas de CDN, à intégrer à la construction |
| Futur client Flutter | Aucun SDK Dart confirmé |
| Licence | Le SDK **n'est pas open source** (licence propre à Garmin) ; conditions de redistribution peu claires : **à lire avant adoption** |

**Deux architectures**

- **A. Lecture dans le back-end** : la PWA envoie le fichier ; le serveur le décode **en mémoire**, renvoie un **aperçu** (sport, date, distance, durée, tours) ; l'utilisateur confirme et choisit de garder ou non le tracé ; le serveur conserve le sous-ensemble retenu et jette le fichier. Un seul décodeur pour la PWA et Flutter, validation centralisée ; en contrepartie, le tracé transite par le serveur.
- **B. Lecture dans la PWA** : le navigateur décode et n'envoie que des données structurées. Le tracé reste sur le téléphone sauf choix contraire, et cela fonctionne hors ligne ; mais le serveur doit quand même valider, et un second décodeur serait nécessaire pour Flutter.

**Orientation** : A en premier (simplicité, centralisation), B comme optimisation de confidentialité plus tard. Arbitrage à confirmer dans l'ADR.

**Points de vigilance**
- Fichier non fiable : taille maximale, délai maximal, mémoire bornée, aucun stockage du fichier brut.
- L'« export original » de Garmin peut être un ZIP ou un `.fit.gz` : ouverture avec des limites contre les archives piégées.
- Doublons : importer deux fois le même fichier ne doit pas créer deux séances (empreinte : sport, début, durée, distance).
- Suivi des mises à jour de la bibliothèque (Dependabot).
- Réception d'un fichier partagé depuis l'application de la montre (partage vers une PWA) : non vérifié.

## Fichiers de test : synthétiques plutôt qu'anonymisés

Anonymiser un vrai fichier est fragile : la **forme** du tracé et la **courbe de fréquence cardiaque** restent reconnaissables même après décalage ; un FIT est binaire avec somme de contrôle, donc à réécrire avec un encodeur (la bibliothèque Python utilisée pour l'analyse ne sait que lire) ; des champs personnalisés de la montre (ressenti, temps de récupération, effort de pointe, VO2max estimée) sont des données de santé.

**Orientation** : un **générateur de séances d'endurance synthétiques** dans les tests du back-end (quelques minutes, trois tours, tracé fictif en boucle près d'un lieu imaginaire, date fictive, valeurs plausibles), plus quelques fichiers **volontairement invalides** (tronqué, somme de contrôle fausse, démesuré). Des vrais fichiers, on ne garde que la **structure** documentée ci-dessus. Cela fera partie de la première issue de l'epic « endurance ».

**À obtenir avant l'ADR** : lecture de la licence du SDK ; un fichier de **natation** et un fichier de **vélo avec capteur** de puissance ou de cadence, pour valider les champs.

## Sources

- [Suunto : types de fichiers exportables](https://www.suunto.com/Support/faq-articles/suunto-app/what-type-of-files-can-i-export-from-the-suunto-app/)
- [Suunto : programme de partenaires (2019)](https://www.suunto.com/News/suunto-welcomes-companies-to-join-its-partner-program-a-digital-ecosystem-for-sports-and-active-life/)
- [Garmin : exporter l'original d'une activité (TrainingPeaks)](https://help.trainingpeaks.com/hc/en-us/articles/204070844-How-can-I-export-my-Garmin-Connect-Activities-into-TrainingPeaks-)
- [Garmin : export de natation en FIT (forum Garmin)](https://forums.garmin.com/apps-software/mobile-apps-web/f/garmin-connect-web/135335/export-swim-activities-from-connect-as-fit-file)
- [Apple Watch : application d'export tierce](https://apps.apple.com/app/id6761850094)
- [Polar : exporter des séances individuelles](https://support.polar.com/us-en/export-training-sessions-flow)
- [Polar : télécharger toutes ses données](https://support.polar.com/us-en/how-to-download-all-your-data-from-polar-flow)
- [Coros : export en masse](https://support.coros.com/hc/en-us/articles/33125636125204-Bulk-Export-Historical-Activity-Data)
- [Coros : guide d'export (Gneta)](https://www.gneta.app/blog/export-coros-data-guide)
- [Fitbit : exporter des séances en TCX](https://support.google.com/fitbit/answer/14236615)
- [Google Health API : export TCX](https://developers.google.com/health/reference/rest/v4/users.dataTypes.dataPoints/exportExerciseTcx)
- [Le SDK Java FIT sur Maven Central (forum Garmin)](https://forums.garmin.com/developer/fit-sdk/b/news-announcements/posts/the-fit-java-sdk-is-now-available-from-maven-central)
- [Artefact `com.garmin:fit` (Maven Central)](https://central.sonatype.com/artifact/com.garmin/fit)
- [Mises à jour de la licence du SDK FIT (forum Garmin)](https://forums.garmin.com/developer/fit-sdk/b/news-announcements/posts/fit-sdk-license-agreement-updates)
- [Téléchargement du SDK FIT (Garmin)](https://developer.garmin.com/fit/download)
- [`@garmin/fitsdk` (npm)](https://npmjs.com/package/%40garmin%2Ffitsdk)
- [SDK JavaScript sans Node.js (forum Garmin)](https://forums.garmin.com/developer/fit-sdk/f/discussion/359510/javascript-sdk-without-node-js-npm)
