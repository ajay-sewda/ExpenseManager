CREATE SCHEMA `expense_manager`;

use `expense_manager`;

SET FOREIGN_KEY_CHECKS = 0;

-- User Table
CREATE TABLE `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(255),
    `last_name` VARCHAR(255),
    `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `User`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: https://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: Ajay@2895
--

INSERT INTO `user` (`first_name`, `last_name`,`username`,`password`,`enabled`)
VALUES 
('Ajay','Kumar','ajay','$2a$12$jq2C5gr3gNPTWeDYVyloDu/3mc1Ho1wTnwPSi0PLEV5qVhgtHGRF6',1);

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `role`
--

INSERT INTO `role` (role)
VALUES 
('ROLE_ADMIN'),
('ROLE_USER'),
('ROLE_DEVELOPER');


--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` BIGINT,
  `role_id` BIGINT,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`), 
  FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role`
VALUES 
(1, 1),
(1, 2),
(1, 3);

-- `group` Table (using backticks to handle reserved keyword)
CREATE TABLE `group` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(255),
    group_type VARCHAR(255),
    total_expense DECIMAL(20,4)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- expense Table
CREATE TABLE expense (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `description` VARCHAR(255),
    `date` DATE,
    amount DECIMAL(20,4),
    paid_by BIGINT,
    group_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES `group`(id)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- finalSplit Table
CREATE TABLE finalSplit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paid_by BIGINT,
    paid_to BIGINT,
    amount DECIMAL(20,4),
    group_id BIGINT,
    FOREIGN KEY (paid_by) REFERENCES `user`(id),
    FOREIGN KEY (paid_to) REFERENCES `user`(id),
    FOREIGN KEY (group_id) REFERENCES `group`(id)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- user_group Join Table (Many-to-Many Relationship between user and group)
CREATE TABLE user_group (
    user_id BIGINT,
    group_id BIGINT,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (group_id) REFERENCES `group`(id)
);

-- user_expense Join Table (Many-to-Many Relationship between user and expense)
CREATE TABLE user_expense (
    user_id BIGINT,
    expense_id BIGINT,
    PRIMARY KEY (user_id, expense_id),
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (expense_id) REFERENCES expense(id)
);

SET FOREIGN_KEY_CHECKS = 1;