-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 19, 2017 at 03:55 PM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `pharmacy2`
--
CREATE DATABASE IF NOT EXISTS `pharmacy2` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `pharmacy2`;

-- --------------------------------------------------------

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
CREATE TABLE IF NOT EXISTS `delivery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `id_pharmacy` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pharmacy` (`id_pharmacy`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `delivery`
--

INSERT INTO `delivery` (`id`, `date`, `id_pharmacy`) VALUES
(11, '2017-01-31', 8),
(20, '2017-02-03', 8),
(30, '2017-02-03', 9),
(31, '2017-02-06', 9),
(32, '2017-01-08', 10),
(33, '2017-01-15', 8),
(35, '2017-01-09', 12),
(36, '2017-02-08', 8),
(39, '2017-02-04', 9),
(40, '2017-01-20', 8),
(41, '2017-01-10', 8),
(42, '2017-02-05', 8),
(43, '2017-01-19', 10),
(44, '2017-02-05', 12),
(45, '2017-01-19', 9),
(47, '2017-01-16', 12),
(48, '2017-01-25', 9),
(49, '2017-01-14', 12),
(51, '2017-01-04', 12),
(52, '2017-01-17', 9),
(53, '2017-01-29', 9),
(54, '2017-01-04', 8),
(57, '2017-01-26', 12),
(61, '2017-01-20', 12),
(62, '2017-01-03', 10),
(64, '2017-02-08', 9),
(67, '2017-01-06', 12),
(69, '2017-01-11', 8),
(71, '2017-02-10', 8),
(72, '2017-01-27', 8),
(73, '2017-01-23', 10),
(74, '2017-01-31', 9),
(75, '2017-02-11', 10),
(77, '2017-01-07', 9),
(80, '2017-01-22', 12),
(84, '2017-01-24', 8),
(85, '2017-01-18', 10),
(87, '2017-01-30', 12),
(90, '2017-01-05', 10),
(92, '2017-01-22', 9),
(93, '2017-01-20', 9),
(94, '2017-01-25', 10),
(98, '2017-02-04', 8),
(100, '2017-01-06', 10),
(101, '2017-02-10', 9),
(102, '2017-02-09', 10),
(103, '2017-01-28', 10),
(105, '2017-01-20', 10),
(106, '2017-02-04', 12),
(108, '2017-01-09', 9),
(109, '2017-01-05', 8),
(111, '2017-02-03', 9),
(113, '2017-01-08', 8),
(114, '2017-01-29', 10),
(115, '2017-02-11', 8),
(116, '2017-01-13', 10),
(118, '2017-02-03', 12),
(120, '2017-01-19', 9),
(121, '2017-02-06', 12),
(122, '2017-01-23', 12),
(123, '2017-02-04', 10),
(124, '2017-02-13', 9),
(125, '2017-01-18', 8),
(126, '2017-02-02', 10),
(128, '2017-01-10', 10),
(129, '2017-01-03', 12),
(130, '2017-02-04', 10);

-- --------------------------------------------------------

--
-- Table structure for table `delivery_medicine`
--

DROP TABLE IF EXISTS `delivery_medicine`;
CREATE TABLE IF NOT EXISTS `delivery_medicine` (
  `id_delivery` int(11) NOT NULL,
  `id_medicine` int(11) NOT NULL,
  `box_quantity` int(11) NOT NULL,
  PRIMARY KEY (`id_delivery`,`id_medicine`),
  KEY `id_medicine` (`id_medicine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `delivery_medicine`
--

INSERT INTO `delivery_medicine` (`id_delivery`, `id_medicine`, `box_quantity`) VALUES
(11, 2, 1),
(11, 3, 2),
(11, 99, 3),
(20, 4, 1),
(20, 5, 2),
(20, 40, 1),
(20, 59, 1),
(30, 4, 1),
(30, 5, 2),
(30, 19, 2),
(30, 37, 3),
(30, 75, 2),
(30, 90, 4),
(31, 16, 3),
(32, 4, 4),
(32, 34, 5),
(35, 52, 1),
(35, 76, 4),
(35, 94, 4),
(39, 6, 2),
(39, 17, 4),
(39, 67, 3),
(40, 28, 5),
(40, 50, 5),
(41, 76, 4),
(43, 105, 4),
(44, 51, 2),
(45, 3, 5),
(45, 58, 2),
(45, 60, 4),
(45, 74, 1),
(45, 87, 5),
(47, 73, 1),
(47, 90, 3),
(47, 98, 1),
(48, 49, 5),
(48, 75, 2),
(49, 34, 3),
(49, 64, 2),
(51, 5, 3),
(52, 32, 2),
(52, 34, 1),
(52, 95, 3),
(52, 105, 4),
(53, 9, 4),
(53, 33, 4),
(53, 69, 3),
(54, 39, 4),
(57, 86, 3),
(62, 42, 5),
(62, 67, 4),
(64, 43, 4),
(67, 40, 5),
(67, 83, 3),
(67, 85, 5),
(71, 89, 5),
(72, 56, 5),
(72, 80, 5),
(73, 67, 1),
(75, 94, 1),
(77, 40, 4),
(77, 103, 2),
(80, 11, 3),
(80, 28, 1),
(80, 62, 2),
(84, 11, 4),
(84, 72, 2),
(85, 5, 1),
(85, 95, 1),
(87, 45, 2),
(92, 58, 4),
(98, 32, 5),
(98, 57, 5),
(100, 59, 5),
(100, 63, 4),
(102, 37, 3),
(102, 73, 1),
(106, 60, 5),
(106, 64, 5),
(109, 20, 3),
(109, 35, 5),
(109, 44, 1),
(109, 45, 2),
(109, 80, 3),
(111, 56, 1),
(111, 60, 4),
(111, 90, 4),
(115, 12, 3),
(115, 70, 1),
(115, 74, 2),
(116, 32, 1),
(120, 70, 2),
(120, 89, 4),
(121, 51, 1),
(125, 67, 3),
(125, 80, 1),
(125, 83, 4),
(125, 95, 2),
(126, 63, 3),
(128, 35, 5),
(128, 43, 1),
(129, 44, 4),
(129, 45, 2),
(129, 51, 3),
(129, 56, 3),
(130, 36, 4);

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
CREATE TABLE IF NOT EXISTS `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `surname` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `occupation` varchar(50) NOT NULL,
  `standing` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`id`, `surname`, `name`, `occupation`, `standing`) VALUES
(1, 'Курочкін', 'Андрій', 'Хірург', 2015),
(6, 'Іваненко', 'Сергій', 'Офтальмолог', 2011),
(7, 'Червоненко', 'Дарина', 'Стоматолог', 2000),
(8, 'Земленко', 'Ольга', 'Педіатр', 2003);

-- --------------------------------------------------------

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
CREATE TABLE IF NOT EXISTS `medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `producer` varchar(255) NOT NULL,
  `box_price` decimal(13,2) NOT NULL,
  `quantity_per_box` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `medicine`
--

INSERT INTO `medicine` (`id`, `name`, `producer`, `box_price`, `quantity_per_box`) VALUES
(2, 'Travomel', 'Znahar', '100.00', 25),
(3, 'Notta', 'Znahar', '100.00', 30),
(4, 'Mezym', 'Znahar', '100.00', 30),
(5, 'Hilac', 'Znahar', '200.00', 10),
(6, 'Penicillin VK', 'AstraZeneca', '500.00', 24),
(7, 'Ciprofloxacin HCl', 'Gilead Sciences', '125.00', 10),
(8, 'Metoprolol Succinate', 'GlaxoSmithKline', '125.00', 30),
(9, 'Enalapril Maleate', 'GlaxoSmithKline', '750.00', 30),
(10, 'Diovan', 'Sanofi', '750.00', 100),
(11, 'Loestrin 24 Fe', 'AstraZeneca', '125.00', 24),
(12, 'Lantus Solostar', 'Bayer', '750.00', 100),
(13, 'Amoxicillin', 'Pfizer', '100.00', 30),
(14, 'Diazepam', 'Novartis', '100.00', 24),
(15, 'Metformin HCl', 'Merck', '250.00', 100),
(16, 'Hydrochlorothiazide', 'AstraZeneca', '100.00', 100),
(17, 'TriNessa', 'AstraZeneca', '750.00', 10),
(19, 'Cymbalta', 'Novartis', '125.00', 24),
(20, 'Albuterol', 'Novartis', '375.00', 30),
(21, 'Lorazepam', 'Bayer', '125.00', 100),
(22, 'Glyburide', 'Merck', '250.00', 100),
(23, 'Carvedilol', 'AstraZeneca', '750.00', 24),
(24, 'Prednisone', 'AstraZeneca', '250.00', 24),
(26, 'Lisinopril', 'AstraZeneca', '750.00', 10),
(28, 'Tamsulosin HCl', 'Merck', '375.00', 30),
(32, 'Alendronate Sodium', 'Roche', '250.00', 10),
(33, 'Cheratussin AC', 'Gilead Sciences', '100.00', 30),
(34, 'Tramadol HCl', 'AstraZeneca', '250.00', 30),
(35, 'Tri-Sprintec', 'Gilead Sciences', '125.00', 24),
(36, 'Warfarin Sodium', 'Novartis', '500.00', 10),
(37, 'Celebrex', 'Gilead Sciences', '250.00', 30),
(39, 'TriNessa', 'Sanofi', '100.00', 30),
(40, 'Amlodipine Besylate', 'Sanofi', '125.00', 100),
(42, 'Risperidone', 'Gilead Sciences', '100.00', 10),
(43, 'Gabapentin', 'Roche', '100.00', 30),
(44, 'Nexium', 'Bayer', '125.00', 24),
(45, 'Oxycodone HCl', 'GlaxoSmithKline', '750.00', 100),
(46, 'Sertraline HCl', 'Gilead Sciences', '125.00', 100),
(47, 'Promethazine HCl', 'Gilead Sciences', '750.00', 30),
(48, 'Seroquel', 'GlaxoSmithKline', '375.00', 10),
(49, 'Amoxicillin Trihydrate/Potassium Clavulanate', 'Merck', '500.00', 100),
(50, 'Pravastatin Sodium', 'Gilead Sciences', '100.00', 30),
(51, 'Fluticasone Propionate', 'GlaxoSmithKline', '500.00', 30),
(52, 'Lisinopril', 'Novartis', '125.00', 30),
(53, 'Azithromycin', 'Bayer', '375.00', 10),
(54, 'Metoprolol Tartrate ', 'GlaxoSmithKline', '500.00', 30),
(56, 'Methylprednisolone', 'Pfizer', '250.00', 10),
(57, 'Allopurinol', 'Pfizer', '375.00', 24),
(58, 'Pantoprazole Sodium', 'Novartis', '500.00', 24),
(59, 'Lovaza', 'Novartis', '500.00', 100),
(60, 'Digoxin', 'GlaxoSmithKline', '375.00', 24),
(62, 'Lovaza', 'Pfizer', '750.00', 24),
(63, 'Lorazepam', 'Sanofi', '750.00', 100),
(64, 'Doxycycline Hyclate', 'Novartis', '125.00', 10),
(67, 'Lexapro', 'Novartis', '500.00', 10),
(69, 'Singulair', 'Novartis', '250.00', 100),
(70, 'Vyvanse', 'Pfizer', '250.00', 10),
(71, 'Synthroid', 'Pfizer', '250.00', 24),
(72, 'Niaspan', 'Roche', '500.00', 10),
(73, 'Metformin HCl', 'Bayer', '750.00', 24),
(74, 'Doxycycline Hyclate', 'AstraZeneca', '100.00', 10),
(75, 'Endocet', 'Sanofi', '750.00', 10),
(76, 'Lantus Solostar', 'Sanofi', '500.00', 10),
(80, 'Prednisone', 'AstraZeneca', '250.00', 24),
(81, 'Amoxicillin', 'Merck', '125.00', 30),
(82, 'Pantoprazole Sodium', 'Bayer', '750.00', 10),
(83, 'Gabapentin', 'Bayer', '250.00', 10),
(85, 'Atenolol', 'Gilead Sciences', '125.00', 30),
(86, 'Clonazepam', 'Bayer', '125.00', 10),
(87, 'Actos', 'Novartis', '750.00', 24),
(89, 'LevothyroxineSodium', 'Sanofi', '100.00', 24),
(90, 'Nuvaring', 'Pfizer', '750.00', 30),
(91, 'Lyrica', 'Bayer', '100.00', 10),
(92, 'Alprazolam', 'GlaxoSmithKline', '375.00', 10),
(94, 'Sertraline HCl', 'Bayer', '250.00', 30),
(95, 'Simvastatin', 'Bayer', '500.00', 24),
(96, 'Lorazepam', 'AstraZeneca', '375.00', 24),
(98, 'Penicillin VK', 'Novartis', '500.00', 30),
(99, 'Pantoprazole Sodium', 'Sanofi', '500.00', 10),
(103, 'Fluticasone Propionate', 'AstraZeneca', '125.00', 10),
(105, 'Promethazine HCl', 'Novartis', '250.00', 10);

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
CREATE TABLE IF NOT EXISTS `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `surname` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`id`, `surname`, `name`, `phone`) VALUES
(1, 'Ramsey', 'Kitra', '036-163-4367'),
(2, 'Lindsey', 'Meghan', '039-499-1604'),
(3, 'Kaufman', 'Knox', '082-270-6766'),
(5, 'Bates', 'Oscar', '047-688-2690'),
(7, 'Christensen', 'Cynthia', '022-304-7031'),
(8, 'Knight', 'Gwendolyn', '037-778-8878'),
(10, 'Norman', 'Lev', '085-698-4968'),
(11, 'Mosley', 'Dale', '052-921-2259'),
(12, 'Dean', 'Raymond', '082-418-5573'),
(13, 'Brennan', 'Baxter', '079-945-3489'),
(14, 'Lang', 'Price', '016-650-2948'),
(15, 'Solis', 'Medge', '062-178-3518'),
(16, 'Bender', 'Cade', '040-801-0454'),
(17, 'Merrill', 'Hedley', '027-418-6116'),
(18, 'Huff', 'Pandora', '040-246-6475'),
(19, 'Landry', 'Jerry', '063-690-3480'),
(20, 'Gill', 'Sebastian', '069-219-9506');

-- --------------------------------------------------------

--
-- Table structure for table `pharmacy`
--

DROP TABLE IF EXISTS `pharmacy`;
CREATE TABLE IF NOT EXISTS `pharmacy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL,
  `extra` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pharmacy`
--

INSERT INTO `pharmacy` (`id`, `name`, `address`, `extra`) VALUES
(8, 'Green apteka', 'Zelena, 12 str.', 15),
(9, 'Znahar', 'Levandivka, 12 str.', 30),
(10, 'АНЦ', 'проспект Бажана, 36', 20),
(12, 'АНЦ', 'вул. Гришко, 6', 20);

-- --------------------------------------------------------

--
-- Table structure for table `pharmacy_medicine`
--

DROP TABLE IF EXISTS `pharmacy_medicine`;
CREATE TABLE IF NOT EXISTS `pharmacy_medicine` (
  `id_pharmacy` int(11) NOT NULL,
  `id_medicine` int(11) NOT NULL,
  `pack_price` decimal(13,2) NOT NULL,
  `pack_quantity` int(11) NOT NULL,
  PRIMARY KEY (`id_pharmacy`,`id_medicine`),
  KEY `id_medicine` (`id_medicine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pharmacy_medicine`
--

INSERT INTO `pharmacy_medicine` (`id_pharmacy`, `id_medicine`, `pack_price`, `pack_quantity`) VALUES
(8, 3, '4.07', 60),
(8, 4, '1.73', 30),
(8, 5, '10.40', 20),
(8, 6, '5.49', 40),
(8, 8, '1.26', 20),
(8, 9, '3.83', 50),
(8, 40, '8.28', 10),
(8, 43, '1.92', 80),
(8, 45, '4.14', 10),
(8, 48, '5.55', 90),
(8, 50, '7.89', 60),
(8, 53, '1.41', 10),
(8, 54, '4.91', 100),
(8, 58, '2.55', 90),
(8, 69, '3.69', 40),
(8, 70, '4.64', 20),
(8, 71, '3.53', 20),
(8, 81, '3.09', 30),
(8, 89, '3.90', 30),
(8, 95, '1.52', 60),
(8, 98, '1.95', 30),
(8, 99, '2.46', 10),
(9, 3, '0.59', 50),
(9, 4, '1.73', 30),
(9, 5, '10.40', 20),
(9, 6, '0.95', 50),
(9, 8, '1.57', 50),
(9, 14, '4.75', 100),
(9, 19, '5.15', 60),
(9, 22, '5.70', 70),
(9, 28, '1.18', 20),
(9, 33, '3.30', 60),
(9, 36, '7.37', 60),
(9, 42, '9.80', 80),
(9, 52, '8.51', 50),
(9, 54, '0.27', 20),
(9, 56, '0.22', 70),
(9, 58, '1.56', 70),
(9, 64, '8.93', 50),
(9, 69, '9.76', 60),
(9, 70, '8.23', 30),
(9, 73, '3.22', 10),
(9, 74, '7.96', 80),
(9, 76, '3.86', 100),
(9, 82, '8.18', 60),
(9, 85, '8.22', 20),
(9, 87, '3.31', 70),
(9, 90, '2.24', 20),
(9, 95, '9.46', 100),
(9, 99, '6.28', 40),
(10, 7, '2.36', 20),
(10, 9, '0.58', 50),
(10, 10, '6.60', 10),
(10, 11, '4.70', 80),
(10, 14, '5.01', 70),
(10, 35, '1.82', 30),
(10, 36, '2.51', 10),
(10, 39, '4.00', 40),
(10, 42, '1.90', 10),
(10, 45, '0.37', 70),
(10, 46, '2.55', 10),
(10, 47, '4.02', 20),
(10, 52, '2.79', 30),
(10, 62, '1.56', 70),
(10, 70, '1.02', 50),
(10, 71, '8.44', 90),
(10, 73, '5.44', 30),
(10, 81, '6.26', 60),
(10, 86, '3.71', 50),
(10, 94, '1.88', 40),
(10, 95, '2.85', 40),
(10, 98, '1.14', 70),
(12, 3, '2.75', 50),
(12, 6, '3.38', 40),
(12, 14, '3.46', 60),
(12, 15, '9.58', 80),
(12, 16, '2.09', 20),
(12, 37, '1.72', 40),
(12, 39, '0.93', 100),
(12, 40, '0.23', 50),
(12, 42, '2.48', 10),
(12, 44, '8.90', 20),
(12, 50, '4.58', 10),
(12, 51, '0.88', 100),
(12, 69, '4.16', 70),
(12, 72, '5.94', 70),
(12, 82, '0.42', 40),
(12, 89, '4.79', 50),
(12, 96, '5.44', 80);

-- --------------------------------------------------------

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
CREATE TABLE IF NOT EXISTS `prescription` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `id_doctor` int(11) NOT NULL,
  `id_patient` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_doctor` (`id_doctor`),
  KEY `id_patient` (`id_patient`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prescription`
--

INSERT INTO `prescription` (`id`, `date`, `id_doctor`, `id_patient`) VALUES
(1, '2016-12-29', 7, 13),
(2, '2016-12-23', 6, 17),
(3, '2016-12-01', 8, 13),
(4, '2016-12-13', 8, 12),
(5, '2016-12-03', 7, 10),
(6, '2016-12-16', 7, 17),
(7, '2016-12-16', 6, 11),
(8, '2016-12-13', 1, 12),
(9, '2016-11-06', 6, 11),
(10, '2016-11-04', 1, 17),
(11, '2016-12-01', 6, 11),
(12, '2016-11-14', 6, 13),
(13, '2016-12-22', 7, 14),
(14, '2016-11-10', 7, 11),
(15, '2016-12-20', 1, 13),
(16, '2016-12-09', 1, 11),
(17, '2016-11-17', 8, 1),
(18, '2016-12-18', 8, 16),
(19, '2016-12-17', 1, 17),
(20, '2016-12-29', 1, 13),
(21, '2016-11-26', 8, 11),
(22, '2016-12-08', 1, 7),
(23, '2016-11-15', 7, 10),
(24, '2016-12-12', 7, 1),
(25, '2016-12-26', 1, 12),
(26, '2016-12-28', 6, 1),
(27, '2016-11-21', 8, 8),
(28, '2016-11-16', 7, 5),
(29, '2016-11-27', 8, 7),
(30, '2016-11-04', 7, 17),
(31, '2016-12-27', 1, 18),
(32, '2016-11-14', 7, 18),
(33, '2016-12-20', 6, 8),
(34, '2016-12-10', 8, 15),
(35, '2016-12-13', 7, 1),
(36, '2016-12-10', 1, 15),
(37, '2016-11-08', 8, 14),
(38, '2016-11-14', 7, 14),
(39, '2016-12-21', 6, 8),
(40, '2016-12-26', 1, 1),
(41, '2016-11-03', 7, 7),
(42, '2016-12-24', 7, 5),
(43, '2016-11-28', 7, 11),
(44, '2016-12-25', 8, 11),
(45, '2016-11-27', 6, 19),
(46, '2016-11-30', 8, 5),
(47, '2016-11-13', 8, 2),
(48, '2016-11-29', 6, 19),
(49, '2016-11-01', 6, 19),
(50, '2016-12-21', 8, 10),
(51, '2016-12-25', 6, 12),
(52, '2016-11-28', 8, 20),
(53, '2016-11-03', 7, 8),
(54, '2016-12-24', 6, 12),
(55, '2016-12-21', 6, 10),
(56, '2016-12-11', 6, 12),
(57, '2016-11-08', 8, 2),
(58, '2016-12-21', 1, 8),
(59, '2016-11-28', 8, 7),
(60, '2016-12-27', 7, 11),
(61, '2016-12-18', 1, 10),
(62, '2016-11-21', 6, 20),
(63, '2016-12-21', 7, 19),
(64, '2016-11-20', 6, 10),
(65, '2016-12-16', 6, 11),
(66, '2016-11-25', 1, 18),
(67, '2016-12-09', 8, 7),
(68, '2016-11-05', 6, 1),
(69, '2016-12-11', 8, 14),
(70, '2016-11-18', 7, 18),
(71, '2016-11-03', 7, 16),
(72, '2016-11-07', 1, 7),
(73, '2016-12-25', 1, 16),
(74, '2016-12-04', 8, 17),
(75, '2016-12-13', 8, 10),
(76, '2016-11-27', 1, 14),
(77, '2016-11-11', 6, 17),
(78, '2016-11-12', 8, 12),
(79, '2016-12-16', 1, 16),
(80, '2016-12-07', 1, 12),
(81, '2016-11-15', 8, 18),
(82, '2016-11-27', 1, 8),
(83, '2016-12-05', 8, 17),
(84, '2016-12-17', 7, 1),
(85, '2016-12-27', 7, 8),
(86, '2016-11-07', 6, 11),
(87, '2016-12-14', 8, 16),
(88, '2016-12-09', 8, 7),
(89, '2016-11-23', 8, 11),
(90, '2016-12-15', 8, 20),
(91, '2016-11-21', 6, 10),
(92, '2016-12-11', 8, 11),
(93, '2016-12-07', 7, 12),
(94, '2016-11-07', 7, 2),
(95, '2016-11-14', 1, 8),
(96, '2016-12-09', 8, 1),
(97, '2016-12-08', 8, 1),
(98, '2016-11-02', 7, 2),
(99, '2016-11-14', 1, 19),
(100, '2016-12-27', 8, 7);

-- --------------------------------------------------------

--
-- Table structure for table `prescr_medicine`
--

DROP TABLE IF EXISTS `prescr_medicine`;
CREATE TABLE IF NOT EXISTS `prescr_medicine` (
  `id_prescr` int(11) NOT NULL,
  `id_medicine` int(11) NOT NULL,
  `pack_quantity` int(11) NOT NULL,
  `pack_bought` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_prescr`,`id_medicine`),
  KEY `id_medicine` (`id_medicine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prescr_medicine`
--

INSERT INTO `prescr_medicine` (`id_prescr`, `id_medicine`, `pack_quantity`, `pack_bought`) VALUES
(2, 9, 1, 1),
(2, 17, 4, 2),
(3, 13, 4, 2),
(3, 14, 3, 1),
(4, 11, 8, 6),
(5, 3, 3, 2),
(5, 11, 6, 5),
(7, 3, 6, 1),
(7, 10, 4, 1),
(9, 16, 5, 3),
(10, 11, 5, 3),
(10, 15, 6, 3),
(10, 16, 2, 1),
(12, 14, 3, 1),
(15, 13, 5, 3),
(15, 82, 1, 0),
(17, 9, 3, 0),
(17, 11, 5, 1),
(18, 36, 6, 4),
(18, 43, 6, 2),
(18, 59, 4, 1),
(20, 5, 5, 1),
(20, 24, 6, 4),
(21, 8, 6, 6),
(21, 16, 5, 3),
(21, 53, 5, 0),
(22, 16, 5, 2),
(24, 8, 5, 3),
(25, 10, 1, 1),
(25, 11, 6, 3),
(25, 37, 4, 3),
(29, 5, 6, 5),
(29, 7, 4, 1),
(29, 10, 3, 2),
(29, 14, 1, 1),
(30, 2, 6, 6),
(30, 32, 3, 3),
(32, 54, 3, 3),
(34, 17, 6, 2),
(34, 32, 2, 2),
(34, 58, 1, 0),
(36, 10, 6, 6),
(36, 105, 4, 0),
(39, 4, 4, 3),
(39, 5, 3, 0),
(41, 10, 2, 1),
(45, 5, 4, 2),
(45, 7, 4, 2),
(46, 96, 5, 2),
(48, 2, 4, 4),
(48, 57, 3, 3),
(50, 12, 5, 4),
(50, 105, 2, 1),
(53, 6, 4, 2),
(56, 2, 3, 2),
(56, 7, 1, 1),
(58, 12, 5, 2),
(58, 14, 6, 5),
(61, 12, 6, 5),
(61, 17, 3, 3),
(61, 71, 3, 2),
(68, 15, 4, 3),
(69, 90, 4, 2),
(71, 13, 5, 0),
(71, 16, 3, 1),
(73, 4, 2, 1),
(73, 11, 1, 0),
(74, 4, 5, 4),
(74, 5, 5, 1),
(74, 8, 6, 6),
(74, 92, 3, 0),
(76, 7, 5, 4),
(77, 3, 6, 2),
(78, 4, 5, 3),
(78, 11, 6, 0),
(79, 3, 3, 1),
(81, 95, 3, 2),
(82, 4, 1, 1),
(82, 9, 3, 3),
(82, 10, 1, 1),
(85, 16, 5, 3),
(85, 99, 4, 1),
(87, 12, 6, 1),
(89, 12, 3, 2),
(89, 15, 5, 0),
(90, 10, 4, 2),
(91, 11, 6, 4),
(92, 11, 6, 6),
(93, 4, 6, 1),
(93, 7, 5, 4),
(94, 7, 5, 5),
(94, 11, 3, 3),
(97, 4, 3, 3),
(97, 6, 4, 2),
(97, 87, 2, 1),
(98, 8, 1, 1),
(98, 13, 6, 4),
(99, 5, 4, 2),
(99, 6, 2, 1),
(99, 14, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
CREATE TABLE IF NOT EXISTS `purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `id_patient` int(11) NOT NULL,
  `id_prescr` int(11) NOT NULL,
  `id_pharmacy` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_patient` (`id_patient`),
  KEY `id_prescr` (`id_prescr`),
  KEY `id_pharmacy` (`id_pharmacy`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`id`, `date`, `id_patient`, `id_prescr`, `id_pharmacy`) VALUES
(1, '2017-01-11', 1, 5, 8),
(2, '2017-01-22', 8, 2, 12),
(4, '2017-01-20', 17, 97, 12),
(5, '2017-01-29', 17, 91, 9),
(7, '2017-01-25', 14, 85, 10),
(8, '2017-02-08', 15, 98, 10),
(10, '2017-01-18', 16, 92, 8),
(11, '2017-02-01', 12, 3, 8),
(12, '2017-01-13', 7, 21, 8),
(13, '2017-01-02', 3, 20, 9),
(14, '2017-02-11', 13, 79, 9),
(15, '2017-02-14', 17, 91, 8),
(16, '2017-01-28', 7, 46, 8),
(17, '2017-01-06', 1, 50, 12),
(19, '2017-01-17', 13, 36, 9),
(20, '2017-01-24', 18, 73, 10),
(21, '2017-02-03', 3, 24, 10),
(22, '2017-01-30', 17, 12, 10),
(23, '2017-01-24', 8, 2, 8),
(24, '2017-01-25', 19, 94, 10),
(25, '2017-01-07', 13, 93, 12),
(28, '2017-02-12', 18, 32, 12),
(29, '2017-02-14', 3, 90, 9),
(30, '2017-01-05', 3, 20, 8),
(31, '2017-01-04', 16, 4, 9),
(33, '2017-02-05', 3, 90, 12),
(34, '2017-01-16', 1, 22, 12),
(35, '2017-01-13', 1, 5, 10),
(36, '2017-01-23', 7, 76, 12),
(37, '2017-01-03', 1, 45, 8),
(38, '2017-02-01', 2, 17, 10),
(39, '2017-01-04', 12, 56, 12),
(40, '2017-01-21', 8, 82, 10),
(41, '2017-01-24', 17, 48, 9),
(42, '2017-01-23', 19, 61, 8),
(43, '2017-01-04', 20, 71, 10),
(44, '2017-01-05', 17, 89, 12),
(45, '2017-01-20', 17, 12, 10),
(46, '2017-01-25', 12, 30, 12),
(47, '2017-01-16', 19, 61, 10),
(48, '2017-02-14', 8, 87, 10),
(52, '2017-01-05', 11, 29, 12),
(53, '2017-01-23', 10, 18, 10),
(54, '2017-01-12', 12, 99, 10),
(55, '2017-01-13', 5, 39, 9),
(56, '2017-01-07', 11, 29, 10),
(57, '2017-02-08', 14, 53, 10),
(59, '2017-01-06', 12, 3, 9),
(60, '2017-01-20', 19, 61, 8),
(61, '2017-02-01', 10, 34, 8),
(62, '2017-01-27', 12, 99, 8),
(63, '2017-02-01', 15, 98, 9),
(64, '2017-01-06', 20, 71, 8),
(65, '2017-01-25', 13, 93, 10),
(66, '2017-01-27', 17, 48, 10),
(67, '2017-02-09', 17, 68, 10),
(68, '2017-01-04', 20, 10, 12),
(69, '2017-02-12', 13, 58, 10),
(71, '2017-01-28', 7, 25, 9),
(72, '2017-01-04', 10, 34, 10),
(73, '2017-02-13', 13, 36, 9),
(75, '2017-01-24', 18, 81, 10),
(76, '2017-01-15', 18, 32, 9),
(77, '2017-01-27', 3, 20, 12),
(79, '2017-01-12', 1, 45, 12),
(80, '2017-02-11', 5, 39, 12),
(81, '2017-01-08', 14, 85, 12),
(82, '2017-02-11', 7, 76, 8),
(83, '2017-02-02', 18, 41, 9),
(84, '2017-01-05', 18, 15, 8),
(85, '2017-01-18', 16, 4, 9),
(86, '2017-02-07', 7, 76, 9),
(87, '2017-01-23', 1, 5, 12),
(88, '2017-02-06', 5, 9, 9),
(89, '2017-01-09', 7, 76, 12),
(90, '2017-01-18', 18, 81, 10),
(91, '2017-02-06', 16, 4, 10),
(92, '2017-01-09', 10, 77, 10),
(93, '2017-01-17', 13, 74, 10),
(94, '2017-01-19', 14, 69, 9),
(95, '2017-02-09', 14, 78, 8),
(98, '2017-02-12', 5, 7, 10),
(99, '2017-01-06', 20, 10, 8),
(100, '2017-01-29', 16, 4, 12);

-- --------------------------------------------------------

--
-- Table structure for table `purch_medicine`
--

DROP TABLE IF EXISTS `purch_medicine`;
CREATE TABLE IF NOT EXISTS `purch_medicine` (
  `id_purchase` int(11) NOT NULL,
  `id_medicine` int(11) NOT NULL,
  `pack_quantity` int(11) NOT NULL,
  PRIMARY KEY (`id_purchase`,`id_medicine`),
  KEY `id_medicine` (`id_medicine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purch_medicine`
--

INSERT INTO `purch_medicine` (`id_purchase`, `id_medicine`, `pack_quantity`) VALUES
(1, 57, 6),
(1, 94, 1),
(2, 10, 1),
(2, 74, 5),
(4, 22, 5),
(4, 95, 6),
(5, 14, 2),
(5, 57, 1),
(5, 76, 6),
(7, 10, 4),
(7, 63, 5),
(8, 15, 6),
(8, 85, 2),
(10, 2, 4),
(11, 3, 3),
(11, 22, 4),
(11, 33, 6),
(11, 48, 1),
(13, 13, 1),
(13, 50, 4),
(13, 54, 3),
(13, 67, 5),
(13, 69, 5),
(14, 6, 3),
(14, 12, 2),
(14, 91, 1),
(15, 3, 1),
(15, 17, 2),
(15, 43, 6),
(16, 24, 4),
(16, 47, 4),
(16, 99, 2),
(17, 14, 6),
(17, 51, 3),
(19, 8, 4),
(19, 28, 3),
(19, 40, 3),
(20, 89, 1),
(21, 43, 5),
(22, 89, 1),
(23, 48, 1),
(24, 99, 3),
(25, 21, 4),
(25, 75, 2),
(25, 95, 6),
(28, 7, 6),
(28, 87, 2),
(29, 47, 5),
(31, 7, 6),
(31, 47, 4),
(34, 2, 6),
(34, 52, 6),
(34, 75, 3),
(35, 32, 3),
(35, 96, 1),
(36, 6, 1),
(36, 22, 4),
(38, 32, 3),
(38, 60, 3),
(38, 103, 1),
(39, 54, 2),
(39, 75, 5),
(40, 4, 1),
(40, 33, 5),
(40, 43, 3),
(40, 94, 4),
(42, 2, 2),
(43, 80, 5),
(44, 11, 6),
(44, 39, 3),
(44, 58, 2),
(44, 70, 3),
(44, 73, 1),
(44, 76, 3),
(44, 103, 5),
(45, 44, 1),
(46, 51, 6),
(46, 72, 2),
(47, 75, 2),
(47, 98, 4),
(48, 4, 5),
(48, 23, 3),
(48, 43, 4),
(53, 22, 1),
(54, 46, 5),
(54, 81, 5),
(54, 103, 1),
(55, 40, 5),
(55, 80, 4),
(56, 62, 4),
(57, 15, 6),
(60, 6, 1),
(60, 21, 6),
(60, 36, 2),
(61, 82, 4),
(62, 13, 1),
(62, 44, 3),
(62, 98, 2),
(63, 12, 6),
(63, 36, 3),
(64, 13, 2),
(64, 33, 3),
(64, 96, 5),
(66, 6, 4),
(66, 73, 5),
(66, 74, 5),
(67, 7, 1),
(67, 75, 6),
(67, 85, 6),
(67, 103, 5),
(68, 23, 1),
(68, 89, 4),
(69, 14, 5),
(69, 22, 5),
(69, 51, 1),
(69, 99, 1),
(71, 49, 6),
(71, 60, 1),
(77, 4, 5),
(79, 40, 6),
(79, 72, 2),
(79, 94, 3),
(80, 28, 2),
(80, 72, 6),
(81, 53, 4),
(81, 70, 1),
(81, 99, 6),
(83, 3, 1),
(83, 103, 4),
(84, 72, 4),
(84, 85, 6),
(84, 91, 6),
(85, 34, 2),
(85, 45, 2),
(87, 36, 4),
(89, 4, 5),
(89, 11, 2),
(89, 32, 3),
(89, 74, 2),
(89, 92, 1),
(89, 96, 6),
(90, 44, 6),
(91, 50, 2),
(92, 49, 3),
(92, 85, 2),
(92, 92, 5),
(94, 10, 2),
(94, 33, 3),
(95, 9, 1),
(95, 70, 2),
(95, 90, 6),
(99, 28, 6),
(99, 49, 5),
(99, 64, 4),
(99, 103, 6),
(100, 12, 1),
(100, 32, 1),
(100, 39, 1);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `delivery`
--
ALTER TABLE `delivery`
  ADD CONSTRAINT `delivery_ibfk_1` FOREIGN KEY (`id_pharmacy`) REFERENCES `pharmacy` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `delivery_medicine`
--
ALTER TABLE `delivery_medicine`
  ADD CONSTRAINT `delivery_medicine_ibfk_1` FOREIGN KEY (`id_delivery`) REFERENCES `delivery` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `delivery_medicine_ibfk_2` FOREIGN KEY (`id_medicine`) REFERENCES `medicine` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `pharmacy_medicine`
--
ALTER TABLE `pharmacy_medicine`
  ADD CONSTRAINT `pharmacy_medicine_ibfk_1` FOREIGN KEY (`id_pharmacy`) REFERENCES `pharmacy` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `pharmacy_medicine_ibfk_2` FOREIGN KEY (`id_medicine`) REFERENCES `medicine` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `prescription`
--
ALTER TABLE `prescription`
  ADD CONSTRAINT `prescription_ibfk_3` FOREIGN KEY (`id_doctor`) REFERENCES `doctor` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `prescription_ibfk_4` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `prescr_medicine`
--
ALTER TABLE `prescr_medicine`
  ADD CONSTRAINT `prescr_medicine_ibfk_1` FOREIGN KEY (`id_prescr`) REFERENCES `prescription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `prescr_medicine_ibfk_2` FOREIGN KEY (`id_medicine`) REFERENCES `medicine` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`id_patient`) REFERENCES `patient` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `purchase_ibfk_2` FOREIGN KEY (`id_prescr`) REFERENCES `prescription` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `purchase_ibfk_3` FOREIGN KEY (`id_pharmacy`) REFERENCES `pharmacy` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `purch_medicine`
--
ALTER TABLE `purch_medicine`
  ADD CONSTRAINT `purch_medicine_ibfk_1` FOREIGN KEY (`id_purchase`) REFERENCES `purchase` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `purch_medicine_ibfk_2` FOREIGN KEY (`id_medicine`) REFERENCES `medicine` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
