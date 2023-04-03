-- market database init script
create table if not exists item
(id bigserial primary key,
 "name" varchar not null,
    price real
    );

create index if not exists item_name_idx on item(name);

insert into item
(id,"name",price)
values
(1,'Laptop_Asus',1000.0),
(2,'Laptop_Mac',2000.0),
(3,'PC',900.0);


create table if not exists role
(
    id   bigserial
        primary key,
    "name" varchar(255)
);
insert into role
(id,"name")
values
(1,'USER'),
(2,'MANAGER');

create table if not exists payment
(
    id           bigserial
        primary key,
    sum          real      not null,
    payment_date timestamp not null,
    order_id     bigserial
        constraint payment_order_id_fk
            references orders);

insert into payment
(id,sum,payment_date,order_id)
values
(1,1345.30,'2023-01-04 20:32:59',1),
(2,1245.31,'2023-01-04 21:32:59',2),
(3,4245.50,'2023-01-04 22:32:59',1);


create table if not exists users
(
    id       bigserial
        primary key,
    password varchar(255),
    username varchar(255)
);


create table if not exists  orders
(
    id             bigserial
        primary key,
    order_status   varchar(255),
    total_items    integer,
    total_payments   double precision);

insert into orders
(id,order_status,total_items,total_payments)
values
(1,'CREATED',5,6212.0),
(2,'SHIPPING',3,3311.5),
(3,'DELIVERED',2,1112.0);