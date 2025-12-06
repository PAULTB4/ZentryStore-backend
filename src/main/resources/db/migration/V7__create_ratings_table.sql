-- Create ratings table
CREATE TABLE IF NOT EXISTS ratings (
                                       id BIGSERIAL PRIMARY KEY,
                                       rater_id BIGINT NOT NULL,
                                       rated_user_id BIGINT NOT NULL,
                                       publication_id BIGINT NOT NULL,

    -- Rating scores (embeddable)
                                       overall_score INTEGER NOT NULL CHECK (overall_score >= 1 AND overall_score <= 5),
    communication_score INTEGER CHECK (communication_score >= 1 AND communication_score <= 5),
    product_quality_score INTEGER CHECK (product_quality_score >= 1 AND product_quality_score <= 5),
    delivery_score INTEGER CHECK (delivery_score >= 1 AND delivery_score <= 5),
    value_for_money_score INTEGER CHECK (value_for_money_score >= 1 AND value_for_money_score <= 5),

    comment TEXT,
    is_anonymous BOOLEAN DEFAULT FALSE,
    is_verified_purchase BOOLEAN DEFAULT FALSE,
    helpful_count INTEGER DEFAULT 0,
    reported_count INTEGER DEFAULT 0,
    is_visible BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    -- Foreign keys
    CONSTRAINT fk_rating_rater FOREIGN KEY (rater_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_rating_rated_user FOREIGN KEY (rated_user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_rating_publication FOREIGN KEY (publication_id) REFERENCES publications(id) ON DELETE CASCADE,

    -- Unique constraint: un usuario solo puede calificar una publicación una vez
    CONSTRAINT uk_rating_rater_publication UNIQUE (rater_id, publication_id)
    );

-- Indexes
CREATE INDEX idx_rating_publication ON ratings(publication_id);
CREATE INDEX idx_rating_rater ON ratings(rater_id);
CREATE INDEX idx_rating_rated_user ON ratings(rated_user_id);
CREATE INDEX idx_rating_created ON ratings(created_at DESC);