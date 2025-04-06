-- Insert data into the 'app_users' table with password
INSERT INTO app_users (username, email, password, level, xp, profile_image_url, is_verified)
VALUES ('john_doe', 'john.doe@example.com', 'hashed_password_1', 5, 1200,
        'https://www.w3schools.com/w3images/avatar2.png', true),
       ('jane_smith', 'jane.smith@example.com', 'hashed_password_2', 3, 800,
        'https://www.w3schools.com/w3images/avatar6.png', false),
       ('mark_twain', 'mark.twain@example.com', 'hashed_password_3', 2, 450,
        'https://www.w3schools.com/w3images/avatar5.png', true);

-- Insert data into the 'habits' table
INSERT INTO habits (title, description, frequency, is_active, app_user_id)
VALUES ('Morning Exercise', 'A habit to perform exercise every morning to stay fit.', 'DAILY', true,
        (SELECT app_user_id FROM app_users WHERE username = 'john_doe')),
       ('Reading Books', 'A habit to read books for at least 30 minutes every day.', 'DAILY', true,
        (SELECT app_user_id FROM app_users WHERE username = 'jane_smith')),
       ('Weekly Meal Prep', 'Prepare healthy meals every week to ensure proper nutrition.', 'WEEKLY', true,
        (SELECT app_user_id FROM app_users WHERE username = 'mark_twain'));

-- Insert data into the 'habit_logs' table
INSERT INTO habit_logs (log_date, status, experience_earned, habit_id)
VALUES ('2025-04-05', 'COMPLETED', 50, (SELECT habit_id FROM habits WHERE title = 'Morning Exercise')),
       ('2025-04-05', 'MISSED', 0, (SELECT habit_id FROM habits WHERE title = 'Reading Books')),
       ('2025-04-05', 'SKIPPED', 0, (SELECT habit_id FROM habits WHERE title = 'Weekly Meal Prep'));

-- Insert data into the 'achievements' table
INSERT INTO achievements (title, description, badge, experience_required)
VALUES ('Fitness Enthusiast', 'Awarded for completing 30 days of morning exercise.', 'fitness_badge.png', 500),
       ('Bookworm', 'Awarded for reading 10 books in a year.', 'bookworm_badge.png', 1000),
       ('Meal Prep Master', 'Awarded for completing 10 weeks of meal prep.', 'meal_prep_badge.png', 700);

-- Insert data into the 'app_user_achievements' join table
INSERT INTO app_user_achievements (app_user_id, achievement_id)
VALUES ((SELECT app_user_id FROM app_users WHERE username = 'john_doe'),
        (SELECT achievement_id FROM achievements WHERE title = 'Fitness Enthusiast')),
       ((SELECT app_user_id FROM app_users WHERE username = 'jane_smith'),
        (SELECT achievement_id FROM achievements WHERE title = 'Bookworm')),
       ((SELECT app_user_id FROM app_users WHERE username = 'mark_twain'),
        (SELECT achievement_id FROM achievements WHERE title = 'Meal Prep Master'));



INSERT INTO app_user_achievements (app_user_id, achievement_id)
VALUES ((SELECT app_user_id FROM app_users WHERE username = 'jane_smith'),
        (SELECT achievement_id FROM achievements WHERE title = 'Fitness Enthusiast'));


SELECT *
FROM app_user_achievements auc
         INNER JOIN public.achievements a on a.achievement_id = auc.achievement_id
WHERE app_user_id = '22bae3e8-9018-4979-abfc-82b73b250b21';

INSERT INTO achievements (title, description, badge, experience_required)
VALUES ('First Habit Completed', 'Awarded when a user completes their first habit.', 'first_habit_badge.png', 50),
       ('7-Day Streak', 'Awarded for completing a habit for 7 consecutive days.', '7_day_streak_badge.png', 100),
       ('30-Day Streak', 'Awarded for completing a habit for 30 consecutive days.', '30_day_streak_badge.png', 200),
       ('XP Overlord', 'Awarded for earning 5000 XP in total.', 'xp_overlord_badge.png', 5000);
