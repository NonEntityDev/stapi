--
-- Creates the account table and all of its constraints.
--
create table account (
    id UUID not null,
    name VARCHAR(140) not null,
    alias VARCHAR(40) not null,
    secret VARCHAR(256) not null,
    account_type VARCHAR(40) not null,
    enabled boolean not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

alter table account
add constraint PKY_ACCOUNT
primary key (id);

alter table account
add constraint UNQ_ACCOUNT_ALIAS
unique (alias, account_type);