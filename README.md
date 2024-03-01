# MuscleTracker
Application to prepare and store your bodybuilding activities

## Start Application

Execute the following command to start client server: `make client-dev`

### Pré-requis

L'application utilise une base de données postgres, qu'on instancie avec podman.

Il faudra donc penser à démarrer/éteindre l'intance podman via la commande suivante : 

```
podman machin start/stop
```

La commande pour démarrer la BDD se trouve dans le makefile, lors du premier démarrage.
Sinon, vous pouvez relancer votre ancien container : `podman container start 2adee7de3f94`
