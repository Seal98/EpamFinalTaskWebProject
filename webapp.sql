-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: webapp
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointed_medicine`
--

DROP TABLE IF EXISTS `appointed_medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appointed_medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `medicine_id` int(11) NOT NULL,
  `appointment_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ToMedicineFK_idx` (`medicine_id`),
  KEY `ToAppointmentFK_idx` (`appointment_id`),
  CONSTRAINT `ToAppointmentFK` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`),
  CONSTRAINT `ToMedicineFK` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointed_medicine`
--

LOCK TABLES `appointed_medicine` WRITE;
/*!40000 ALTER TABLE `appointed_medicine` DISABLE KEYS */;
INSERT INTO `appointed_medicine` VALUES (2,2,15),(3,1,16),(4,2,17),(5,1,20),(6,1,22),(7,1,30),(8,2,47),(9,2,57),(10,2,63),(11,2,70),(12,1,74),(13,2,79),(14,1,80),(15,1,85),(16,2,86),(17,1,88);
/*!40000 ALTER TABLE `appointed_medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointed_procedures`
--

DROP TABLE IF EXISTS `appointed_procedures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appointed_procedures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `procedure_id` int(11) NOT NULL,
  `appointment_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ToProcedureFK_idx` (`procedure_id`),
  KEY `ProcedureAppointmentFK_idx` (`appointment_id`),
  CONSTRAINT `ProcedureAppointmentFK` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`),
  CONSTRAINT `ToProcedureFK` FOREIGN KEY (`procedure_id`) REFERENCES `procedures` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointed_procedures`
--

LOCK TABLES `appointed_procedures` WRITE;
/*!40000 ALTER TABLE `appointed_procedures` DISABLE KEYS */;
INSERT INTO `appointed_procedures` VALUES (3,2,11),(4,2,13),(5,2,18),(6,3,21),(7,3,23),(8,2,24),(9,1,26),(10,2,28),(11,3,29),(12,1,31),(13,2,36),(14,3,38),(15,2,40),(16,2,43),(17,3,45),(18,2,46),(19,3,48),(20,2,51),(21,2,52),(22,2,53),(23,2,54),(24,3,55),(25,2,56),(26,2,60),(27,1,61),(28,1,62),(29,2,64),(30,1,65),(31,2,66),(32,2,69),(33,2,73),(34,2,76),(35,2,82),(36,2,87),(37,1,89);
/*!40000 ALTER TABLE `appointed_procedures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointed_surgeries`
--

DROP TABLE IF EXISTS `appointed_surgeries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appointed_surgeries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `surgery_id` int(11) NOT NULL,
  `appointment_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ToSurgeryFK_idx` (`surgery_id`),
  KEY `ToAppointmentFK_idx` (`appointment_id`),
  CONSTRAINT `SurgeryAppointmentFK` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`),
  CONSTRAINT `ToSurgeryFK` FOREIGN KEY (`surgery_id`) REFERENCES `surgeries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointed_surgeries`
--

LOCK TABLES `appointed_surgeries` WRITE;
/*!40000 ALTER TABLE `appointed_surgeries` DISABLE KEYS */;
INSERT INTO `appointed_surgeries` VALUES (2,1,14),(3,1,25),(4,1,27),(5,1,32),(6,1,33),(7,1,34),(8,1,35),(9,1,37),(10,1,39),(11,1,41),(12,1,42),(13,1,44),(14,1,49),(15,1,50),(16,1,58),(17,1,59),(18,1,67),(19,1,68),(20,1,71),(21,1,72),(22,1,75),(23,1,77),(24,1,78),(25,1,81),(26,1,83),(27,1,84);
/*!40000 ALTER TABLE `appointed_surgeries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appointments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `appointed_doctor_id` int(11) NOT NULL,
  `executor_type` varchar(45) NOT NULL,
  `procedures` tinyint(4) NOT NULL,
  `medicine` tinyint(4) NOT NULL,
  `surgery` tinyint(4) NOT NULL,
  `completion_status` varchar(45) NOT NULL DEFAULT 'not completed',
  PRIMARY KEY (`id`),
  KEY `AppointmentPatientFK_idx` (`patient_id`),
  KEY `AppointedDoctorFK_idx` (`appointed_doctor_id`),
  CONSTRAINT `AppointedDoctorFK` FOREIGN KEY (`appointed_doctor_id`) REFERENCES `doctors` (`id`),
  CONSTRAINT `AppointmentPatientFK` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (11,4,1,'doctor',1,0,0,'canceled'),(13,3,1,'doctor',1,0,0,'canceled'),(14,3,1,'doctor',0,0,1,'completed'),(15,3,1,'nurse',0,1,0,'completed'),(16,14,1,'nurse',0,1,0,'completed'),(17,13,1,'nurse',0,1,0,'completed'),(18,5,1,'doctor',1,0,0,'canceled'),(20,3,1,'nurse',0,1,0,'completed'),(21,5,1,'doctor',1,0,0,'completed'),(22,4,1,'nurse',0,1,0,'completed'),(23,12,1,'doctor',1,0,0,'completed'),(24,12,1,'doctor',1,0,0,'completed'),(25,5,1,'doctor',0,0,1,'canceled'),(26,5,1,'doctor',1,0,0,'canceled'),(27,4,1,'doctor',0,0,1,'completed'),(28,13,1,'doctor',1,0,0,'canceled'),(29,13,1,'doctor',1,0,0,'canceled'),(30,5,1,'nurse',0,1,0,'completed'),(31,12,1,'doctor',1,0,0,'completed'),(32,13,1,'doctor',0,0,1,'canceled'),(33,3,1,'doctor',0,0,1,'canceled'),(34,3,1,'doctor',0,0,1,'completed'),(35,4,1,'doctor',0,0,1,'canceled'),(36,5,1,'doctor',1,0,0,'canceled'),(37,12,1,'doctor',0,0,1,'completed'),(38,4,1,'nurse',1,0,0,'completed'),(39,12,1,'doctor',0,0,1,'completed'),(40,11,7,'nurse',1,0,0,'completed'),(41,6,7,'doctor',0,0,1,'not completed'),(42,7,7,'doctor',0,0,1,'completed'),(43,9,7,'nurse',1,0,0,'completed'),(44,4,1,'doctor',0,0,1,'completed'),(45,13,1,'doctor',1,0,0,'completed'),(46,13,1,'nurse',1,0,0,'canceled'),(47,15,1,'nurse',0,1,0,'canceled'),(48,6,7,'nurse',1,0,0,'completed'),(49,9,7,'doctor',0,0,1,'not completed'),(50,11,7,'doctor',0,0,1,'completed'),(51,9,7,'nurse',1,0,0,'completed'),(52,6,7,'nurse',1,0,0,'not completed'),(53,5,1,'doctor',1,0,0,'completed'),(54,4,1,'doctor',1,0,0,'completed'),(55,14,1,'doctor',1,0,0,'completed'),(56,4,1,'nurse',1,0,0,'completed'),(57,5,1,'nurse',0,1,0,'completed'),(58,14,1,'doctor',0,0,1,'completed'),(59,3,1,'doctor',0,0,1,'completed'),(60,5,1,'doctor',1,0,0,'completed'),(61,2,1,'doctor',1,0,0,'canceled'),(62,3,1,'doctor',1,0,0,'canceled'),(63,2,1,'nurse',0,1,0,'canceled'),(64,5,1,'doctor',1,0,0,'completed'),(65,12,1,'doctor',1,0,0,'completed'),(66,3,1,'doctor',1,0,0,'canceled'),(67,13,1,'doctor',0,0,1,'canceled'),(68,14,1,'doctor',0,0,1,'completed'),(69,16,1,'doctor',1,0,0,'completed'),(70,16,1,'nurse',0,1,0,'canceled'),(71,17,1,'doctor',0,0,1,'completed'),(72,19,7,'doctor',0,0,1,'canceled'),(73,19,7,'doctor',1,0,0,'not completed'),(74,19,7,'nurse',0,1,0,'completed'),(75,13,1,'doctor',0,0,1,'canceled'),(76,20,7,'doctor',1,0,0,'not completed'),(77,11,7,'doctor',0,0,1,'canceled'),(78,20,7,'doctor',0,0,1,'canceled'),(79,3,1,'nurse',0,1,0,'canceled'),(80,14,1,'nurse',0,1,0,'canceled'),(81,3,1,'doctor',0,0,1,'canceled'),(82,5,1,'doctor',1,0,0,'canceled'),(83,16,1,'doctor',0,0,1,'canceled'),(84,12,1,'doctor',0,0,1,'canceled'),(85,12,1,'nurse',0,1,0,'completed'),(86,7,7,'nurse',0,1,0,'canceled'),(87,7,7,'nurse',1,0,0,'completed'),(88,22,7,'nurse',0,1,0,'canceled'),(89,22,7,'nurse',1,0,0,'completed');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discharge_curing_info`
--

DROP TABLE IF EXISTS `discharge_curing_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `discharge_curing_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discharge_date` date DEFAULT NULL,
  `diagnosis` varchar(90) NOT NULL,
  `final_diagnosis` varchar(45) NOT NULL,
  `patient_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `discharge_patient_id_fk_idx` (`patient_id`),
  CONSTRAINT `discharge_patient_id_fk` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discharge_curing_info`
--

LOCK TABLES `discharge_curing_info` WRITE;
/*!40000 ALTER TABLE `discharge_curing_info` DISABLE KEYS */;
INSERT INTO `discharge_curing_info` VALUES (10,'2019-01-07','Headache','Healthy',6),(11,'2019-01-07','Toothache','Healthy',10),(12,'2019-01-07','Headache','Healthy',2),(13,'2019-01-07','Headache','Healthy',19),(14,'2019-01-07','Headache','Healthy',13),(15,'2019-01-07','Headache','Healthy',14),(16,'2019-01-07','Brain problems','Untreatable',3),(17,'2019-01-07','not healthy','healthy',15),(18,'2019-01-07','not healthy','healthy',17),(19,'2019-01-10','Headache','Healthy',12),(20,'2019-01-10','Headache','Healthy',7),(21,'2019-01-10','Headache','Healthy',22);
/*!40000 ALTER TABLE `discharge_curing_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `doctors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `birthdate` date NOT NULL,
  `speciality` varchar(45) NOT NULL,
  `experience` varchar(45) NOT NULL,
  `login_data_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_data_id_UNIQUE` (`login_data_id`),
  CONSTRAINT `LoginDataDoctorFK` FOREIGN KEY (`login_data_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'Smith','Marston','1976-01-15','therapist','5 years',21),(4,'Daniel','Joshua','1981-11-11','neurosurgeon','7 years',22),(5,'Oleg','Kravchenko','1973-07-14','obstetrician','10 years',23),(6,'Meria','Korsaro','1991-12-12','back-surgeon','3 years',24),(7,'Glad','Valakas','1941-02-15','therapist','15 years',53);
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors_executors`
--

DROP TABLE IF EXISTS `doctors_executors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `doctors_executors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appointment_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `DoctorExecutorAppointmentFK_idx` (`appointment_id`),
  KEY `DoctorExecutorDoctor_idx` (`doctor_id`),
  CONSTRAINT `DoctorExecutorAppointmentFK` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`),
  CONSTRAINT `DoctorExecutorDoctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors_executors`
--

LOCK TABLES `doctors_executors` WRITE;
/*!40000 ALTER TABLE `doctors_executors` DISABLE KEYS */;
INSERT INTO `doctors_executors` VALUES (4,11,5),(5,13,6),(6,14,4),(7,18,5),(8,21,4),(9,23,4),(10,24,5),(11,25,6),(12,26,6),(13,27,4),(14,28,6),(15,29,6),(16,31,5),(17,32,6),(18,33,6),(19,34,4),(20,35,6),(21,36,5),(22,37,5),(23,39,5),(24,41,6),(25,42,4),(26,44,4),(27,45,4),(28,49,6),(29,50,4),(30,53,5),(31,54,4),(32,55,5),(33,58,5),(34,59,5),(35,60,5),(36,61,6),(37,62,6),(38,64,4),(39,65,5),(40,66,6),(41,67,5),(42,68,5),(43,69,5),(44,71,4),(45,72,6),(46,73,4),(47,75,6),(48,76,6),(49,77,5),(50,78,5),(51,81,4),(52,82,4),(53,83,4),(54,84,6);
/*!40000 ALTER TABLE `doctors_executors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `medicine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `indications` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (1,'Paracetamol','1/day'),(2,'Aspirin','2/day');
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nurses`
--

DROP TABLE IF EXISTS `nurses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `nurses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `birthdate` date NOT NULL,
  `experience` varchar(45) NOT NULL,
  `login_data_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `LoginDataNurseFK_idx` (`login_data_id`),
  CONSTRAINT `LoginDataNurseFK` FOREIGN KEY (`login_data_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurses`
--

LOCK TABLES `nurses` WRITE;
/*!40000 ALTER TABLE `nurses` DISABLE KEYS */;
INSERT INTO `nurses` VALUES (1,'Maria','Plovova','1976-05-05','10 years',35);
/*!40000 ALTER TABLE `nurses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nurses_executors`
--

DROP TABLE IF EXISTS `nurses_executors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `nurses_executors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appointment_id` int(11) NOT NULL,
  `nurse_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `NurseExecutorAppointmentFK_idx` (`appointment_id`),
  KEY `NurseExecutorNurseFK_idx` (`nurse_id`),
  CONSTRAINT `NurseExecutorAppointmentFK` FOREIGN KEY (`appointment_id`) REFERENCES `appointments` (`id`),
  CONSTRAINT `NurseExecutorNurseFK` FOREIGN KEY (`nurse_id`) REFERENCES `nurses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurses_executors`
--

LOCK TABLES `nurses_executors` WRITE;
/*!40000 ALTER TABLE `nurses_executors` DISABLE KEYS */;
INSERT INTO `nurses_executors` VALUES (7,15,1),(8,16,1),(9,17,1),(10,20,1),(11,22,1),(12,30,1),(13,38,1),(14,40,1),(15,43,1),(16,46,1),(17,47,1),(18,48,1),(19,51,1),(20,52,1),(21,56,1),(22,57,1),(23,63,1),(24,70,1),(25,74,1),(26,79,1),(27,80,1),(28,85,1),(29,86,1),(30,87,1),(31,88,1),(32,89,1);
/*!40000 ALTER TABLE `nurses_executors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `patients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `birthdate` date NOT NULL,
  `admission_date` date NOT NULL,
  `attended_doctor_id` int(11) NOT NULL,
  `login_data_id` int(11) NOT NULL,
  `discharge_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `PatientsAttendingDoctorFK_idx` (`attended_doctor_id`),
  KEY `LoginDataPatientFK_idx` (`login_data_id`),
  CONSTRAINT `LoginDataPatientFK` FOREIGN KEY (`login_data_id`) REFERENCES `users` (`id`),
  CONSTRAINT `PatientsAttendingDoctorFK` FOREIGN KEY (`attended_doctor_id`) REFERENCES `doctors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (2,'Alex','Konato','1994-05-07','1999-11-12',1,25,1),(3,'Pashok','Kunavich','2018-12-19','2018-12-19',1,41,1),(4,'Valera','Korenjko','2018-12-20','2018-12-19',1,42,0),(5,'Jo','Kun','2018-12-04','2018-12-19',1,43,0),(6,'Maksim','Dvoryakov','2018-12-04','2018-12-22',7,44,1),(7,'Alesandro','Legan','2018-12-04','2018-12-23',7,45,1),(8,'Ilya','Kovalev','2018-12-05','2018-12-23',7,46,0),(9,'Kirill','Gumilevskiy','2018-12-12','2018-12-23',7,47,0),(10,'Jojo','Reference','2018-12-11','2018-12-23',7,48,1),(11,'Tanya','Shereshek','2018-11-27','2018-12-23',7,49,0),(12,'Alexios','Karlos','2018-12-03','2018-12-23',1,50,1),(13,'Anton','Mihailov','2018-12-11','2018-12-23',1,51,1),(14,'Mika','MikaMika','2019-01-02','2019-01-02',1,52,1),(15,'Dasha','Brodskaja','2019-01-09','2019-01-06',1,54,1),(16,'Jora','Prohorchik','2019-01-10','2019-01-07',1,55,0),(17,'Dimas','Meehead','2019-01-09','2019-01-07',1,56,1),(18,'Lyosha','Kirko','2019-01-10','2019-01-07',7,57,0),(19,'Alina','Rusakovich','2019-01-10','2019-01-07',7,58,1),(20,'Alex','Karpov','2019-01-18','2019-01-07',7,59,0),(21,'Konato1','Konato','2019-01-04','2019-01-07',7,60,0),(22,'Sasha','Adereiko','2019-01-17','2019-01-10',7,61,1),(23,'Vlad','Fishbain','2019-01-11','2019-01-10',7,62,0),(24,'Vlad','Verinskiy','2019-01-11','2019-01-10',7,63,0);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `procedures`
--

DROP TABLE IF EXISTS `procedures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `procedures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procedures`
--

LOCK TABLES `procedures` WRITE;
/*!40000 ALTER TABLE `procedures` DISABLE KEYS */;
INSERT INTO `procedures` VALUES (1,'Massage','simple massage'),(2,'Baths','simple baths'),(3,'Salt baths','simple salt baths');
/*!40000 ALTER TABLE `procedures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surgeries`
--

DROP TABLE IF EXISTS `surgeries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `surgeries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `duration` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surgeries`
--

LOCK TABLES `surgeries` WRITE;
/*!40000 ALTER TABLE `surgeries` DISABLE KEYS */;
INSERT INTO `surgeries` VALUES (1,'brain surgery','4 hours');
/*!40000 ALTER TABLE `surgeries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `login` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL DEFAULT 'patient',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (21,'Smith','smith123','doctor'),(22,'Abraham','abraham123','doctor'),(23,'Malcolm','malcolm123','doctor'),(24,'Vazovskiy','vazovskiy123','doctor'),(25,'Alex','123123','patient'),(35,'Maria','maria123','nurse'),(41,'user7','123user7','patient'),(42,'valera','123valera1','patient'),(43,'joKun','123joKun1','patient'),(44,'maksLog','123qwe','patient'),(45,'aless','aless123','patient'),(46,'iliya','iliya123','patient'),(47,'KillReal','killreal123','patient'),(48,'jojo','jojo123','patient'),(49,'tanya','tanya123','patient'),(50,'alexios','alexios123','patient'),(51,'misha','misha123','patient'),(52,'mika123qwe','mika123qwe','patient'),(53,'valeron','valeron123','doctor'),(54,'daria','daria123','patient'),(55,'jora','jora123','patient'),(56,'dimasM','dimasM123','patient'),(57,'ninetail','ninetail123','patient'),(58,'alisha','alisha123','patient'),(59,'alex1231','alex1231','patient'),(60,'konato123','konato123','patient'),(61,'sashka','sashka','patient'),(62,'valdus','valdus','patient'),(63,'varvar','varvar','patient');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-10  4:15:11
