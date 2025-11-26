-- Add missing columns to messages table
ALTER TABLE messages
    ADD COLUMN IF NOT EXISTS attachment_type VARCHAR(50),
    ADD COLUMN IF NOT EXISTS is_system_message BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS delivered_at TIMESTAMP;