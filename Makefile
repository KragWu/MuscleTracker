initiate:
	@echo "Hello"

status:
	cd client/ && npm run lint && npm run test && cd .. && git status

lint: 
	cd client/ && npm run lint

test: 
	cd client/ && npm run test

client-dev:
	cd client/ && npm run serve-dev

client-prod: 
	cd client/ && npm run build-prod

user-dev:
	cd userapi/ && mvn clean package && java -jar ./target/userapi-0.0.1-SNAPSHOT.jar

start-bdd:
	podman run --name bdd-muscletracker -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=mU5CLetR4CKEr -e POSTGRES_DB=postgres  -p 5432:5432 -d 43677b39c446

stop-bdd:
	podman container stop bdd-muscletracker