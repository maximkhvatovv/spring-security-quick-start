insert into users(id, email)
values (1, 'maximkhvatovv@yandex.ru'),
       (2, 'bloch@gmail.com'),
       (3, 'gosling@sun.com');

insert into user_password(user_id, password)
values (1, '{noop}qwerty2001'),
       (2, '{noop}effectivejava'),
       (3, '{noop}justjava');

insert into user_authority(id, user_id, authority)
values (DEFAULT, 1, 'ROLE_USER'),
       (DEFAULT, 1, 'ROLE_DB_USER'),
       (DEFAULT, 2, 'ROLE_USER'),
       (DEFAULT, 3, 'ROLE_DB_USER'),
       (DEFAULT, 3, 'ROLE_USER');