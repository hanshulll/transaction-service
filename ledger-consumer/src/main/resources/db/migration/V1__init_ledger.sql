CREATE TABLE ledger_entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transaction_id UUID NOT NULL,
    account_id UUID NOT NULL,
    entry_type VARCHAR(10) NOT NULL,       -- DEBIT | CREDIT
    amount NUMERIC(19,4) NOT NULL,
    currency CHAR(3) NOT NULL,
    balance_after NUMERIC(19,4),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX idx_ledger_account ON ledger_entries(account_id);
