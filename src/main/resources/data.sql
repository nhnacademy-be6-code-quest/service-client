INSERT INTO CLIENT_GRADE (`CLIENT_GRADE_NAME`, `CLIENT_POLICY_BOUNDARY`, `RATE`)
VALUES ('common', 0, 1),
       ('royal', 100000, 2),
       ('gold', 200000, 3),
       ('platinum', 300000, 4);

INSERT INTO `role` (`client_role_name`)
VALUES (ROLE_ADMIN, ROLE_USER, NON_USER)