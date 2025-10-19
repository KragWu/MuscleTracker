# MuscleTracker
Application to prepare and store your bodybuilding activities

## Start Application

Execute the following command to start client: 
```shell
make client-angular-dev
```

Execute the following command to start user server: 
```shell
make user-dev
```

### Prerequisite

The application use a postgres database, that instanciate by Podman.

To apply it, we need to start/stop the Podman engine with this following command: 

```shell
podman machine start/stop
```

The command to start the database find itself in the `Makefile`, just for the first starting.

Else, you can start the last container: 
```shell
podman container start <id container>
```

For the first starting of API, we init a database and create the API schema with the following command : 

```sql
CREATE SCHEMA IF NOT EXISTS "<nom du schema pour l'API>";
```

## Develop Application

### Angular

You can start a mock with Wiremock, you need to get the JAR file Wiremock-Standalone, use this link (version 3.13.1 - 17/10/2025):
https://repo1.maven.org/maven2/org/wiremock/wiremock-standalone/3.13.1/wiremock-standalone-3.13.1.jar

Place the JAR in the folder `wiremock-config` and remove the version in the filename.
Then you can start these commands: 

```shell
make start-mock
make client-angular-dev
```

## Tests Application

### Playwright

To execute the playwright test, you need to start all the application components.

Then to execute the following command: 
```shell
cd ./playwright && npx playwright test (--ui)
```