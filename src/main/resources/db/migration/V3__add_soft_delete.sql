-- Add soft delete support to products
ALTER TABLE products ADD COLUMN deleted BOOLEAN NOT NULL DEFAULT FALSE;

-- Index supporting soft delete queries
CREATE INDEX idx_products_active ON products(id);

-- Simple index on name (H2 does not support functional indexes like PostgreSQL's lower(name))
-- The case-insensitive search query still works; this index helps basic lookups.
CREATE INDEX idx_products_name ON products(name);
