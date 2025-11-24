ALTER TABLE publications ADD COLUMN IF NOT EXISTS amount DECIMAL(10, 2);
ALTER TABLE publications ADD COLUMN IF NOT EXISTS currency VARCHAR(3) DEFAULT 'USD';
ALTER TABLE publications ADD COLUMN IF NOT EXISTS discount_percentage DECIMAL(5, 2);
ALTER TABLE publications ADD COLUMN IF NOT EXISTS original_amount DECIMAL(10, 2);

ALTER TABLE publications ADD COLUMN IF NOT EXISTS city VARCHAR(100);
ALTER TABLE publications ADD COLUMN IF NOT EXISTS state VARCHAR(100);
ALTER TABLE publications ADD COLUMN IF NOT EXISTS country VARCHAR(100);
ALTER TABLE publications ADD COLUMN IF NOT EXISTS postal_code VARCHAR(20);
ALTER TABLE publications ADD COLUMN IF NOT EXISTS address TEXT;
ALTER TABLE publications ADD COLUMN IF NOT EXISTS latitude DOUBLE PRECISION;
ALTER TABLE publications ADD COLUMN IF NOT EXISTS longitude DOUBLE PRECISION;

CREATE INDEX IF NOT EXISTS idx_publications_city ON publications(city);
CREATE INDEX IF NOT EXISTS idx_publications_country ON publications(country);
CREATE INDEX IF NOT EXISTS idx_publications_amount ON publications(amount);
