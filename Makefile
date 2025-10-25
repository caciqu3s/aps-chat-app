.PHONY: help build up down logs clean restart test

help: ## Show this help message
	@echo 'Usage: make [target]'
	@echo ''
	@echo 'Available targets:'
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  %-15s %s\n", $$1, $$2}'

build: ## Build the Docker images
	docker-compose build

up: ## Start the application and MongoDB
	docker-compose up -d

down: ## Stop the application and MongoDB
	docker-compose down

logs: ## View application logs
	docker-compose logs -f chat-app

logs-all: ## View all logs (app and MongoDB)
	docker-compose logs -f

clean: ## Stop containers and remove volumes
	docker-compose down -v

restart: ## Restart the application
	docker-compose restart chat-app

status: ## Show status of containers
	docker-compose ps

test: ## Run tests in a container
	docker-compose run --rm chat-app gradle test

shell: ## Open a shell in the application container
	docker-compose exec chat-app sh

mongo-shell: ## Open MongoDB shell
	docker-compose exec mongodb mongosh chatapp

