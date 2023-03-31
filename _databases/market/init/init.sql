-- market database init script
create table if not exists item
(id bigserial primary key,
 "name" varchar not null,
    price real
    );

create index if not exists item_name_idx on item(name);

INSERT INTO item
(id,"name",price)
VALUES
(1,'Laptop_Asus',1000.0),
(2,'Laptop_Mac',2000.0),
(3,'PC',900.0);


create type order_status as enum('CREATED', 'PROCESSING', 'SHIPPING', 'DELIVERED');

create table if not exists  order_
(
    id             bigserial
        primary key,
    current_status varchar,
    total_items    integer,
    total_payments real);

INSERT INTO order_
(id,current_status,total_items,total_payments)
VALUES
(1,'CREATED',5,6212.0),
(2,'SHIPPING',3,3311.5),
(3,'DELIVERED',2,1112.0);

create table if not exists payment
(
    id           bigserial
        primary key,
    sum          real      not null,
    payment_date timestamp not null,
    order_id     bigserial
        constraint payment_order_id_fk
            references order_);

INSERT INTO payment
(id,sum,payment_date,order_id)
VALUES
(1,1345.30,'2023-01-04 20:32:59',1),
(2,1245.31,'2023-01-04 21:32:59',2),
(3,4245.50,'2023-01-04 22:32:59',1);