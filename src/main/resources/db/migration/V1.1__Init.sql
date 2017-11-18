-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ShaktiGold
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ShaktiGold
-- -----------------------------------------------------
-- CREATE SCHEMA IF NOT EXISTS `ShaktiGold` DEFAULT CHARACTER SET utf8 ;
USE `ShaktiGold` ;

-- -----------------------------------------------------
-- Table `ShaktiGold`.`user_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`user_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(64) NOT NULL,
  `last_name` VARCHAR(64) NOT NULL,
  `email` VARCHAR(128) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `password_salt` VARBINARY(512) NOT NULL,
  `password_hash` VARCHAR(1024) NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`user_account` (`id` ASC);

CREATE UNIQUE INDEX `email_UNIQUE` ON `ShaktiGold`.`user_account` (`email` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`user_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`user_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address_line_1` VARCHAR(128) NOT NULL,
  `address_line_2` VARCHAR(128) NULL,
  `state` VARCHAR(64) NOT NULL,
  `country` VARCHAR(64) NOT NULL,
  `mobile_number` VARCHAR(10) NOT NULL,
  `alt_mobile_number` VARCHAR(10) NULL,
  `landline_number` VARCHAR(16) NULL,
  `created_date` DATETIME NOT NULL,
  `updated_date` DATETIME NOT NULL,
  `profile_img` VARCHAR(512) NULL,
  `user_account_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `ud_user_account_fk`
    FOREIGN KEY (`user_account_fk`)
    REFERENCES `ShaktiGold`.`user_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `mobile_number_UNIQUE` ON `ShaktiGold`.`user_details` (`mobile_number` ASC);

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`user_details` (`id` ASC);

CREATE UNIQUE INDEX `user_account_fk_UNIQUE` ON `ShaktiGold`.`user_details` (`user_account_fk` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(128) NOT NULL,
  `description` VARCHAR(1024) NULL,
  `img_url` VARCHAR(1024) NULL,
  `record_active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`category` (`id` ASC);

CREATE UNIQUE INDEX `category_name_UNIQUE` ON `ShaktiGold`.`category` (`category_name` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`subcategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`subcategory` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `subcategory_name` VARCHAR(128) NOT NULL,
  `description` VARCHAR(1024) NULL,
  `img_url` VARCHAR(1024) NULL,
  `record_active` TINYINT(1) NOT NULL DEFAULT 1,
  `category_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `s_category_fk`
    FOREIGN KEY (`category_fk`)
    REFERENCES `ShaktiGold`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`subcategory` (`id` ASC);

CREATE INDEX `s_category_fk_idx` ON `ShaktiGold`.`subcategory` (`category_fk` ASC);

CREATE UNIQUE INDEX `subcategory_name_UNIQUE` ON `ShaktiGold`.`subcategory` (`subcategory_name` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `item_name` VARCHAR(128) NOT NULL,
  `img_url` VARCHAR(1024) NOT NULL,
  `record_active` TINYINT(1) NOT NULL DEFAULT 1,
  `subcategory_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `i_subcategory_fk`
    FOREIGN KEY (`subcategory_fk`)
    REFERENCES `ShaktiGold`.`subcategory` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`item` (`id` ASC);

CREATE UNIQUE INDEX `item_name_UNIQUE` ON `ShaktiGold`.`item` (`item_name` ASC);

CREATE INDEX `i_subcategory_fk_idx` ON `ShaktiGold`.`item` (`subcategory_fk` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`order_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`order_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_date` DATETIME NOT NULL,
  `order_status` ENUM('NEW', 'PROCESSING', 'DELIVERED', 'CANCELLED') NOT NULL,
  `order_complete_date` DATETIME NULL,
  `order_description` VARCHAR(2048) NULL,
  `quantity` INT NOT NULL,
  `item_fk` INT NOT NULL,
  `user_account_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `od_item_fk`
    FOREIGN KEY (`item_fk`)
    REFERENCES `ShaktiGold`.`item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `od_user_account_fk`
    FOREIGN KEY (`user_account_fk`)
    REFERENCES `ShaktiGold`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `invoice_number_UNIQUE` ON `ShaktiGold`.`order_details` (`id` ASC);

CREATE INDEX `item_fk_idx` ON `ShaktiGold`.`order_details` (`item_fk` ASC);

CREATE INDEX `user_account_fk_idx` ON `ShaktiGold`.`order_details` (`user_account_fk` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`subcategory_property`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`subcategory_property` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `property_name` VARCHAR(128) NOT NULL,
  `property_type` VARCHAR(64) NOT NULL,
  `property_unit` VARCHAR(32) NULL,
  `subcategory_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `sp_subcategory_fk`
    FOREIGN KEY (`subcategory_fk`)
    REFERENCES `ShaktiGold`.`subcategory` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`subcategory_property` (`id` ASC);

CREATE INDEX `sp_subcategory_fk_idx` ON `ShaktiGold`.`subcategory_property` (`subcategory_fk` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`item_property`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`item_property` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `property_value` VARCHAR(2048) NOT NULL,
  `item_fk` INT NOT NULL,
  `subcategory_property_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `ip_item_fk`
    FOREIGN KEY (`item_fk`)
    REFERENCES `ShaktiGold`.`item` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `ip_subcategory_property_fk`
    FOREIGN KEY (`subcategory_property_fk`)
    REFERENCES `ShaktiGold`.`subcategory_property` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`item_property` (`id` ASC);

CREATE INDEX `ip_item_fk_idx` ON `ShaktiGold`.`item_property` (`item_fk` ASC);

CREATE INDEX `ip_subcategory_property_fk_idx` ON `ShaktiGold`.`item_property` (`subcategory_property_fk` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`user_session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`user_session` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ssid` VARCHAR(512) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `user_account_fk` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `ss_user_account_fk`
    FOREIGN KEY (`user_account_fk`)
    REFERENCES `ShaktiGold`.`user_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`user_session` (`id` ASC);

CREATE INDEX `ss_user_account_fk_idx` ON `ShaktiGold`.`user_session` (`user_account_fk` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `item_fk` INT NOT NULL,
  `user_account_fk` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `ct_item_fk`
    FOREIGN KEY (`item_fk`)
    REFERENCES `ShaktiGold`.`item` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `ct_user_account_fk`
    FOREIGN KEY (`user_account_fk`)
    REFERENCES `ShaktiGold`.`user_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `guid_UNIQUE` ON `ShaktiGold`.`cart` (`id` ASC);

CREATE INDEX `ct_item_fk_idx` ON `ShaktiGold`.`cart` (`item_fk` ASC);

CREATE INDEX `ct_user_account_fk_idx` ON `ShaktiGold`.`cart` (`user_account_fk` ASC);


-- -----------------------------------------------------
-- Table `ShaktiGold`.`notifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShaktiGold`.`notifications` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `admin_read` TINYINT(1) NOT NULL,
  `user_read` TINYINT(1) NOT NULL,
  `approx_amount` INT NULL,
  `user_fk` INT NOT NULL,
  `item_fk` INT NOT NULL,
  `created_date` DATETIME NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  CONSTRAINT `no_user_fk`
    FOREIGN KEY (`id`)
    REFERENCES `ShaktiGold`.`user_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `no_item_fk`
    FOREIGN KEY (`id`)
    REFERENCES `ShaktiGold`.`item` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `ShaktiGold`.`notifications` (`id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
