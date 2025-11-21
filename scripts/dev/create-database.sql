-- scripts/dev/create-database.sql
-- Run this script to create the development database

-- Create database
CREATE DATABASE zentry_store_dev
    WITH
    OWNER = zentry_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Create user if not exists
DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'zentry_user') THEN

CREATE ROLE zentry_user LOGIN PASSWORD 'zentry_pass';
END IF;
END
$$;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE zentry_store_dev TO zentry_user;