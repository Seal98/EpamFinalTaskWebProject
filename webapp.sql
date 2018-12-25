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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointed_medicine`
--

LOCK TABLES `appointed_medicine` WRITE;
/*!40000 ALTER TABLE `appointed_medicine` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointed_procedures`
--

LOCK TABLES `appointed_procedures` WRITE;
/*!40000 ALTER TABLE `appointed_procedures` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointed_surgeries`
--

LOCK TABLES `appointed_surgeries` WRITE;
/*!40000 ALTER TABLE `appointed_surgeries` DISABLE KEYS */;
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
  `procedure` tinyint(4) NOT NULL,
  `medicine` tinyint(4) NOT NULL,
  `surgery` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `AppointmentPatientFK_idx` (`patient_id`),
  KEY `AppointedDoctorFK_idx` (`appointed_doctor_id`),
  CONSTRAINT `AppointedDoctorFK` FOREIGN KEY (`appointed_doctor_id`) REFERENCES `doctors` (`id`),
  CONSTRAINT `AppointmentPatientFK` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curing_info`
--

DROP TABLE IF EXISTS `curing_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `curing_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `diagnosis` varchar(90) NOT NULL,
  `discharge_date` date DEFAULT NULL,
  `final diagnosis` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `patient_id_UNIQUE` (`patient_id`),
  CONSTRAINT `PatientFK` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curing_info`
--

LOCK TABLES `curing_info` WRITE;
/*!40000 ALTER TABLE `curing_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `curing_info` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'Smith','Marston','1976-01-15','surgeon','5 years',21),(4,'Daniel','Joshua','1981-11-11','neurosurgeon','7 years',22),(5,'Oleg','Kravchenko','1973-07-14','obstetrician','10 years',23),(6,'Meria','Korsaro','1991-12-12','back-surgeon','3 years',24);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors_executors`
--

LOCK TABLES `doctors_executors` WRITE;
/*!40000 ALTER TABLE `doctors_executors` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurses_executors`
--

LOCK TABLES `nurses_executors` WRITE;
/*!40000 ALTER TABLE `nurses_executors` DISABLE KEYS */;
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
  PRIMARY KEY (`id`),
  KEY `PatientsAttendingDoctorFK_idx` (`attended_doctor_id`),
  KEY `LoginDataPatientFK_idx` (`login_data_id`),
  CONSTRAINT `LoginDataPatientFK` FOREIGN KEY (`login_data_id`) REFERENCES `users` (`id`),
  CONSTRAINT `PatientsAttendingDoctorFK` FOREIGN KEY (`attended_doctor_id`) REFERENCES `doctors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (2,'Alex','Konato','1994-05-07','1999-11-12',1,25),(3,'Pashok','Kunavich','2018-12-19','2018-12-19',1,41),(4,'Valera','Korenjko','2018-12-20','2018-12-19',1,42),(5,'Jo','Kun','2018-12-04','2018-12-19',1,43),(6,'Maksim','Dvoryakov','2018-12-04','2018-12-22',1,44),(7,'Alesandro','Legan','2018-12-04','2018-12-23',1,45),(8,'Ilya','Kovalev','2018-12-05','2018-12-23',1,46),(9,'Kirill','Gumilevskiy','2018-12-12','2018-12-23',1,47),(10,'Jojo','Reference','2018-12-11','2018-12-23',1,48),(11,'Tanya','Shereshek','2018-11-27','2018-12-23',1,49),(12,'Alexios','Karlos','2018-12-03','2018-12-23',1,50),(13,'Anton','Mihailov','2018-12-11','2018-12-23',1,51);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `procedures`
--

LOCK TABLES `procedures` WRITE;
/*!40000 ALTER TABLE `procedures` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surgeries`
--

LOCK TABLES `surgeries` WRITE;
/*!40000 ALTER TABLE `surgeries` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (21,'Smith','smith123','doctor'),(22,'Abraham','abraham123','doctor'),(23,'Malcolm','malcolm123','doctor'),(24,'Vazovskiy','vazovskiy123','doctor'),(25,'Alex','123123','patient'),(35,'Maria','maria123','nurse'),(41,'user7','123user7','patient'),(42,'valera','123valera1','patient'),(43,'joKun','123joKun1','patient'),(44,'maksLog','123qwe','patient'),(45,'aless','aless123','patient'),(46,'iliya','iliya123','patient'),(47,'KillReal','killreal123','patient'),(48,'jojo','jojo123','patient'),(49,'tanya','tanya123','patient'),(50,'alexios','alexios123','patient'),(51,'misha','misha123','patient');
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

-- Dump completed on 2018-12-25 22:33:37
