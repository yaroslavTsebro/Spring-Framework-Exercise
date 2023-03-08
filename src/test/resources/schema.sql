drop table if exists gift_certificate CASCADE;
create table gift_certificate
(
    id               serial                                         not null
        constraint gift_certificate_pk primary key,
    name             varchar(128)                                   not null,
    description      varchar(512),
    price            numeric(12, 2) check (price > 0)               not null,
    duration         integer                                        not null,
    create_date      timestamp default (now() at time zone 'UTC+2') not null,
    last_update_date timestamp default (now() at time zone 'UTC+2') not null,
    UNIQUE (name)
);

drop table if exists tag CASCADE;
create table tag
(
    id   serial
        constraint tag_pk
            primary key,
    name varchar(128) not null,
    UNIQUE (name)
);

DROP TABLE if exists gift_certificate_to_tag CASCADE;
CREATE TABLE gift_certificate_to_tag
(
    id                  serial not null
        constraint gift_certificate_to_tag_pk primary key,
    gift_certificate_id int REFERENCES gift_certificate (id) ON UPDATE CASCADE ON DELETE CASCADE,
    tag_id              int REFERENCES tag (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT gift_certificate_tag_pk PRIMARY KEY (id)
);