-- Add missing columns to existing favorites table
ALTER TABLE favorites
    ADD COLUMN IF NOT EXISTS notes TEXT,
    ADD COLUMN IF NOT EXISTS notification_enabled BOOLEAN DEFAULT TRUE;

-- Add indexes if they don't exist
CREATE INDEX IF NOT EXISTS idx_favorite_user ON favorites(user_id);
CREATE INDEX IF NOT EXISTS idx_favorite_publication ON favorites(publication_id);
CREATE INDEX IF NOT EXISTS idx_favorite_created ON favorites(created_at DESC);

-- Add constraints if they don't exist (PostgreSQL doesn't have IF NOT EXISTS for constraints, so we ignore if they exist)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_favorite_user'
    ) THEN
ALTER TABLE favorites
    ADD CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_favorite_publication'
    ) THEN
ALTER TABLE favorites
    ADD CONSTRAINT fk_favorite_publication FOREIGN KEY (publication_id) REFERENCES publications(id) ON DELETE CASCADE;
END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'uk_favorite_user_publication'
    ) THEN
ALTER TABLE favorites
    ADD CONSTRAINT uk_favorite_user_publication UNIQUE (user_id, publication_id);
END IF;
END $$;