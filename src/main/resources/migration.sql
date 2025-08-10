-- indexes
CREATE INDEX IF NOT EXISTS profile_email_index ON profile USING GIN (email gin_trgm_ops);
CREATE INDEX IF NOT EXISTS profile_name_index ON profile USING GIN (name gin_trgm_ops);
