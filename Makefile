.PHONY: start stop test demo build logs

start: ## Build images and start the full stack
	docker compose up --build -d

stop: ## Tear down the stack
	docker compose down

logs: ## Tail logs from ledger-app and ledger-consumer
	docker compose logs -f ledger-app ledger-consumer

build: ## Build all Maven modules
	./mvnw clean install

test: ## Run unit + integration + ArchUnit tests across the reactor
	./mvnw test

demo: ## TODO: scripted demo sequence (transfer, duplicate key, concurrency, chaos toggle)
	@echo "TODO: implement demo script (see docs/ARCHITECTURE.md)"
