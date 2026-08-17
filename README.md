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

- **Language / Framework:** Java 21, Spring Boot (Web, Data JPA, Validation)
- **Database:** PostgreSQL, versioned with Flyway migrations
- **API Docs:** springdoc-openapi (Swagger UI) — live at `/swagger-ui.html`
- **Testing:** JUnit 5, AssertJ — 17 passing unit tests covering core
  domain logic (stock reservation, cart rules, order state transitions)
- **Containerization:** Docker, docker-compose
- **Deployment:** [to be added]

## Architecture

Layered architecture: `Controller → Service → Repository`, with a
clearly separated domain model (`Product`, `StockItem`, `Cart`, `Order`,
`Payment`, etc.) so business rules live in the service layer and domain
entities, not scattered across controllers. Key invariants — stock can
never go negative, an order can't be paid twice, cart quantity can't
exceed available stock — are enforced directly on the domain entities
themselves (`Cart.addItem()`, `StockItem.reserve()`, `Order.markPaid()`),
so they can't be bypassed by calling code. These rules are covered by
unit tests in `src/test/java`.

## Getting Started

\`\`\`bash
git clone https://github.com/kaahoza/retailflow.git
cd retailflow
docker-compose up -d      # starts Postgres
./mvnw spring-boot:run
\`\`\`

Interactive API docs available at `http://localhost:8080/swagger-ui.html`
once running — every endpoint can be explored and tested directly from
the browser.

Run the test suite:

\`\`\`bash
./mvnw test
\`\`\`

## Status

✅ Complete. All three pillars (Inventory, Sales, Intelligence) implemented,
tested, documented, and deployed live.

**🔗 Live demo:** https://retailflow-2.onrender.com/swagger-ui.html

## Roadmap

- [x] Project scaffold & CI-ready repo structure
- [x] Inventory module (Product, Category, Supplier, StockItem)
- [x] Sales module (Cart, Checkout, Order, Payment)
- [x] Intelligence module (reporting & analytics endpoints)
- [x] Test coverage & Swagger documentation
- [X] Live deployment