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

### Keycloak (Auth)

We use Keycloak as the OpenID Connect provider to secure the application. To start a development Keycloak instance (Podman) run:

```shell
make start-keycloak
```

This will run Keycloak on `http://localhost:8081` with admin user `admin` / `admin` (development only). Create a realm named `muscletracker`, add a client (public) with redirect URI `http://localhost:4200/*` and enable standard flow. Create users or import a realm configuration later.

To stop Keycloak:

```shell
make stop-keycloak
```

When Keycloak is running, update `userapi/src/main/resources/application.yml` to point to your realm issuer URI (example: `http://localhost:8081/realms/muscletracker`).

Playwright integration notes
---------------------------
For end-to-end tests with Playwright you can perform a programmatic login against Keycloak to obtain an access token and set it in the browser context (localStorage) before tests run. High-level steps:

1. Call Keycloak token endpoint `http://localhost:8081/realms/muscletracker/protocol/openid-connect/token` with client credentials (or Resource Owner Password grant for tests) to obtain an access token.
2. In your Playwright test, use `page.addInitScript` or `page.evaluate` to set the token in `localStorage` so the app sees the authenticated user.

Example (pseudo):

```ts
// fetch token
const res = await fetch('http://localhost:8081/realms/muscletracker/protocol/openid-connect/token', {
	method: 'POST',
	headers: { 'content-type': 'application/x-www-form-urlencoded' },
	body: new URLSearchParams({ grant_type: 'password', client_id: 'muscletracker-client', username: 'test', password: 'test' })
});
const data = await res.json();
await page.addInitScript(token => localStorage.setItem('kc_token', token), data.access_token);
```

Adjust the storage key according to the Angular Keycloak integration you implement.
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