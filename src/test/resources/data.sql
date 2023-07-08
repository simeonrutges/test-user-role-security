INSERT INTO users (username, password, firstname, lastname, email, phone_number, enabled)
VALUES ('testUser', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'John', 'Doe', 'johndoe@example.com', 3161234, true);

INSERT INTO roles(rolename)
VALUES ('PASSAGIER'), ('BESTUURDER');

INSERT INTO users_roles (users_username, roles_rolename)
VALUES ('testUser', 'BESTUURDER');

INSERT INTO cars (id, model, brand, license_plate)
VALUES (1, 'Capture', 'Renault', '8HHG-98');

INSERT INTO cars (id, model, brand, license_plate)
VALUES (2, 'Megane', 'Renault', '7GGH-77');

-- DEZE KLASSE ERBIJ GEZET EVT WEGHALEN