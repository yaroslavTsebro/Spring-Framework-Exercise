INSERT INTO tag (name)
VALUES ('tag1'),
       ('tag2'),
       ('tag3');

INSERT INTO gift_certificate
(name, description, price, duration, create_date, last_update_date)
VALUES ('certificate1', 'description1', 1, 1, '2023-03-08T12:28:17', '2023-03-08T12:28:17'),
       ('certificate2', 'description2', 2, 2, '2023-03-08T12:28:17', '2023-03-08T12:28:17');

INSERT INTO gift_certificate_to_tag
(gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3);
