INSERT INTO users (username, password, firstname, lastname, email, phone_number, enabled)
VALUES ('testuser', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'John', 'Doe', 'johndoe@example.com', 3161234, true);

INSERT INTO roles(rolename)
VALUES ('PASSAGIER'), ('BESTUURDER');

INSERT INTO users_roles (users_username, roles_rolename)
VALUES ('testuser', 'BESTUURDER');


-- DEZE KLASSE ERBIJ GEZET EVT WEGHALEN