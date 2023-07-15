INSERT INTO rides (id, pick_up_location, destination, route, add_ride_info, departure_date_time, price_per_person, pax, total_rit_price, available_spots, automatic_acceptance, eta, driver_username, pick_up_address, destination_address)
VALUES
    (105, 'Amsterdam', 'Utrecht', 'A2', 'No pets allowed', '2023-08-01T14:00', 12.5, 1, 12.5, 3, true, '14:45', 'Tim', 'Keizersgracht 123', 'Neude 45'),
    (106, 'Amsterdam', 'Utrecht', 'A2', 'Air-conditioned car', '2023-08-01T15:00', 7.5, 1, 7.5, 3, true, '15:45', 'Anna', 'Keizersgracht 123', 'Neude 45'),
    (107, 'Amsterdam', 'Utrecht', 'A2', 'Pickup from home available', '2023-08-01T16:15', 5.0, 1, 5.0, 3, true, '17:00', 'John', 'Keizersgracht 123', 'Neude 45'),
    (108, 'Rotterdam', 'Den Haag', 'A13', 'No smoking allowed', '2023-08-08T17:00', 8.75, 1, 0.0, 3, true, '17:45', 'Sophie', 'Witte de Withstraat 67', 'Buitenhof 88'),
    (109, 'Rotterdam', 'Den Haag', 'A13', 'Pets allowed', '2023-08-08T18:30', 3.0, 1, 3.0, 3, true, '19:00', 'Hugo', 'Witte de Withstraat 67', 'Buitenhof 88'),
    (110, 'Rotterdam', 'Den Haag', 'A13', 'No pets allowed', '2023-08-08T18:00', 12.5, 2, 25.0, 5, true, '18:45', 'Emily', 'Witte de Withstraat 67', 'Buitenhof 88'),
    (111, 'Groningen', 'Leeuwarden', 'A7', 'Air-conditioned car', '2023-08-10T19:30', 7.5, 2, 15.0, 5, true, '20:15', 'Max', 'Grote Markt 22', 'Stationsplein 1'),
    (112, 'Groningen', 'Leeuwarden', 'A7', 'Pickup from home available', '2023-08-10T20:15', 5.0, 2, 10.0, 5, true, '21:00', 'Oliver', 'Grote Markt 22', 'Stationsplein 1'),
    (113, 'Groningen', 'Leeuwarden', 'A7', 'No smoking allowed', '2023-08-10T21:00', 8.75, 1, 8.75, 3, true, '21:45', 'Amelia', 'Grote Markt 22', 'Stationsplein 1'),
    (114, 'Eindhoven', 'Tilburg', 'A58', 'Pets allowed', '2023-08-15T22:30', 3.0, 1, 3.0, 3, true, '23:00', 'Liam', 'Station CS', 'Station CS'),
    (115, 'Eindhoven', 'Tilburg', 'A58', 'No pets allowed', '2023-08-15T09:00', 12.5, 1, 12.5, 3, true, '09:45', 'Noah', 'Station CS', 'Station CS'),
    (116, 'Eindhoven', 'Tilburg', 'A58', 'Air-conditioned car', '2023-08-15T10:30', 7.5, 1, 7.5, 3, true, '11:15', 'Ava', 'Station CS', 'Station CS'),
    (117, 'Maastricht', 'Heerlen', 'A79', 'Pickup from home available', '2023-08-20T12:15', 5.0, 2, 10.0, 5, true, '13:00', 'Lucas', 'Vrijthof 11', 'Spoorlaan 55'),
    (118, 'Maastricht', 'Heerlen', 'A79', 'No smoking allowed', '2023-08-20T14:00', 8.75, 2, 17.5, 5, true, '14:45', 'Ella', 'Vrijthof 11', 'Spoorlaan 55'),
    (119, 'Maastricht', 'Heerlen', 'A79', 'Pets allowed', '2023-08-20T15:30', 3.0, 0, 0.0, 3, true, '16:00', 'Mason', 'Vrijthof 11', 'Spoorlaan 55');


INSERT INTO users (username, password, firstname, lastname, email, phone_number, enabled)
VALUES ('Cor', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'Cornelis', 'Brinkman', 'corbrinkman@example.com', 316883193, true),
       ('Joe', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'Joel', 'Verbeek', 'joeverbeek@example.com', 316983729, true),
       ('Livia', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'Olivia', 'Ho', 'olivia@example.com', 316736277, true),
       ('Eline', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'Els', 'Horst', 'els@example.com', 316121123, true);

INSERT INTO roles(rolename)
VALUES ('PASSAGIER'), ('BESTUURDER');

INSERT INTO users_roles (users_username, roles_rolename)
VALUES ('Cor', 'BESTUURDER'),
       ('Joe', 'PASSAGIER'),
       ('Livia', 'PASSAGIER'),
       ('Eline', 'BESTUURDER');











