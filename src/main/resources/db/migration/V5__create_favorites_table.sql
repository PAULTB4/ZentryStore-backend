-- Create favorites table
CREATE TABLE IF NOT EXISTS favorites (
                                         id BIGSERIAL PRIMARY KEY,
                                         user_id BIGINT NOT NULL,
                                         publication_id BIGINT NOT NULL,
                                         notes TEXT,
                                         notification_enabled BOOLEAN DEFAULT TRUE,
                                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign keys
                                         CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_publication FOREIGN KEY (publication_id) REFERENCES publications(id) ON DELETE CASCADE,

    -- Unique constraint
    CONSTRAINT uk_favorite_user_publication UNIQUE (user_id, publication_id)
    );

-- Indexes
CREATE INDEX idx_favorite_user ON favorites(user_id);
CREATE INDEX idx_favorite_publication ON favorites(publication_id);
CREATE INDEX idx_favorite_created ON favorites(created_at DESC);