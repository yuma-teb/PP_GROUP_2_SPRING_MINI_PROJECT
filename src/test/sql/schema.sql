-- create table
DROP TABLE IF EXISTS achievements;
CREATE TABLE IF NOT EXISTS achievements
(
    achievement_id      UUID PRIMARY KEY      DEFAULT gen_random_uuid() NOT NULL,
    title               VARCHAR(100) NOT NULL,
    description         TEXT,
    badge               VARCHAR(100) NOT NULL DEFAULT 0,
    experience_required INTEGER CHECK (experience_required >= 0),
    created_at          TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);

-- CREATE APP USER
CREATE TABLE IF NOT EXISTS app_users
(
    appUserId       UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Auto-generate UUID
    username        VARCHAR(100) NOT NULL,                      -- User's username
    email           VARCHAR(255) NOT NULL UNIQUE,               -- User's email (unique)
    level           INTEGER          DEFAULT 0,                 -- User's level (default to 0)
    xp              INTEGER          DEFAULT 0,                 -- User's experience points (default to 0)
    profileImageUrl VARCHAR(255),                               -- URL to profile image
    isVerified      BOOLEAN          DEFAULT false,             -- Verification status
    createdAt       TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP  -- Date and time the user was created
);

CREATE TABLE IF NOT EXISTS habits
(
    habitId     UUID PRIMARY KEY DEFAULT gen_random_uuid(),                                -- Auto-generate UUID for habit
    title       VARCHAR(255) NOT NULL,                                                     -- Habit title
    description TEXT,                                                                      -- Habit description
    frequency   VARCHAR(50) CHECK (frequency IN ('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY')), -- Frequency field
    isActive    BOOLEAN          DEFAULT true,                                             -- Habit active status
    createdAt   TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP,                                -- Date and time habit was created
    appUserId   UUID         NOT NULL,                                                     -- Foreign key to app_users
    FOREIGN KEY (appUserId) REFERENCES app_users (appUserId)                               -- Foreign key constraint
);


-- Create the 'habit_logs' table to track habit completion
CREATE TABLE IF NOT EXISTS habit_logs
(
    habitLogId UUID PRIMARY KEY DEFAULT gen_random_uuid(),                       -- Auto-generate UUID for the habit log
    logDate    DATE NOT NULL,                                                    -- Date of habit completion
    status     VARCHAR(50) CHECK (status IN ('COMPLETED', 'MISSED', 'SKIPPED')), -- Habit completion status
    xpEarned   INTEGER          DEFAULT 0,                                       -- XP earned for completing the habit
    habitId    UUID NOT NULL,                                                    -- Foreign key to habits
    FOREIGN KEY (habitId) REFERENCES habits (habitId),                           -- Foreign key constraint to habits
    appUserId  UUID NOT NULL,                                                    -- Foreign key to app_users (who completed the habit)
    FOREIGN KEY (appUserId) REFERENCES app_users (appUserId),                    -- Foreign key constraint to app_users
    createdAt  TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP                        -- Date and time the log was created
);

CREATE TABLE IF NOT EXISTS user_achievements
(
    appUserId     UUID NOT NULL,                                                          -- Foreign key to app_users
    achievementId UUID NOT NULL,                                                          -- Foreign key to achievements
    achievedAt    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,                                  -- Date when achievement was earned
    PRIMARY KEY (appUserId, achievementId),                                               -- Composite primary key for many-to-many
    FOREIGN KEY (appUserId) REFERENCES app_users (appUserId) ON DELETE CASCADE,           -- Foreign key to app_users
    FOREIGN KEY (achievementId) REFERENCES achievements (achievement_id)  ON DELETE CASCADE -- Foreign key to achievements
);

