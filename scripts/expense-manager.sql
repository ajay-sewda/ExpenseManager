CREATE SCHEMA `expense_manager`;

use `expense_manager`;

SET FOREIGN_KEY_CHECKS = 0;

-- User Table
CREATE TABLE `User` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(255),
    `last_name` VARCHAR(255),
    `role` varchar(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `User` 
VALUES 
(1,'Ajay','Kumar','ROLE_ADMIN');

--
-- Table structure for table `loginDetails`
--

CREATE TABLE `loginDetails` (
`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  user_id BIGINT,
  FOREIGN KEY (user_id) REFERENCES `User`(id),
  UNIQUE KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `loginDetails`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- A generation tool is avail at: https://www.luv2code.com/generate-bcrypt-password
--
-- Default passwords here are: Ajay@2895
--
 
INSERT INTO `loginDetails` 
VALUES 
(1,'ajay@gmail.com','{noop}Ajay@2895',1,1);

-- `Group` Table (using backticks to handle reserved keyword)
CREATE TABLE `Group` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(255),
    group_type VARCHAR(255),
    total_expense DECIMAL(20,4)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Expense Table
CREATE TABLE Expense (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `description` VARCHAR(255),
    `date` DATE,
    amount DECIMAL(20,4),
    paid_by BIGINT,
    group_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES `Group`(id)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- FinalSplit Table
CREATE TABLE FinalSplit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paid_by BIGINT,
    paid_to BIGINT,
    amount DECIMAL(20,4),
    group_id BIGINT,
    FOREIGN KEY (paid_by) REFERENCES `User`(id),
    FOREIGN KEY (paid_to) REFERENCES `User`(id),
    FOREIGN KEY (group_id) REFERENCES `Group`(id)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- User_Group Join Table (Many-to-Many Relationship between User and Group)
CREATE TABLE User_Group (
    user_id BIGINT,
    group_id BIGINT,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES `User`(id),
    FOREIGN KEY (group_id) REFERENCES `Group`(id)
);

-- User_Expense Join Table (Many-to-Many Relationship between User and Expense)
CREATE TABLE User_Expense (
    user_id BIGINT,
    expense_id BIGINT,
    PRIMARY KEY (user_id, expense_id),
    FOREIGN KEY (user_id) REFERENCES `User`(id),
    FOREIGN KEY (expense_id) REFERENCES Expense(id)
);


SET FOREIGN_KEY_CHECKS = 1;