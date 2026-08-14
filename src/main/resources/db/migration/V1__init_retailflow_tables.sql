CREATE TABLE category (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE supplier (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          contact_email VARCHAR(150)
);

CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         sku VARCHAR(50) NOT NULL UNIQUE,
                         name VARCHAR(200) NOT NULL,
                         description TEXT,
                         price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
                         active BOOLEAN NOT NULL DEFAULT TRUE,
                         category_id BIGINT NOT NULL REFERENCES category(id),
                         created_at TIMESTAMP NOT NULL DEFAULT now(),
                         updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE stock_item (
                            id BIGSERIAL PRIMARY KEY,
                            product_id BIGINT NOT NULL UNIQUE REFERENCES product(id),
                            supplier_id BIGINT REFERENCES supplier(id),
                            quantity_on_hand INTEGER NOT NULL DEFAULT 0 CHECK (quantity_on_hand >= 0),
                            quantity_reserved INTEGER NOT NULL DEFAULT 0 CHECK (quantity_reserved >= 0),
                            reorder_threshold INTEGER NOT NULL DEFAULT 5,
                            updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          password_hash VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE cart (
                      id BIGSERIAL PRIMARY KEY,
                      customer_id BIGINT NOT NULL REFERENCES customer(id),
                      status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                      created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE cart_item (
                           id BIGSERIAL PRIMARY KEY,
                           cart_id BIGINT NOT NULL REFERENCES cart(id),
                           product_id BIGINT NOT NULL REFERENCES product(id),
                           quantity INTEGER NOT NULL CHECK (quantity > 0),
                           UNIQUE (cart_id, product_id)
);

CREATE TABLE customer_order (
                                id BIGSERIAL PRIMARY KEY,
                                customer_id BIGINT NOT NULL REFERENCES customer(id),
                                status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                total_amount NUMERIC(10,2) NOT NULL DEFAULT 0,
                                created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE order_item (
                            id BIGSERIAL PRIMARY KEY,
                            order_id BIGINT NOT NULL REFERENCES customer_order(id),
                            product_id BIGINT NOT NULL REFERENCES product(id),
                            quantity INTEGER NOT NULL CHECK (quantity > 0),
                            unit_price_at_sale NUMERIC(10,2) NOT NULL
);

CREATE TABLE payment (
                         id BIGSERIAL PRIMARY KEY,
                         order_id BIGINT NOT NULL UNIQUE REFERENCES customer_order(id),
                         amount NUMERIC(10,2) NOT NULL,
                         method VARCHAR(30) NOT NULL,
                         status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                         transaction_ref VARCHAR(100),
                         created_at TIMESTAMP NOT NULL DEFAULT now()
);