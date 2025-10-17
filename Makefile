VERSION_API_USER=0.0.1-SNAPSHOT

initiate:
	@echo "Hello"

status-react:
	cd client-react/ && npm run lint && npm run test && cd .. && git status

lint-react: 
	cd client-react/ && npm run lint

test-react: 
	cd client-react/ && npm run test

client-react-dev:
	cd client-react/ && npm run serve-dev

client-react-prod: 
	cd client-react/ && npm run build-prod

status-angular:
	cd client-angular/ && npm run test && cd .. && git status

test-angular: 
	cd client-angular/ && npm run test

client-angular-dev:
	cd client-angular/ && npm run start

user-test:
	export TESTCONTAINERS_RYUK_DISABLED=true && cd userapi/ && mvn clean test

user-dev:
	export TESTCONTAINERS_RYUK_DISABLED=true && cd userapi/ && mvn clean package && java -jar ./target/userapi-${VERSION_API_USER}.jar

start-bdd:
	podman run --name bdd-muscletracker -e POSTGRES_USER= -e POSTGRES_PASSWORD= -e POSTGRES_DB=postgres  -p 5432:5432 -d id

stop-bdd:
	podman container stop bdd-muscletracker

start-mock:
	cd wiremock-config/ && java -jar wiremock-standalone.jar --port 8080 --verbose --root-dir .
