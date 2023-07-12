

-- INSERT INTO rides (id, pick_up_location, destination, route, add_ride_info, departure_date, departure_date_time, departure_time, price_per_person, pax, total_rit_price, available_spots, automatic_acceptance, eta)
-- VALUES
--     (100, 'Amsterdam', 'Utrecht', 'A2', 'No pets allowed', NULL, '2023-06-01T08:00', NULL, 12.5, 3, 37.5, 2, true, '08:30'),
--     (101, 'Rotterdam', 'Den Haag', 'A13', 'Air-conditioned car', NULL, '2023-06-02T09:30', NULL, 7.5, 4, 30.0, 1, false, '10:00'),
--     (102, 'Eindhoven', 'Tilburg', 'A58', 'Pickup from home available', NULL, '2023-06-03T10:15', NULL, 5.0, 2, 10.0, 3, true, '11:00'),
--     (103, 'Groningen', 'Leeuwarden', 'A7', 'No smoking allowed', NULL, '2023-06-04T11:00', NULL, 8.75, 1, 8.75, 4, false, '11:45'),
--     (104, 'Maastricht', 'Heerlen', 'A79', 'Pets allowed', NULL, '2023-06-05T12:30', NULL, 3.0, 3, 9.0, 5, true, '13:00'),
--     (105, 'Enschede', 'Hengelo', 'A35', 'No talking allowed', NULL, '2023-06-06T14:00', NULL, 6.25, 2, 12.50, 2, false, '14:30'),
--     (106, 'Arnhem', 'Nijmegen', 'A325', 'Free wifi available', NULL, '2023-06-07T15:45', NULL, 9.0, 4, 36.0, 1, true, '16:15'),
--     (107, 'Zwolle', 'Apeldoorn', 'A50', 'Snacks provided', NULL, '2023-06-08T17:30', NULL, 4.5, 3, 13.5, 3, false, '18:00'),
--     (108, 'Breda', 'Tilburg', 'A58', 'Music allowed', NULL, '2023-06-09T19:00', NULL, 7.0, 2, 14.0, 4, true, '19:45'),
--     (109, 'Den Bosch', 'Eindhoven', 'A2', 'No eating allowed', NULL, '2023-05-06T21:15', NULL, 6.0, 3, 18.0, 2, true, '21:45'),
--     (110, 'Breukelen', 'Amsterdam', 'A2', 'No pets allowed', NULL, '2023-05-07T08:30', NULL, 6.0, 4, 24.0, 2, true, '09:00'),
--     (111, 'Breukelen', 'Amsterdam', 'A2', 'Air-conditioned car', NULL, '2023-05-09T12:30', NULL, 5.0, 3, 15.0, 2, false, '13:00'),
--     (112, 'Breukelen', 'Amsterdam', 'A2', 'Pickup from home available', NULL, '2023-05-09T11:15', NULL, 12.0, 1, 12.0, 1, true, '12:00'),
--     (113, 'Breukelen', 'Amsterdam', 'A2', 'No smoking allowed', NULL, '2023-05-09T12:30', NULL, 5.0, 3, 15.0, 2, false, '13:00'),
--     (114, 'Breukelen', 'Amsterdam', 'A2', 'Pets allowed', NULL, '2023-05-09T14:00', NULL, 9.0, 2, 18.0, 4, true, '14:30'),
--     (115, 'Breukelen', 'Amsterdam', 'A2', 'No talking allowed', NULL, '2023-08-10T16:00', NULL, 7.75, 1, 7.75, 3, false, '16:45'),
--     (116, 'Breukelen', 'Amsterdam', 'A2', 'Free wifi available', NULL, '2023-08-11T17:30', NULL, 6.0, 4, 24.0, 2, true, '18:00'),
--     (117, 'Breukelen', 'Amsterdam', 'A2', 'Snacks provided', NULL, '2023-08-12T19:00', NULL, 4.5, 3, 13.5, 3, false, '19:30');
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
-- ('Jack', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'John', 'Doe', 'johndoe@example.com', 0688319330, true),

INSERT INTO roles(rolename)
VALUES ('PASSAGIER'), ('BESTUURDER');

INSERT INTO users_roles (users_username, roles_rolename)
VALUES ('Cor', 'BESTUURDER'),
       ('Joe', 'PASSAGIER'),
       ('Livia', 'PASSAGIER'),
       ('Eline', 'BESTUURDER');











