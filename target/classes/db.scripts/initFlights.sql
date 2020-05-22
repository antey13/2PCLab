CREATE SCHEMA fly;

CREATE TABLE fly.fly_booking(
   id serial PRIMARY KEY,
   client_name VARCHAR (50) NOT NULL,
   fly_number VARCHAR (50) NOT NULL,
   arrival_place VARCHAR (50) NOT NULL,
   departure_place VARCHAR (50) NOT NULL,
   arrival_date DATE NOT NULL
);

INSERT INTO fly.fly_booking(client_name, fly_number, arrival_place, departure_place, arrival_date) VALUES
('Person', 'DD 111', 'from1', 'to1', '1999-01-01'),
('PersonW', 'DD 112', 'from2', 'to', '1979-01-01'),
('PersonM', 'DD 121', 'from3', 'to3', '1979-01-01');
