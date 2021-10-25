INSERT INTO certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('Cinema', 'Very good and not expensive', 100.1, 1, '2019-01-01 00:00:00', '2019-01-01 00:00:00');
INSERT INTO certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('Dance Studio', 'Best studio in the city', 500, 1, '2019-01-01 00:00:00', '2020-01-01 12:10:00');
INSERT INTO certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('Travelling', 'You will like it', 1000, 5, '2015-01-12 11:00:00', '2021-01-01 20:00:00');
INSERT INTO certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('Museum', 'It is nice', 75, 2, '2018-05-16 12:00:00', '2019-01-01 21:00:00');

INSERT INTO tags (name)
VALUES ('home');
INSERT INTO tags (name)
VALUES ('school');
INSERT INTO tags (name)
VALUES ('work');
INSERT INTO tags (name)
VALUES ('holiday');

INSERT INTO certificate_tags(certificate_id, tag_id)
VALUES (1, 1);
INSERT INTO certificate_tags(certificate_id, tag_id)
VALUES (2, 3);
INSERT INTO certificate_tags(certificate_id, tag_id)
VALUES (1, 2);
INSERT INTO certificate_tags(certificate_id, tag_id)
VALUES (2, 2);

INSERT INTO users(id,name)
VALUES (1,'Oleg');
INSERT INTO users(id,name)
VALUES (2,'Gleb');
INSERT INTO users(id,name)
VALUES (3,'Ivan');

INSERT INTO certificate_orders(create_date, price, user_id,
                                   certificate_id)
VALUES ('2019-01-01 21:00:00', 1000, 1, 2);
INSERT INTO certificate_orders(create_date, price, user_id,
                               certificate_id)
VALUES ('2021-01-01 20:00:00', 400, 1, 1);
INSERT INTO certificate_orders(create_date, price, user_id,
                               certificate_id)
VALUES ('2020-08-02 12:00:00', 100, 2, 3);
INSERT INTO certificate_orders(create_date, price, user_id,
                               certificate_id)
VALUES ('2018-05-16 12:00:00', 1500, 3, 2);
