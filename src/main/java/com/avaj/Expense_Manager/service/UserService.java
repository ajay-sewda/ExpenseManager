package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {
   void createUser(User theUser);
    List<User> getAllUsers();
    Optional<User> getUserByUserName(String userName);
    List<Group> getUserGroups(String userName);
    List<Expense> getUserExpense(String userName);
    @Transactional
    void updateUser(User theUser);
    @Transactional
    void deleteUserByUserName(String userName);





}
