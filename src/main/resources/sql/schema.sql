-- Enable UUID generation extension
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create the 'app_users' table
CREATE TABLE IF NOT EXISTS app_users
(
    app_user_id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username          VARCHAR(100) NOT NULL,
    email             VARCHAR(255) NOT NULL UNIQUE,
    level             INTEGER          DEFAULT 0,
    xp                INTEGER          DEFAULT 0,
    profile_image_url VARCHAR(255),
    is_verified       BOOLEAN          DEFAULT false,
    created_at        TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP
);

-- Create the 'habits' table
CREATE TABLE IF NOT EXISTS habits
(
    habit_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    frequency   VARCHAR(50) CHECK (frequency IN ('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY')),
    is_active   BOOLEAN          DEFAULT true,
    created_at  TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP,
    app_user_id UUID         NOT NULL,
    FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id)
);

-- Create the 'habit_logs' table
DROP TABLE IF EXISTS habit_logs;
CREATE TABLE IF NOT EXISTS habit_logs
(
    habit_log_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    log_date          DATE NOT NULL,
    status            VARCHAR(50) CHECK (status IN ('PENDING', 'COMPLETED', 'MISSED', 'SKIPPED')),
    experience_earned INTEGER          DEFAULT 0,
    habit_id          UUID NOT NULL,
    FOREIGN KEY (habit_id) REFERENCES habits (habit_id)
);

-- Create the 'achievements' table
CREATE TABLE IF NOT EXISTS achievements
(
    achievement_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title               VARCHAR(100) NOT NULL,
    description         TEXT,
    badge               VARCHAR(100) NOT NULL,
    experience_required INTEGER CHECK (experience_required >= 0),
    created_at          TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP
);

-- Create the 'app_user_achievements' join table
CREATE TABLE IF NOT EXISTS app_user_achievements
(
    app_user_id    UUID NOT NULL,
    achievement_id UUID NOT NULL,
    achieved_at    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (app_user_id, achievement_id),
    FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id) ON DELETE CASCADE,
    FOREIGN KEY (achievement_id) REFERENCES achievements (achievement_id) ON DELETE CASCADE
);
