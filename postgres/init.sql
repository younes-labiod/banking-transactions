CREATE DATABASE bank;
\c bank

CREATE TABLE IF not exists BK_Transaction (
	id SERIAL PRIMARY KEY,
	currency varchar(3) not null,
	amount BIGINT NOT NULL,
	sourceAccountId varchar(100) not null,
    createdAt timestamp not null
);

INSERT INTO BK_Transaction (currency, amount, sourceAccountId, createdAt ) VALUES ('AED', 20000, 'account123456789', Now());