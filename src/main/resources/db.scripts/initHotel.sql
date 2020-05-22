CREATE SCHEMA hotel;

CREATE TABLE hotel.hotel_booking(
   id serial PRIMARY KEY,
   client_name VARCHAR (50) NOT NULL,
   hotel_name VARCHAR (50) NOT NULL,
   arrival_date DATE NOT NULL,
   departure_date DATE NOT NULL
);

INSERT INTO hotel.hotel_booking(client_name, hotel_name, arrival_date, departure_date) VALUES
('Person', 'Hotel', '1998-09-01', '1999-01-01'),
('PersonW', 'W_Hotel', '1978-02-05', '1979-01-01'),
('PersonM', 'M_Hotel', '1975-01-01', '1979-01-01');