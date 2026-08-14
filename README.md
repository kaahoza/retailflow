# RetailFlow

A backend platform for retail inventory management, sales processing, and
business intelligence — built to reflect how real retail-tech systems
handle stock, orders, and reporting at scale.

## Overview

RetailFlow lets a retail business manage its product catalog and stock
levels, process customer orders through a cart-and-checkout flow, and
surface operational insights like revenue trends, top-selling products,
and low-stock alerts.

## Features

- **Inventory Management** — products, categories, suppliers, and stock
  levels with reorder threshold tracking
- **Sales & Checkout** — cart management, stock validation, order
  processing, and payment recording
- **Business Intelligence** — revenue reporting, top/slow-moving product
  analysis, and low-stock alerting
- **Concurrency-safe stock handling** — stock is reserved (not deducted)
  at cart-time, and validated again at checkout to prevent overselling

## Tech Stack

- **Language / Framework:** Java 21, Spring Boot (Web, Data JPA, Security, Validation)
- **Database:** PostgreSQL, versioned with Flyway migrations
- **API Docs:** springdoc-openapi (Swagger UI)
- **Testing:** JUnit 5, Mockito, Testcontainers
- **Containerization:** Docker, docker-compose
- **Deployment:** [to be added]

## Architecture

Layered architecture: `Controller → Service → Repository`, with a
clearly separated domain model (`Product`, `StockItem`, `Cart`, `Order`,
`Payment`, etc.) so business rules live in the service layer, not
scattered across controllers.

## Getting Started

\`\`\`bash
git clone https://github.com/<your-username>/retailflow.git
cd retailflow
docker-compose up -d      # starts Postgres
./mvnw spring-boot:run
\`\`\`

API docs available at `http://localhost:8080/swagger-ui.html` once running.

## Status

🚧 In active development. Current phase: **Foundation & Inventory module**.

## Roadmap

- [x] Project scaffold & CI-ready repo structure
- [ ] Inventory module (Product, Category, Supplier, StockItem)
- [ ] Sales module (Cart, Checkout, Order, Payment)
- [ ] Intelligence module (reporting & analytics endpoints)
- [ ] Test coverage & Swagger documentation
- [ ] Live deployment