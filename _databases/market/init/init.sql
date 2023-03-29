-- market database init script
create table if not exists items
(id bigserial primary key,
 "name" varchar not null,
    price real);

create index if not exists item_name_idx on items(name);

INSERT INTO items
(id,"name",price)
VALUES
(1,'Laptop_Asus',1000.0),
(2,'Laptop_Mac',2000.0),
(3,'PC',900.0),
(4,'MAC',1600.0),
(5,'Consol_PS4',1500.0);
(6,'Consol_PS5',1600.0);

create table if not exists payment
(
  id bigserial primary key,
    sum real not null,
    payment_date timestamp not null
);

INSERT INTO payment
(id,sum,payment_date)
VALUES
(1,1345.30,'2023-01-04 20:32:59'),
(2,1245.31,'2023-01-04 21:32:59'),
(3,4245.50,'2023-01-04 22:32:59'),
(4,145.13,'2023-01-04 23:32:59'),
(5,5645.90,'2023-01-04 12:32:59');


create table if not exists  order_tbl
(
  id bigserial primary key,
    status varchar not null,
    total_items int not null,
    total_payments real not null
);

INSERT INTO order_tbl
(id,"status",total_items,total_payments)
VALUES
(1,'SHIPPING',5,6212.0),
(2,'SHIPPING',3,3311.5),
(3,'DELIVERING',2,1112.0);