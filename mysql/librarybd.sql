/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.16-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: librarybd
-- ------------------------------------------------------
-- Server version	10.11.16-MariaDB-ubu2204

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `OrderBookEntity`
--

DROP TABLE IF EXISTS `OrderBookEntity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderBookEntity` (
  `idOrderBook` int(11) NOT NULL AUTO_INCREMENT,
  `dateOrder` datetime(6) DEFAULT NULL,
  `orderBookStatus` tinyint(4) DEFAULT NULL,
  `id_client` varchar(36) NOT NULL,
  PRIMARY KEY (`idOrderBook`),
  KEY `FK_OB_CLIENT` (`id_client`),
  CONSTRAINT `FK_OB_CLIENT` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderBookEntity`
--

LOCK TABLES `OrderBookEntity` WRITE;
/*!40000 ALTER TABLE `OrderBookEntity` DISABLE KEYS */;
INSERT INTO `OrderBookEntity` VALUES
(1,'2026-04-20 23:22:53.000000',1,'2761547a-d4d0-4a8d-9464-2eed033a54e3'),
(2,'2026-04-20 23:23:50.000000',1,'b44f9ff2-4b16-4ab1-8654-104ca585d7f8'),
(3,'2026-04-25 21:34:51.000000',1,'3d288b4b-4ce0-45d7-8c62-0381f6ac3f9f');
/*!40000 ALTER TABLE `OrderBookEntity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id_book` varchar(36) NOT NULL,
  `author` varchar(255) NOT NULL,
  `book_status` enum('AVAILABLE','BORROWED') NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_book`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES
('0597d26e-2a91-4d2a-853a-6a693f30aef4','Jose Caceres','AVAILABLE','98789','Head First Design Patterns',0),
('430fc4bf-f798-4c34-8c5a-836b3ebf121b','Deitel & Deitel','BORROWED','987','Java pro',1),
('4ae40285-de54-4cb4-8597-eedbeb8dacdf','Joshua Bloch','BORROWED','9878','Effective Java ',1),
('5ea11e29-87ed-42c7-9dc4-f284b802d8c5','Katty sierra','BORROWED','123','java 8 cetificacion',1),
('9690a376-9654-4397-9278-ca98dd7cbaa0','Emprender un negocio para dummies','BORROWED','12345','Colin Barrow',2),
('a172b6ed-4ccf-4a70-aebc-d6340333b427','Josh Kaufman','BORROWED','1234','Mi primer Mba',1);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id_client` varchar(36) NOT NULL,
  `age` int(11) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `name` varchar(55) NOT NULL,
  `surname` varchar(55) NOT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES
('2761547a-d4d0-4a8d-9464-2eed033a54e3',40,'1715842256','jose','caceres'),
('3d288b4b-4ce0-45d7-8c62-0381f6ac3f9f',20,'1716565','Benjamin','caceres'),
('b44f9ff2-4b16-4ab1-8654-104ca585d7f8',42,'1718317561',' monica','vallejo'),
('d48c9cf4-5ebb-4037-a8f5-581f82056202',18,'1715842257','Mateo','caceres');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_book_books`
--

DROP TABLE IF EXISTS `order_book_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_book_books` (
  `id_order_book` int(11) NOT NULL,
  `id_book` varchar(36) NOT NULL,
  KEY `FK49lwd41ou34x94cfd9dap78v7` (`id_book`),
  KEY `FKksw2271q1v1k7d5j5jxr54nq0` (`id_order_book`),
  CONSTRAINT `FK49lwd41ou34x94cfd9dap78v7` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`),
  CONSTRAINT `FKksw2271q1v1k7d5j5jxr54nq0` FOREIGN KEY (`id_order_book`) REFERENCES `OrderBookEntity` (`idOrderBook`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_book_books`
--

LOCK TABLES `order_book_books` WRITE;
/*!40000 ALTER TABLE `order_book_books` DISABLE KEYS */;
INSERT INTO `order_book_books` VALUES
(1,'5ea11e29-87ed-42c7-9dc4-f284b802d8c5'),
(1,'9690a376-9654-4397-9278-ca98dd7cbaa0'),
(2,'a172b6ed-4ccf-4a70-aebc-d6340333b427'),
(3,'430fc4bf-f798-4c34-8c5a-836b3ebf121b'),
(3,'4ae40285-de54-4cb4-8597-eedbeb8dacdf');
/*!40000 ALTER TABLE `order_book_books` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-26  4:10:15
