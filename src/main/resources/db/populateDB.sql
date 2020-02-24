DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user1@yandex.ru', 'password1'),
       ('User2', 'user2@yandex.ru', 'password2'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_USER', 100001),
       ('ROLE_ADMIN', 100002);

INSERT INTO meals(user_id, date_time, description, calories)
VALUES (100000, '2020.01.30T14:00', 'Обед', 1000),
       (100000, '2020.01.30T20:00', 'Ужин', 500),
       (100000, '2020.01.31T00:00', 'Еда на граничное значение', 100),
       (100000, '2020.01.31T07:00', 'Завтрак', 1000),
       (100000, '2020.01.31T14:00', 'Обед', 500),
       (100000, '2020.01.31T20:00', 'Ужин', 410),
       (100001, '2020.01.30T07:00', 'Завтрак', 500),
       (100001, '2020.01.30T14:00', 'Обед', 1000)
;
