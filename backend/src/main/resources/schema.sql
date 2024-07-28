create table if not exists products(
    id serial primary key not null,
    name text,
    price integer,
    in_stock boolean
);

create table if not exists orders(
    id serial primary key not null,
    date timestamp,
    customer_id text
);

create table if not exists order_lines(
    id serial primary key not null,
    product integer not null,
    amount integer not null,
    purchase_price integer not null,
    orders int references orders (id)
);
