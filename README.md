# MuscleTracker
Application to prepare and store your bodybuilding activities

## Start Application

Execute the following command to start client: `make client-angular-dev`

Execute the following command to start user server: `make user-dev`

### Pré-requis

L'application utilise une base de données postgres, qu'on instancie avec podman.

Il faudra donc penser à démarrer/éteindre l'intance podman via la commande suivante : 

```
podman machine start/stop
```

La commande pour démarrer la BDD se trouve dans le makefile, lors du premier démarrage.
Sinon, vous pouvez relancer votre ancien container : `podman container start <id container>`

For the first starting of API, we init a database and create the API schema with the following command : 

```
CREATE SCHEMA IF NOT EXISTS "<nom du schema pour l'API>";
```
