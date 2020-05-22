CREATE SCHEMA money;

CREATE TABLE money.money(
   id serial PRIMARY KEY,
   client_name VARCHAR (50),
   amount INT NOT NULL CHECK (amount > 0)
);

INSERT INTO money.money(client_name, amount) VALUES
('Person', '1000')
