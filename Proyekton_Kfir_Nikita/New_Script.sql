use project;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Books`
--

DROP TABLE IF EXISTS `Books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Books` (
  `book_id` BIGINT NOT NULL, 
  `borrower_id` INT NOT NULL DEFAULT -1, 
  `book_name` VARCHAR(100) NOT NULL, 
  `book_subjects` VARCHAR(100) NOT NULL, 
  `book_description` VARCHAR(255) DEFAULT NULL, 
  `status` VARCHAR(20) NOT NULL, 
  `loan_date` DATE DEFAULT NULL, 
  `return_date` DATE DEFAULT NULL, 
  `location` VARCHAR(50) DEFAULT NULL, 
  `next_borrower_id` INT DEFAULT NULL,
  PRIMARY KEY (`book_id`, `borrower_id`),
  CONSTRAINT `Books_ibfk_1` FOREIGN KEY (`borrower_id`) REFERENCES `Subscriber` (`subscriber_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Books`
--

LOCK TABLES `Books` WRITE;
/*!40000 ALTER TABLE `Books` DISABLE KEYS */;
INSERT INTO `Books` VALUES
(9780061120084, 0, 'To Kill a Mockingbird – Harper Lee', 'fiction,classic,law', 'A novel about racial injustice in the Deep South', 'available', NULL, NULL, '1A', NULL),
(9780141439518, 0, 'Pride and Prejudice – Jane Austen', 'romance,classic,society', 'A romantic novel about manners and marriage', 'available', NULL, NULL, '1B', NULL),
(9780451524935, 0, '1984 – George Orwell', 'dystopia,politics,classic', 'A dystopian novel about totalitarianism', 'available', NULL, NULL, '1C', NULL),
(9780743273565, 0, 'The Great Gatsby – F. Scott Fitzgerald', 'classic,wealth,romance', 'A novel about the American dream', 'available', NULL, NULL, '1D', NULL),
(9781503280786, 0, 'Moby-Dick – Herman Melville', 'adventure,classic,sea', 'A story about a quest for a white whale', 'available', NULL, NULL, '1E', NULL),
(9780141441146, 0, 'Jane Eyre – Charlotte Brontë', 'classic,romance,drama', 'A novel about a governess and her journey', 'available', NULL, NULL, '2A', NULL),
(9780141439556, 0, 'Wuthering Heights – Emily Brontë', 'romance,gothic,classic', 'A tale of love and revenge on the Yorkshire moors', 'available', NULL, NULL, '2B', NULL),
(9780316769488, 0, 'The Catcher in the Rye – J.D. Salinger', 'classic,coming-of-age', 'A story about teenage rebellion', 'available', NULL, NULL, '2C', NULL),
(9780486415871, 0, 'Crime and Punishment – Fyodor Dostoevsky', 'philosophy,classic,crime', 'A psychological novel about guilt and redemption', 'available', NULL, NULL, '2D', NULL),
(9780060850524, 0, 'Brave New World – Aldous Huxley', 'dystopia,science fiction', 'A dystopian story about a technologically advanced world', 'available', NULL, NULL, '2E', NULL);
/*!40000 ALTER TABLE `Books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `History`
--

DROP TABLE IF EXISTS `History`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `History` (
  `detailed_subscription_history` int NOT NULL,
  PRIMARY KEY (`detailed_subscription_history`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `History`
--

LOCK TABLES `History` WRITE;
/*!40000 ALTER TABLE `History` DISABLE KEYS */;
/*!40000 ALTER TABLE `History` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subscriber`
--

DROP TABLE IF EXISTS `Subscriber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Subscriber` (
  `subscriber_id` int NOT NULL,
  `subscriber_name` varchar(40) NOT NULL,
  `detailed_subscription_history` int DEFAULT NULL,
  `subscriber_phone_number` varchar(20) NOT NULL,
  `subscriber_email` varchar(40) NOT NULL,
  `password` varchar(30) NOT NULL,
  `account_status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`subscriber_id`),
  KEY `detailed_subscription_history` (`detailed_subscription_history`),
  CONSTRAINT `Subscriber_ibfk_1` FOREIGN KEY (`detailed_subscription_history`) REFERENCES `History` (`detailed_subscription_history`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subscriber`
--

LOCK TABLES `Subscriber` WRITE;
/*!40000 ALTER TABLE `Subscriber` DISABLE KEYS */;
INSERT INTO `Subscriber` VALUES (1,'tester',NULL,'1234567890','123@1.com','1234','admin'),(2,'pope',NULL,'6666666666','123@.com','1234','active'),(3,'badboy',NULL,'1234512345','email@mail.c','password','frozen'),(316112176,'Kfir Cohen',NULL,'0542449218','kfirn13@gmail.com','1234','active'),(318471216,'Guy Zamir',NULL,'0546818838','zamir123321@gmail.com','1234','active'),(319006698,'Shani Fahima',NULL,'0528735203','shanii206f@gmail.com','1234','active'),(321237406,'Nikita Konovalenko',NULL,'0542181112','nikitke@gmail.com','1234','active');
/*!40000 ALTER TABLE `Subscriber` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-02 17:47:43
