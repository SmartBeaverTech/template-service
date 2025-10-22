CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE templates (
    template_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    type VARCHAR NOT NULL,
    version INT NOT NULL,

    -- Embedded Metadata fields
    name VARCHAR,
    description TEXT,
    category VARCHAR,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now(),

    -- JSONB content fields
    content JSONB,
    appearance JSONB,
    behavior JSONB,
    logic JSONB,
    analytics JSONB
);