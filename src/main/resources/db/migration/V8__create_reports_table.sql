-- Create reports table
CREATE TABLE IF NOT EXISTS reports (
                                       id BIGSERIAL PRIMARY KEY,
                                       reporter_id BIGINT NOT NULL,
                                       type VARCHAR(50) NOT NULL,
    reported_entity_id BIGINT NOT NULL,
    reported_entity_type VARCHAR(50) NOT NULL,
    reason VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    reviewed_by BIGINT,
    review_notes TEXT,
    reviewed_at TIMESTAMP,
    priority VARCHAR(50) DEFAULT 'NORMAL',
    action_taken TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    -- Foreign keys
    CONSTRAINT fk_report_reporter FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_report_reviewer FOREIGN KEY (reviewed_by) REFERENCES users(id) ON DELETE SET NULL
    );

-- Indexes
CREATE INDEX idx_report_reporter ON reports(reporter_id);
CREATE INDEX idx_report_reported_entity ON reports(reported_entity_id, reported_entity_type);
CREATE INDEX idx_report_status ON reports(status);
CREATE INDEX idx_report_created ON reports(created_at DESC);