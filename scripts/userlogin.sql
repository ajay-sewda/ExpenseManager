USE `expense_manager`;

--
-- Table structure for table `users`
--

CREATE TABLE `loginDetails` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  user_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES `User`(id),
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: https://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: Ajay@2895
--
 
INSERT INTO `loginDetails` 
VALUES 
('ajay@gmail.com','{bcrypt}$2a$12$jq2C5gr3gNPTWeDYVyloDu/3mc1Ho1wTnwPSi0PLEV5qVhgtHGRF6',1,1);

--
-- Table structure for table `authorities`
--

CREATE TABLE `roles` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	`username` varchar(50) NOT NULL,
    `role` varchar(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES `loginDetails`(username)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `roles` 
VALUES 
(1,'ajay@gmail.com','ROLE_ADMIN');