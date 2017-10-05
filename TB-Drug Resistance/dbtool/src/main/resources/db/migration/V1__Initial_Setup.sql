/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Dineli
 * Created: Nov 28, 2016
 */

-- Database creation

-- CREATE DATABASE IF NOT EXISTS tbdr
 
-- Table structure for `drugs`

DROP TABLE IF EXISTS `drugs`;
CREATE TABLE `drugs` (
  `id` INT(4) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `data_sources`

DROP TABLE IF EXISTS `data_sources`;
CREATE TABLE `data_sources` (
  `id` INT(4) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `description` VARCHAR(500),

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `variants`

DROP TABLE IF EXISTS `variants`;
CREATE TABLE `variants` (
  `id` INT(5) unsigned NOT NULL AUTO_INCREMENT,
  `var_position_genome_start` INT(7) NOT NULL,
  `var_position_genome_stop` INT(7) NOT NULL,
  `var_type` VARCHAR(5),
  `number` INT (2),
  `wt_base` VARCHAR(6),
  `var_base` VARCHAR(6) NOT NULL,
  `region` VARCHAR(15),
  `gene_id` VARCHAR(15),
  `gene_name` VARCHAR(15),
  `gene_start` INT (7),
  `gene_stop` INT (7),
  `gene_length` INT (4),
  `dir` VARCHAR(3),
  `wt_aa` VARCHAR(5),
  `codon_nr` INT (4),
  `codon_nr_e_coli` INT (4),
  `var_aa` VARCHAR(10),
  `aa_change` VARCHAR(20),
  `codon_change` VARCHAR(15),
  `var_position_gene_start` INT (7),
  `var_position_gene_stop` INT (7),
  `remarks` VARCHAR(1000),

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Table structure for `drug_resistance`

DROP TABLE IF EXISTS `drug_resistance`;
CREATE TABLE `drug_resistance` (
  `id` INT(5) unsigned NOT NULL AUTO_INCREMENT,
  `variant_id` INT(5) unsigned NOT NULL,
  `drug_id` INT(4) unsigned NOT NULL,
  `data_source_id` INT(4) unsigned NOT NULL,
  `reference_pmid` INT(10),
  `high_confidence` BOOLEAN DEFAULT FALSE,

  PRIMARY KEY (`id`),
  FOREIGN KEY (variant_id) REFERENCES variants(id),
  FOREIGN KEY (drug_id) REFERENCES drugs(id),
  FOREIGN KEY (data_source_id) REFERENCES data_sources(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;