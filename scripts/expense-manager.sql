CREATE SCHEMA `expense_manager`;

use `expense_manager`;

SET FOREIGN_KEY_CHECKS = 0;

-- User Table
CREATE TABLE `User` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(255),
    `last_name` VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `User` 
VALUES 
(1,'Ajay','Kumar');

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

-- Foreign key constraints for bi-directional relationship
ALTER TABLE `User` ADD CONSTRAINT fk_group_owner FOREIGN KEY (id) REFERENCES `Group`(id);
ALTER TABLE `Group` ADD CONSTRAINT fk_user_owner FOREIGN KEY (id) REFERENCES `User`(id);

SET FOREIGN_KEY_CHECKS = 1;