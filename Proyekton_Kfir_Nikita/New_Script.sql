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
  `book_id` int NOT NULL,
  `book_name` varchar(40) NOT NULL,
  `book_subjects` varchar(100) NOT NULL,
  `book_description` varchar(100) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `borrower_id` int DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `location` varchar(30) DEFAULT NULL,
  `next_borrower_id` int DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  KEY `borrower_id` (`borrower_id`),
  CONSTRAINT `Books_ibfk_1` FOREIGN KEY (`borrower_id`) REFERENCES `Subscriber` (`subscriber_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Books`
--

LOCK TABLES `Books` WRITE;
/*!40000 ALTER TABLE `Books` DISABLE KEYS */;
INSERT INTO `Books` VALUES (1,'bookname','book subject,test,subject','tests for search of description','available',NULL,NULL,'location',NULL),(2,'name','book subject,test,subject','tests for search of description','borrowed',2,'2020-12-12','location',NULL),(3,'name','book subject,test,subject','tests for search of description','borrowed',2,'2020-12-12','location1',NULL),(4,'name','book subject,test,subject','tests for search of description','borrowed',2,'2020-12-12','location2',NULL),(9981,'back','java,cool,test,whatever','book that is very cool test','borrowed',1,'2012-11-11','china',NULL),(9982,'back','java,cool,test,whatever','book that is very cool test','available',NULL,NULL,'china2',NULL),(9991,'book','java,cool,test,whatever','book that is very cool test','borrowed',2,'2069-11-11','documentary-row:1-col:420',NULL),(12341,'java for dummies','programming,java,computers','book for begginers to start programming in java','available',NULL,NULL,'computers-row:5-col:5',NULL);
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
