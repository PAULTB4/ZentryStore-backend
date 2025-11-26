ALTER TABLE conversations ADD COLUMN deleted_at TIMESTAMP;
ALTER TABLE messages ADD COLUMN deleted_at TIMESTAMP;