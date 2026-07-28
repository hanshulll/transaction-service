CREATE TABLE accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_name VARCHAR(255) NOT NULL,
    currency CHAR(3) NOT NULL,
    balance NUMERIC(19,4) NOT NULL DEFAULT 0 CHECK (balance >= 0),
    version BIGINT NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
