DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom` (
  `building` varchar(20) NOT NULL,
  `room_number` int NOT NULL,
  `capacity` int NOT NULL,
  PRIMARY KEY (`building`,`room_number`)
)

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `credits` int NOT NULL,
  `dept_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  KEY `course_FK` (`dept_name`),
  CONSTRAINT `course_FK` FOREIGN KEY (`dept_name`) REFERENCES `department` (`dept_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_check` CHECK (((`course_id` >= 100000000) and (`course_id` <= 999999999)))
)


DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `dept_name` varchar(30) NOT NULL,
  PRIMARY KEY (`dept_name`)
)

DROP TABLE IF EXISTS `instructor`;
CREATE TABLE `instructor` (
  `inst_id` int NOT NULL AUTO_INCREMENT,
  `inst_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `inst_passwd` varchar(100) NOT NULL,
  PRIMARY KEY (`inst_id`),
  KEY `instructor_FK` (`dept_name`),
  CONSTRAINT `instructor_FK` FOREIGN KEY (`dept_name`) REFERENCES `department` (`dept_name`) ON DELETE SET NULL ON UPDATE CASCADE
)

DROP TABLE IF EXISTS `major`;
CREATE TABLE `major` (
  `major_name` varchar(30) NOT NULL,
  `credit_req` int NOT NULL,
  `dept_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`major_name`),
  KEY `major_FK` (`dept_name`),
  CONSTRAINT `major_FK` FOREIGN KEY (`dept_name`) REFERENCES `department` (`dept_name`) ON DELETE CASCADE ON UPDATE CASCADE
)

DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
  `course_id` int NOT NULL,
  `sec_id` int NOT NULL,
  `semester` varchar(10) NOT NULL,
  `year` int NOT NULL,
  `selected_stu` int DEFAULT NULL,
  `max_stu` int DEFAULT NULL,
  `building` varchar(20) DEFAULT NULL,
  `room_number` int DEFAULT NULL,
  `time_slot_id` int DEFAULT NULL,
  PRIMARY KEY (`course_id`,`sec_id`,`semester`,`year`),
  KEY `sect_classroom_FK` (`building`,`room_number`),
  KEY `sec_time_FK` (`time_slot_id`),
  CONSTRAINT `section_FK` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `section_FK_1` FOREIGN KEY (`building`, `room_number`) REFERENCES `classroom` (`building`, `room_number`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `section_FK_2` FOREIGN KEY (`time_slot_id`) REFERENCES `time_slot` (`time_slot_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `section_check` CHECK ((`selected_stu` <= `max_stu`))
)

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stu_id` int NOT NULL AUTO_INCREMENT,
  `stu_name` varchar(30) NOT NULL,
  `total_cred` int NOT NULL,
  `major_name` varchar(30) DEFAULT NULL,
  `stu_passwd` varchar(100) NOT NULL,
  PRIMARY KEY (`stu_id`),
  KEY `student_FK` (`major_name`),
  CONSTRAINT `student_FK` FOREIGN KEY (`major_name`) REFERENCES `major` (`major_name`) ON DELETE SET NULL ON UPDATE CASCADE
)

DROP TABLE IF EXISTS `takes`;
CREATE TABLE `takes` (
  `stu_id` int NOT NULL,
  `course_id` int NOT NULL,
  `sec_id` int NOT NULL,
  `semester` varchar(10) NOT NULL,
  `year` int NOT NULL,
  `grade` int DEFAULT NULL,
  PRIMARY KEY (`stu_id`,`course_id`,`sec_id`,`semester`,`year`),
  KEY `takes_FK_1` (`stu_id`),
  KEY `takes_FK_section` (`course_id`,`sec_id`,`semester`,`year`),
  CONSTRAINT `takes_FK` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `takes_FK_section` FOREIGN KEY (`course_id`, `sec_id`, `semester`, `year`) REFERENCES `section` (`course_id`, `sec_id`, `semester`, `year`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `takes_check` CHECK (((`grade` >= 0) and (`grade` <= 100)))
) 

DROP TABLE IF EXISTS `teaches`;
CREATE TABLE `teaches` (
  `inst_id` int NOT NULL,
  `course_id` int NOT NULL,
  `sec_id` int NOT NULL,
  `semester` varchar(10) NOT NULL,
  `year` int NOT NULL,
  PRIMARY KEY (`inst_id`,`course_id`,`sec_id`,`semester`,`year`),
  KEY `teaches_FK_1` (`course_id`,`sec_id`,`semester`,`year`),
  CONSTRAINT `teaches_FK` FOREIGN KEY (`inst_id`) REFERENCES `instructor` (`inst_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teaches_FK_1` FOREIGN KEY (`course_id`, `sec_id`, `semester`, `year`) REFERENCES `section` (`course_id`, `sec_id`, `semester`, `year`) ON DELETE CASCADE ON UPDATE CASCADE
)

DROP TABLE IF EXISTS `time_slot`;
CREATE TABLE `time_slot` (
  `time_slot_id` int NOT NULL,
  `day` varchar(10) NOT NULL,
  `start_hr` int NOT NULL,
  `start_min` int NOT NULL,
  `end_hr` int DEFAULT NULL,
  `end_min` int DEFAULT NULL,
  PRIMARY KEY (`time_slot_id`)
)