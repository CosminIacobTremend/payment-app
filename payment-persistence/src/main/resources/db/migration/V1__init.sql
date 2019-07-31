create table client
(
    id bigserial not null
        constraint client_pkey
            primary key,
    cnp varchar(255) not null unique,
    email varchar(255) not null ,
    first_name varchar(255) not null,
    last_name varchar(255) not null
);

alter table client owner to postgres;

create table account
(
    id serial not null
        constraint account_pkey
            primary key,
    iban varchar(255) not null unique,
    client_id bigint not null
        constraint account_fk_client
            references client(id)
);

alter table account owner to postgres;

create table transaction
(
    id bigserial not null
        constraint transaction_pkey
            primary key,
    description varchar(255),
    name varchar(255) not null ,
    sum double precision not null ,
    transaction_type varchar(255) not null,
    account_id integer not null
        constraint transaction_fk_account
            references account,
    client_id bigint not null
        constraint transaction_fk_client
            references client(id)
);

alter table transaction owner to postgres;