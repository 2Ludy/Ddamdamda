CREATE DATABASE IF NOT EXISTS ddamdamda
    DEFAULT CHARACTER SET utf8mb4 
    DEFAULT COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 선택
USE ddamdamda;

-- Images 테이블 (프로필 이미지)
CREATE TABLE images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    file_path VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50) NOT NULL
);

-- Exercises 테이블
CREATE TABLE exercises (
    exercises_id INT AUTO_INCREMENT PRIMARY KEY,
    part ENUM('가슴', '등', '어깨', '팔', '코어', '하체', '전신'),
    name VARCHAR(100) NOT NULL,
    video_url VARCHAR(255) NOT NULL
);

-- User 테이블
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,  ## username으로 변경
    profile_image_id INT,
	role enum ('ADMIN','USER') DEFAULT 'USER',
    FOREIGN KEY (profile_image_id) 
        REFERENCES images(id) 
        ON DELETE SET NULL
);

-- Routine 테이블
CREATE TABLE routine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(100) NOT NULL,
    exercise_date DATE NOT NULL,
    sets INT NOT NULL,
    reps INT NOT NULL,
    exercises_id INT,
    is_completed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) 
        REFERENCES user(id) 
        ON DELETE CASCADE,
    FOREIGN KEY (exercises_id) 
        REFERENCES exercises(exercises_id) 
        ON DELETE SET NULL
);

-- Board 테이블
CREATE TABLE board (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category ENUM('자유', '운동인증'),
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    view_count INT DEFAULT 0,
    likes_count INT DEFAULT 0,
    comments_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) 
        REFERENCES user(id) 
        ON DELETE CASCADE
);

-- BoardImage 테이블
CREATE TABLE board_image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    board_id INT,
    file_path VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50) NOT NULL,
    FOREIGN KEY (board_id) 
        REFERENCES board(id) 
        ON DELETE CASCADE
);

-- Comment 테이블
CREATE TABLE comment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    content TEXT NOT NULL,
    board_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) 
        REFERENCES user(id) 
        ON DELETE CASCADE,
    FOREIGN KEY (board_id) 
        REFERENCES board(id) 
        ON DELETE CASCADE
);

-- Groups 테이블
CREATE TABLE group_info (
    group_id INT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    admin_id INT NOT NULL,
    group_img INT,
    mate_status ENUM('모집중', '마감') DEFAULT '모집중',
    region VARCHAR(100) NOT NULL,
    exercise_type VARCHAR(100) NOT NULL,
    current_members INT DEFAULT 1,
    member_count INT NOT NULL,
    FOREIGN KEY (admin_id) 
        REFERENCES user(id) 
        ON DELETE CASCADE,
    FOREIGN KEY (group_img) 
        REFERENCES images(id) 
        ON DELETE SET NULL
);

-- group_members 테이블
CREATE TABLE group_members (
	id INT AUTO_INCREMENT PRIMARY KEY,
    group_id INT,
    user_id INT,
    FOREIGN KEY (group_id) 
        REFERENCES group_info(group_id) 
        ON DELETE CASCADE,
    FOREIGN KEY (user_id) 
        REFERENCES user(id) 
        ON DELETE CASCADE
);

-- group_notice 테이블
CREATE TABLE group_notice (
    gnotice_id INT AUTO_INCREMENT PRIMARY KEY,
    group_id INT,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) 
        REFERENCES group_info(group_id) 
        ON DELETE CASCADE
);

-- gnotice_image 테이블
CREATE TABLE gnotice_image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gnotice_id INT,
    file_path VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50) NOT NULL,
    FOREIGN KEY (gnotice_id) 
        REFERENCES group_notice(gnotice_id) 
        ON DELETE CASCADE
);

-- Gcomment 테이블
CREATE TABLE gcomment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    content TEXT NOT NULL,
    gnotice_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) 
        REFERENCES user(id) 
        ON DELETE CASCADE,
    FOREIGN KEY (gnotice_id) 
        REFERENCES group_notice(gnotice_id) 
        ON DELETE CASCADE
);

-- Notice 테이블
CREATE TABLE notice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    content TEXT NOT NULL,
    reference_id INT NOT NULL,
    reference_type ENUM('like', 'comment', 'group_notice', 'group_member'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) 
        REFERENCES user(id) 
        ON DELETE CASCADE
);

-- Likes 테이블
CREATE TABLE likes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    board_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) 
        REFERENCES user(id) 
        ON DELETE CASCADE,
    FOREIGN KEY (board_id) 
        REFERENCES board(id) 
        ON DELETE CASCADE,
    UNIQUE KEY unique_like (user_id, board_id)
);

 -- token
 CREATE TABLE token (
        id INT AUTO_INCREMENT PRIMARY KEY,
        is_logged_out bit DEFAULT 0,
        user_id INT,
        access_token VARCHAR(255),
        refresh_token varchar(255),
		FOREIGN KEY (user_id) 
			references user (id)
    );
    
    SHOW TABLES;
    
    SELECT * FROM user;
    
    INSERT INTO user (email, password, username)
    VALUES('ddd@gmail.com', '1234', '나는월급들어왔지롱메롱');