-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 04, 2017 at 02:46 PM
-- Server version: 10.1.24-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id2536617_toll`
--

-- --------------------------------------------------------

--
-- Table structure for table `accesslog`
--

CREATE TABLE `accesslog` (
  `logId` int(11) NOT NULL,
  `CNIC` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `access_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `credit`
--

CREATE TABLE `credit` (
  `credit_id` int(11) NOT NULL,
  `credit_amount` double NOT NULL,
  `CNIC` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `credit`
--

INSERT INTO `credit` (`credit_id`, `credit_amount`, `CNIC`) VALUES
(1, 125, '4213169464'),
(2, 200, '85552521');

-- --------------------------------------------------------

--
-- Table structure for table `operator`
--

CREATE TABLE `operator` (
  `CNIC` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `plazaId` int(11) NOT NULL,
  `booth_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `operator_message`
--

CREATE TABLE `operator_message` (
  `message_id` int(11) NOT NULL,
  `sender_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `message_desc` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `send_time` time NOT NULL,
  `repondent_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `reponse_time` time NOT NULL,
  `status` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `plaza`
--

CREATE TABLE `plaza` (
  `plazaId` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  `location` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `plaza`
--

INSERT INTO `plaza` (`plazaId`, `description`, `location`) VALUES
(1001, 'M9-Karachi ', 'Karachi'),
(1002, 'M9 - Hyderabad', 'Hyderabad'),
(1003, 'sukkar', 'sukkar');

-- --------------------------------------------------------

--
-- Table structure for table `tax`
--

CREATE TABLE `tax` (
  `rate` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tax`
--

INSERT INTO `tax` (`rate`) VALUES
(25);

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `ID` int(11) NOT NULL,
  `Start_plazaId` int(11) NOT NULL,
  `End_plazaId` int(11) NOT NULL,
  `vehicle_type` int(11) NOT NULL,
  `distance` double NOT NULL,
  `rate` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `toll_booth`
--

CREATE TABLE `toll_booth` (
  `booth_ID` int(11) NOT NULL,
  `lane_number` int(11) NOT NULL,
  `plazaId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `toll_booth`
--

INSERT INTO `toll_booth` (`booth_ID`, `lane_number`, `plazaId`) VALUES
(1001, 1001, 1001),
(1002, 1002, 1002),
(3001, 3, 1003);

-- --------------------------------------------------------

--
-- Table structure for table `toll_fare`
--

CREATE TABLE `toll_fare` (
  `fair_id` int(11) NOT NULL,
  `fair_amount` float NOT NULL,
  `vehicle_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `toll_fare`
--

INSERT INTO `toll_fare` (`fair_id`, `fair_amount`, `vehicle_type`) VALUES
(1, 0.75, 1),
(2, 1.23, 2),
(3, 2.45, 3),
(4, 3.5, 4),
(5, 5.5, 5);

-- --------------------------------------------------------

--
-- Table structure for table `trip`
--

CREATE TABLE `trip` (
  `Trip_ID` int(11) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `trip_date` date NOT NULL,
  `start_booth_ID` int(11) NOT NULL,
  `end_booth_ID` int(11) NOT NULL,
  `fair_id` int(11) NOT NULL,
  `Total_Fare` double NOT NULL,
  `Distance` double NOT NULL,
  `CNIC` varchar(50) NOT NULL,
  `plaza_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trip`
--

INSERT INTO `trip` (`Trip_ID`, `start_time`, `end_time`, `trip_date`, `start_booth_ID`, `end_booth_ID`, `fair_id`, `Total_Fare`, `Distance`, `CNIC`, `plaza_ID`) VALUES
(1, '11:41:38', '00:00:00', '0000-00-00', 1001, 1002, 1, 100, 0, '1011253645897', 1002),
(2, '00:00:00', '00:00:00', '0000-00-00', 1001, 1002, 3, 100, 0, '458096635423', 1002),
(3, '00:00:00', '00:00:00', '0000-00-00', 1002, 3001, 3, 100, 0, '458096635423', 1003);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `CNIC` varchar(50) NOT NULL,
  `Full_name` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `phone_Number` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `AccountNumber` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`CNIC`, `Full_name`, `Email`, `Password`, `phone_Number`, `address`, `AccountNumber`) VALUES
('00000000000000', 'Admin', 'admin@nfctoll.com', 'admin123', '0000000', 'NA', 'NA'),
('1011253645897', 'hulu', 'hulu@aol.com', 'password', '08965423', 'adddresss', ''),
('12321312', 'Pappudada', '', '', '0345asdasd', 'address', ''),
('12345', 'Pappu', 'useryt@demo.com', 'password', '0345', 'address', ''),
('13542148754369', 'newzealand', 'timhorton@mail.com', 'password', '03421548980', 'humberty', ''),
('4200028523658', 'user360', 'user360@mail.com', 'd41d8cd98f00b204e9800998ecf8427e', '03202002325', 'somewhere', ''),
('420008563325', 'user', 'user@demo.com', 'password', '52365486', 'address', ''),
('4200085633257', 'newuser', 'nuser@mail.com', 'password', '03608563218', 'Address', '1210234856'),
('4201012548856', 'navuser', 'navuser@mail.com', 'password', '03212136458', 'address', '121312546'),
('4210169538857', 'hello', 'hello@mail.com', 'polkadots', '03009806549', 'number15 street dimko jue', ''),
('421017298210', 'hamza', 'hamza@mail.com', '12345', '03212310024', 'Asdd', '123456789'),
('4213169464', 'usman', 'usman@mail.com', 'usman', '03453287183', 'hsiawjwjaoaoao', ''),
('4213666451', 'guest101', 'guest101@mail.com', 'guest101', '0345213645', 'hsiwiaoa', ''),
('458096635423', 'user2', 'user2@mail.com', 'password', '2147483647', 'pyuutdwakaoaoa', ''),
('48464331546', 'navigation drawer', 'nav@demo.com', 'password', '054213648', 'adrewsss', ''),
('85552521', 'credit test', 'ctest@mail.com', 'credits', '3453286', 'hskskka', ''),
('Array', 'Array', 'naz@mail.com', 'nazeer', 'Array', 'address', '');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `number_plate` varchar(20) NOT NULL,
  `type` int(11) NOT NULL,
  `CNIC` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`number_plate`, `type`, `CNIC`) VALUES
('asd-532', 1, '421017298210');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_type`
--

CREATE TABLE `vehicle_type` (
  `type` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle_type`
--

INSERT INTO `vehicle_type` (`type`, `name`) VALUES
(1, 'Car/Jeep/Land Cruiser/Pajero Tractor without Trolley & Equivalent'),
(2, 'Wagon up to 24 Seats/Coaster/Mini Bus up to 24 seats and Mini Trucks'),
(3, 'Buses greater than 25 seats'),
(4, '2 Axle, 3 Axle Trucks, Tractor with Trolley	'),
(5, '4/5/6 Axle Trucks (Articulated)');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accesslog`
--
ALTER TABLE `accesslog`
  ADD PRIMARY KEY (`logId`),
  ADD KEY `CNIC` (`CNIC`);

--
-- Indexes for table `credit`
--
ALTER TABLE `credit`
  ADD PRIMARY KEY (`credit_id`),
  ADD KEY `CNIC` (`CNIC`);

--
-- Indexes for table `operator`
--
ALTER TABLE `operator`
  ADD PRIMARY KEY (`CNIC`),
  ADD KEY `CNIC` (`CNIC`),
  ADD KEY `plazaId` (`plazaId`),
  ADD KEY `booth_ID` (`booth_ID`);

--
-- Indexes for table `operator_message`
--
ALTER TABLE `operator_message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `sender_id` (`sender_id`),
  ADD KEY `repondent_id` (`repondent_id`);

--
-- Indexes for table `plaza`
--
ALTER TABLE `plaza`
  ADD PRIMARY KEY (`plazaId`),
  ADD KEY `plazaId` (`plazaId`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `toll_booth`
--
ALTER TABLE `toll_booth`
  ADD PRIMARY KEY (`booth_ID`),
  ADD KEY `plazaId` (`plazaId`),
  ADD KEY `booth_ID` (`booth_ID`);

--
-- Indexes for table `toll_fare`
--
ALTER TABLE `toll_fare`
  ADD PRIMARY KEY (`fair_id`),
  ADD KEY `vehicle_type` (`vehicle_type`);

--
-- Indexes for table `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`Trip_ID`),
  ADD KEY `CNIC` (`CNIC`),
  ADD KEY `fair_id` (`fair_id`),
  ADD KEY `start_booth_ID` (`start_booth_ID`),
  ADD KEY `end_booth_ID` (`end_booth_ID`),
  ADD KEY `plaza_ID` (`plaza_ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`CNIC`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`number_plate`),
  ADD KEY `CNIC` (`CNIC`),
  ADD KEY `type` (`type`);

--
-- Indexes for table `vehicle_type`
--
ALTER TABLE `vehicle_type`
  ADD PRIMARY KEY (`type`),
  ADD KEY `type` (`type`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accesslog`
--
ALTER TABLE `accesslog`
  MODIFY `logId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `credit`
--
ALTER TABLE `credit`
  MODIFY `credit_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `operator_message`
--
ALTER TABLE `operator_message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `vehicle_type`
--
ALTER TABLE `vehicle_type`
  MODIFY `type` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `accesslog`
--
ALTER TABLE `accesslog`
  ADD CONSTRAINT `accesslog_ibfk_1` FOREIGN KEY (`CNIC`) REFERENCES `operator` (`CNIC`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `credit`
--
ALTER TABLE `credit`
  ADD CONSTRAINT `credit_ibfk_1` FOREIGN KEY (`CNIC`) REFERENCES `user` (`CNIC`);

--
-- Constraints for table `operator`
--
ALTER TABLE `operator`
  ADD CONSTRAINT `operator_ibfk_1` FOREIGN KEY (`booth_ID`) REFERENCES `toll_booth` (`booth_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `operator_ibfk_2` FOREIGN KEY (`plazaId`) REFERENCES `plaza` (`plazaId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `operator_message`
--
ALTER TABLE `operator_message`
  ADD CONSTRAINT `operator_message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `operator` (`CNIC`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `operator_message_ibfk_2` FOREIGN KEY (`repondent_id`) REFERENCES `operator` (`CNIC`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `toll_booth`
--
ALTER TABLE `toll_booth`
  ADD CONSTRAINT `toll_booth_ibfk_1` FOREIGN KEY (`plazaId`) REFERENCES `plaza` (`plazaId`);

--
-- Constraints for table `toll_fare`
--
ALTER TABLE `toll_fare`
  ADD CONSTRAINT `toll_fare_ibfk_3` FOREIGN KEY (`vehicle_type`) REFERENCES `vehicle_type` (`type`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`CNIC`) REFERENCES `user` (`CNIC`),
  ADD CONSTRAINT `trip_ibfk_2` FOREIGN KEY (`fair_id`) REFERENCES `toll_fare` (`fair_id`),
  ADD CONSTRAINT `trip_ibfk_3` FOREIGN KEY (`start_booth_ID`) REFERENCES `toll_booth` (`booth_ID`),
  ADD CONSTRAINT `trip_ibfk_4` FOREIGN KEY (`end_booth_ID`) REFERENCES `toll_booth` (`booth_ID`),
  ADD CONSTRAINT `trip_ibfk_5` FOREIGN KEY (`plaza_ID`) REFERENCES `plaza` (`plazaId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `vehicle_ibfk_1` FOREIGN KEY (`CNIC`) REFERENCES `user` (`CNIC`),
  ADD CONSTRAINT `vehicle_ibfk_2` FOREIGN KEY (`type`) REFERENCES `vehicle_type` (`type`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
