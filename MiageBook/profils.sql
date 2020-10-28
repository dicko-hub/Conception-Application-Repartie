-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mar. 24 mars 2020 à 10:18
-- Version du serveur :  5.7.26
-- Version de PHP :  7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `book`
--

-- --------------------------------------------------------

--
-- Structure de la table `profils`
--

DROP TABLE IF EXISTS `profils`;
CREATE TABLE IF NOT EXISTS `profils` (
  `id` int(22) NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `onLigne` enum('yes','no') NOT NULL,
  `lastTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pseudo` (`pseudo`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `profils`
--

INSERT INTO `profils` (`id`, `pseudo`, `password`, `email`, `nom`, `prenom`, `onLigne`, `lastTime`) VALUES
(1, 'gilles', 'gilles', 'gilles@gmail.com', 'gilles', 'charles', 'no', '2020-03-23 19:53:29'),
(10, 'dicko', 'dicko', 'dicko@gmail.com', 'Dicko', 'Seydou Salia', 'no', '2020-03-23 19:54:27'),
(11, 'adama', 'adama', 'adama@gmail.com', 'Traore', 'Adama', 'no', '2020-03-23 19:54:17'),
(12, 'mohamed', 'mohamed', 'dicko22@gmail.com', 'Dicko', 'Mohamed', 'no', '2020-03-23 19:48:40');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
