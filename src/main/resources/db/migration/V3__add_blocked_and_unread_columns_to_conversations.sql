-- Add blocked columns to conversations table
ALTER TABLE conversations
    ADD COLUMN IF NOT EXISTS is_blocked_by_user1 BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS is_blocked_by_user2 BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS unread_count_user1 INTEGER DEFAULT 0,
    ADD COLUMN IF NOT EXISTS unread_count_user2 INTEGER DEFAULT 0;