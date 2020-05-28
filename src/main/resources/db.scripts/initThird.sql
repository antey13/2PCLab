CREATE SCHEMA third;

CREATE TABLE third.third_booking(
   id serial PRIMARY KEY,
   client_name VARCHAR (50) NOT NULL,
   money integer NOT NULL CHECK (money >= 0)
);

INSERT INTO third.third_booking(client_name, money) VALUES
('Person1', 1000),
('Person2', 100);