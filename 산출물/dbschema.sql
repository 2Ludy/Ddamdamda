-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ddamdamda
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ddamdamda
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ddamdamda` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `ddamdamda` ;

-- -----------------------------------------------------
-- Table `ddamdamda`.`images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`images` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `file_path` VARCHAR(255) NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `file_type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 120
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `profile_image_id` INT NULL DEFAULT '1',
  `role` ENUM('ADMIN', 'USER') NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email` (`email` ASC) VISIBLE,
  UNIQUE INDEX `username` (`username` ASC) VISIBLE,
  INDEX `profile_image_id` (`profile_image_id` ASC) VISIBLE,
  CONSTRAINT `user_ibfk_1`
    FOREIGN KEY (`profile_image_id`)
    REFERENCES `ddamdamda`.`images` (`id`)
    ON DELETE SET NULL)
ENGINE = InnoDB
AUTO_INCREMENT = 70
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `category` ENUM('자유', '운동인증') NULL DEFAULT NULL,
  `title` VARCHAR(200) NOT NULL,
  `content` TEXT NOT NULL,
  `view_count` INT NULL DEFAULT '0',
  `likes_count` INT NULL DEFAULT '0',
  `comments_count` INT NULL DEFAULT '0',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 90
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`board_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`board_image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NULL DEFAULT NULL,
  `file_path` VARCHAR(255) NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `file_type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `board_id` (`board_id` ASC) VISIBLE,
  CONSTRAINT `board_image_ibfk_1`
    FOREIGN KEY (`board_id`)
    REFERENCES `ddamdamda`.`board` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 54
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `content` TEXT NOT NULL,
  `board_id` INT NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `board_id` (`board_id` ASC) VISIBLE,
  CONSTRAINT `comment_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `comment_ibfk_2`
    FOREIGN KEY (`board_id`)
    REFERENCES `ddamdamda`.`board` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 171
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`exercises`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`exercises` (
  `exercises_id` INT NOT NULL AUTO_INCREMENT,
  `part` ENUM('가슴', '등', '어깨', '팔', '코어', '하체', '전신') NULL DEFAULT NULL,
  `name` VARCHAR(100) NOT NULL,
  `video_url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`exercises_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 71
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`group_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`group_info` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(100) NOT NULL,
  `description` TEXT NOT NULL,
  `admin_id` INT NOT NULL,
  `group_img` INT NULL DEFAULT NULL,
  `mate_status` ENUM('모집중', '마감') NULL DEFAULT '모집중',
  `region` VARCHAR(100) NOT NULL,
  `exercise_type` VARCHAR(100) NOT NULL,
  `current_members` INT NULL DEFAULT '1',
  `member_count` INT NOT NULL,
  PRIMARY KEY (`group_id`),
  INDEX `admin_id` (`admin_id` ASC) VISIBLE,
  INDEX `group_img` (`group_img` ASC) VISIBLE,
  CONSTRAINT `group_info_ibfk_1`
    FOREIGN KEY (`admin_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `group_info_ibfk_2`
    FOREIGN KEY (`group_img`)
    REFERENCES `ddamdamda`.`images` (`id`)
    ON DELETE SET NULL)
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`group_notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`group_notice` (
  `gnotice_id` INT NOT NULL AUTO_INCREMENT,
  `group_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(200) NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`gnotice_id`),
  INDEX `group_id` (`group_id` ASC) VISIBLE,
  CONSTRAINT `group_notice_ibfk_1`
    FOREIGN KEY (`group_id`)
    REFERENCES `ddamdamda`.`group_info` (`group_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`gcomment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`gcomment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `content` TEXT NOT NULL,
  `gnotice_id` INT NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `gnotice_id` (`gnotice_id` ASC) VISIBLE,
  CONSTRAINT `gcomment_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `gcomment_ibfk_2`
    FOREIGN KEY (`gnotice_id`)
    REFERENCES `ddamdamda`.`group_notice` (`gnotice_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 67
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`gnotice_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`gnotice_image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gnotice_id` INT NULL DEFAULT NULL,
  `file_path` VARCHAR(255) NOT NULL,
  `file_name` VARCHAR(255) NOT NULL,
  `file_type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `gnotice_id` (`gnotice_id` ASC) VISIBLE,
  CONSTRAINT `gnotice_image_ibfk_1`
    FOREIGN KEY (`gnotice_id`)
    REFERENCES `ddamdamda`.`group_notice` (`gnotice_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`group_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`group_members` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `group_id` (`group_id` ASC) VISIBLE,
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `group_members_ibfk_1`
    FOREIGN KEY (`group_id`)
    REFERENCES `ddamdamda`.`group_info` (`group_id`)
    ON DELETE CASCADE,
  CONSTRAINT `group_members_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 145
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`likes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `board_id` INT NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `unique_like` (`user_id` ASC, `board_id` ASC) VISIBLE,
  INDEX `board_id` (`board_id` ASC) VISIBLE,
  CONSTRAINT `likes_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `likes_ibfk_2`
    FOREIGN KEY (`board_id`)
    REFERENCES `ddamdamda`.`board` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 572
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`notice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `content` TEXT NOT NULL,
  `reference_id` INT NOT NULL,
  `reference_type` ENUM('like', 'comment', 'group_notice', 'group_member') NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `is_read` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `notice_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1174
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`routine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`routine` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NOT NULL,
  `exercise_date` DATE NOT NULL,
  `sets` INT NOT NULL,
  `reps` INT NOT NULL,
  `exercises_id` INT NULL DEFAULT NULL,
  `is_completed` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  INDEX `exercises_id` (`exercises_id` ASC) VISIBLE,
  CONSTRAINT `routine_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `routine_ibfk_2`
    FOREIGN KEY (`exercises_id`)
    REFERENCES `ddamdamda`.`exercises` (`exercises_id`)
    ON DELETE SET NULL)
ENGINE = InnoDB
AUTO_INCREMENT = 158
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `ddamdamda`.`token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ddamdamda`.`token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `is_logged_out` BIT(1) NULL DEFAULT b'0',
  `user_id` INT NULL DEFAULT NULL,
  `access_token` VARCHAR(255) NULL DEFAULT NULL,
  `refresh_token` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKe32ek7ixanakfqsdaokm4q9y2` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `token_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ddamdamda`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 396
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
