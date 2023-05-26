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