-- alter columns
ALTER TABLE profile ADD COLUMN IF NOT EXISTS name_purified CHARACTER VARYING(30) GENERATED ALWAYS AS (purify(name)) STORED;

-- indexes
CREATE INDEX IF NOT EXISTS profile_email_index ON profile USING GIN (email gin_trgm_ops);
CREATE INDEX IF NOT EXISTS profile_name_purified_index ON profile USING GIN (name_purified gin_trgm_ops);
