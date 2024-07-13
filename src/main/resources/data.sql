INSERT INTO client_grade (`client_grade_name`, `client_policy_boundry`, `point_policy_id`)
VALUES ('common', 0, 1),
       ('royal', 100000, 2),
       ('gold', 200000, 3),
       ('platinum', 300000, 4);

INSERT INTO `role` (`role_name`)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');