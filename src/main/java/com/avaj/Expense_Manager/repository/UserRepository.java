package com.avaj.Expense_Manager.repository;

import com.avaj.Expense_Manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    User findByResetToken(String token);
}
