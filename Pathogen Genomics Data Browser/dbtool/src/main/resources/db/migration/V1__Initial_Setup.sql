/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Dineli
 * Created: May 08, 2017
 */

-- Database creation

-- CREATE DATABASE IF NOT EXISTS pgdb
 
-- Table structure for `organisms`

DROP TABLE IF EXISTS `organisms`;
CREATE TABLE `organisms` (
  `id` INT(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `projects`

DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects` (
  `id` INT(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `description` VARCHAR(500),

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `samples`

DROP TABLE IF EXISTS `samples`;
CREATE TABLE `samples` (
  `id` VARCHAR(7) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `organism_id` INT(6) unsigned NOT NULL,
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  FOREIGN KEY (organism_id) REFERENCES organisms(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for `sequence_files`

DROP TABLE IF EXISTS `sequence_files`;
CREATE TABLE `sequence_files` (
  `id` INT(6) unsigned NOT NULL AUTO_INCREMENT,
  `file_path` VARCHAR(255) NOT NULL,
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `locations`

-- DROP TABLE IF EXISTS `locations`;
-- CREATE TABLE `locations` (
--   `id` INT(5) unsigned NOT NULL AUTO_INCREMENT,
--   `country` VARCHAR(30) unsigned NOT NULL,
--   `city` VARCHAR(50) DEFAULT NULL,
--   `latitude` DECIMAL(11, 8) NOT NULL,
--   `longitude` DECIMAL(11, 8) NOT NULL,
-- 
--   PRIMARY KEY (`id`),
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for `users`

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` INT(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `user_name` VARCHAR(10) NOT NULL,
  `encrypted_password` CHAR(40) NOT NULL,
  `dummy_password` VARCHAR(50) NOT NULL, 
  `salt` CHAR(40) NOT NULL,
  `is_admin` BOOLEAN DEFAULT FALSE,
  `is_pw_reset` BOOLEAN NOT NULL DEFAULT 0,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `privileges`
-- 
-- DROP TABLE IF EXISTS `privileges`;
-- CREATE TABLE `privileges` (
--   `id` INT(4) unsigned NOT NULL AUTO_INCREMENT,
--   `view` BOOLEAN,
-- 
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `user_privileges`

-- DROP TABLE IF EXISTS `user_privileges`;
-- CREATE TABLE `user_privileges` (
--   `id` INT(4) unsigned NOT NULL AUTO_INCREMENT,  
--   `user_id` INT(4) unsigned NOT NULL,
--   `privilege_id` INT(4) unsigned NOT NULL,
-- 
--   PRIMARY KEY (`id`),
--   FOREIGN KEY (user_id) REFERENCES users(id),
--   FOREIGN KEY (privilege_id) REFERENCES privileges(id) --cheeck for keyword
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `user_projects`

DROP TABLE IF EXISTS `user_projects`;
CREATE TABLE `user_projects` (
  `id` INT(6) unsigned NOT NULL AUTO_INCREMENT,  
  `user_id` INT(6) unsigned NOT NULL,
  `project_id` INT(6) unsigned NOT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (project_id) REFERENCES projects(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `project_samples`

DROP TABLE IF EXISTS `project_samples`;
CREATE TABLE `project_samples` (
  `id` INT(6) unsigned NOT NULL AUTO_INCREMENT,  
  `project_id` INT(6) unsigned NOT NULL,
  `sample_id` VARCHAR(7) NOT NULL, 
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (project_id) REFERENCES projects(id),
  FOREIGN KEY (sample_id) REFERENCES samples(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `samples_sequence_files`

DROP TABLE IF EXISTS `samples_sequence_files`;
CREATE TABLE `samples_sequence_files` (
  `id` INT(6) unsigned NOT NULL AUTO_INCREMENT,  
  `sample_id` VARCHAR(7) NOT NULL,
  `sequence_file_id` INT(6) unsigned NOT NULL, 

  PRIMARY KEY (`id`),
  FOREIGN KEY (sample_id) REFERENCES samples(id),
  FOREIGN KEY (sequence_file_id) REFERENCES sequence_files(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `pipeline_jobs`

CREATE TABLE `pipeline_jobs` (
  `id` INT(6) unsigned NOT NULL AUTO_INCREMENT,  
  `user_id` INT(6) unsigned NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  `type` VARCHAR(60) NOT NULL,
  `file_path` VARCHAR(150) NOT NULL,
  `input_path` VARCHAR(100) NOT NULL,
  `output_path` VARCHAR(100) NOT NULL,
  `status` VARCHAR(15),
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) REFERENCES users(id)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `jobs_cart`

CREATE TABLE `jobs_cart` (
`id` INT(6) unsigned NOT NULL AUTO_INCREMENT,  
`sample_id` VARCHAR(7) NOT NULL, 
`user_id` INT(6) unsigned NOT NULL,
`project_name` VARCHAR(30) NOT NULL,

 PRIMARY KEY (`id`),
 FOREIGN KEY (sample_id) REFERENCES samples(id),
 FOREIGN KEY (user_id) REFERENCES users(id)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `pipeline_jobs_history`

CREATE TABLE `pipeline_jobs_history` (
`id` INT(6) unsigned NOT NULL AUTO_INCREMENT,  
`name` VARCHAR(60) NOT NULL,
`type` VARCHAR(60) NOT NULL,
`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for `shared_user_projects`

CREATE TABLE `pgdb`.`shared_user_projects` (
`id` INT(6) unsigned NOT NULL AUTO_INCREMENT,
`shared_project_id` INT(6) unsigned NOT NULL,
`project_owner_id` INT(6) unsigned NOT NULL,
`shared_user_id` INT(6) unsigned NOT NULL,
  
PRIMARY KEY (`id`),
FOREIGN KEY (`shared_project_id`) REFERENCES `projects` (`id`),
FOREIGN KEY (`project_owner_id`) REFERENCES `users` (`id`),
FOREIGN KEY (`shared_user_id`) REFERENCES `users` (`id`)  
)ENGINE=InnoDB DEFAULT CHARSET=latin1;