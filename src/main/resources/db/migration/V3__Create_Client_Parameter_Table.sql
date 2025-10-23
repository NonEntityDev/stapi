--
-- This script aims to create the initial structure and required constraints for table CLIENT_PARAMETER.
--
create table client_parameter (
    parameter_id UUID not null,
    client_id UUID not null,
    parameter_name VARCHAR(40) not null,
    parameter_value VARCHAR(460) not null,
    modified_at TIMESTAMP not null
);

alter table client_parameter
add constraint PKY_CLIENT_PARAMETER
primary key (parameter_id);

alter table client_parameter
add constraint FKY_CLIENT_ACCOUNT_CLIENT_PARAMETER
foreign key (client_id)
references client_account(client_id)
on delete cascade;

alter table client_parameter
add constraint UNQ_CLIENT_PARAMETER_NAME
unique (client_id, parameter_name);