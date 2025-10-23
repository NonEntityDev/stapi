--
-- This script aims to create the STAPI Client account to allow the application management.
--
INSERT INTO client_account (
    client_id, title, description, alias, secret_hash, scopes, created_at, updated_at
) VALUES (
    'd6570bce-7a4d-4442-a866-f68bf7f686a3',
    'STAPI Management Client',
    'Client account used to manage the STAPI application during runtime.',
    'stapi',
    '$2a$10$qQNXG7tdMCIxtFhl0c7qveW3fg6jCZokiG67NvpED4BQFxX4IrEJ.',
    'client.all',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);