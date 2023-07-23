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











