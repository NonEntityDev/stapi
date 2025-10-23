--
-- This script aims to create the initial structure and constraints for the CLIENT_ACCOUNT table.
--
create table client_account (
    client_id UUID not null,
    title VARCHAR(140) not null,
    description VARCHAR(140) not null,
    alias VARCHAR(40) not null,
    secret_hash VARCHAR(140) not null,
    scopes VARCHAR(280) not null,
    created_at TIMESTAMP not null,
    updated_at TIMESTAMP not null
);

alter table client_account
add constraint PKY_CLIENT_ACCOUNT
primary key (client_id);

alter table client_account
add constraint UNQ_CLIENT_ACCOUNT_ALIAS
unique (alias);