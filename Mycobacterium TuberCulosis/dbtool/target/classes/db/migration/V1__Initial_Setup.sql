/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  EPHAADK
 * Created: Feb 17, 2016
 */

-- Database creation

-- CREATE DATABASE IF NOT EXISTS MTC_DB
 
-- Table structure for table `studys`

DROP TABLE IF EXISTS `studys`;
CREATE TABLE `studys` (
  `id` VARCHAR(12) NOT NULL,
  `name` VARCHAR(250),
  `contact_name` VARCHAR(50) DEFAULT NULL,
  `contact_details` VARCHAR(100) DEFAULT NULL,
  `contact_email` VARCHAR(25) DEFAULT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for table `countrys`

DROP TABLE IF EXISTS `countrys`;
CREATE TABLE `countrys` (
  `id` INT(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(25) NOT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `locations`

DROP TABLE IF EXISTS `locations`;
CREATE TABLE `locations` (
  `id` INT(10) unsigned NOT NULL AUTO_INCREMENT,
  `country_id` INT(10) unsigned NOT NULL,
  `city` VARCHAR(25) DEFAULT NULL,
  `latitude` DECIMAL(11, 8) NOT NULL,
  `longitude` DECIMAL(11, 8) NOT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (country_id) REFERENCES countrys(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `samples`

DROP TABLE IF EXISTS `samples`;
CREATE TABLE `samples` (
  `id` VARCHAR(10) NOT NULL,
  `study_id` VARCHAR(12) NOT NULL,
  `location_id` INT(10) unsigned NOT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (study_id) REFERENCES studys(id),
  FOREIGN KEY (location_id) REFERENCES locations(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `sample_replications`

DROP TABLE IF EXISTS `sample_replications`;
CREATE TABLE `sample_replications` (
  `id` VARCHAR(10) NOT NULL,
  `sample_id` VARCHAR(10) NOT NULL,
 
  PRIMARY KEY (`id`),
  FOREIGN KEY (sample_id) REFERENCES samples(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `accessions`

DROP TABLE IF EXISTS `accessions`;
CREATE TABLE `accessions` (
  `id` VARCHAR(10) NOT NULL,
  `sample_id` VARCHAR(10) NOT NULL,
  `sample_replication_id` VARCHAR(10),
  `url_1` VARCHAR(80) NOT NULL,
  `url_2` VARCHAR(80) DEFAULT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (sample_id) REFERENCES samples(id),
  FOREIGN KEY (sample_replication_id) REFERENCES sample_replications(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `drugs`;
CREATE TABLE `drugs` (
  `id` INT(4) unsigned NOT NULL AUTO_INCREMENT,
  `drug_abbreviation` VARCHAR(10) NOT NULL,
  `drug_name` VARCHAR(25),

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `tb_genetic_locus`;
CREATE TABLE `tb_genetic_locus` (
  `id` INT(4) unsigned NOT NULL AUTO_INCREMENT,
  `locus_name` VARCHAR(25) NOT NULL,
  `locus_tag` VARCHAR(25),
  `url` VARCHAR(100),

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `drug_resistance_data`;
CREATE TABLE `drug_resistance_data` (
  `id` INT(5) unsigned NOT NULL AUTO_INCREMENT,
  `drug_id` INT(4) unsigned NOT NULL, 
  `tb_genetic_locus_id` INT(4) unsigned NOT NULL,
  `description` VARCHAR(120) DEFAULT NULL,
  `start_chr_coord` INT(7) DEFAULT NULL,
  `stop_chr_coord` INT(7) DEFAULT NULL,
  `start_gene_coord` INT(4) DEFAULT NULL,
  `stop_gene_coord` INT(4) DEFAULT NULL,
  `nucleotide_ref` VARCHAR(4) DEFAULT NULL,
  `nucleotide_alt` VARCHAR(5) DEFAULT NULL,
  `start_codon_no` INT(3) DEFAULT NULL,
  `stop_codon_no` INT(3) DEFAULT NULL,
  `codon_ref` VARCHAR(3) DEFAULT NULL,
  `codon_alt` VARCHAR(11) DEFAULT NULL,
  `amino_ref` CHAR(5) DEFAULT NULL,
  `amino_alt` CHAR(5) DEFAULT NULL,
  `tbdreamdb` BOOLEAN DEFAULT NULL,
  `tbproflr` BOOLEAN DEFAULT NULL,
  `kvarq` BOOLEAN DEFAULT NULL,
  `hc` BOOLEAN DEFAULT NULL,
  `isValidated` BOOLEAN DEFAULT NULL,
  `references` VARCHAR(350),
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (drug_id) REFERENCES drugs(id),
  FOREIGN KEY (tb_genetic_locus_id) REFERENCES tb_genetic_locus(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



