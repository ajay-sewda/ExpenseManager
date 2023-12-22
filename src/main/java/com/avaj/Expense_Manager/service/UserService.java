package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User theUser);
    void deleteUserById(Long userId);
    void updateUserById(User theUser);
    User getUserById(Long userId);
    List<User> getAllUsers();
    List<Group> getUserGroups(Long userId);
    List<Expense> getUserExpense(Long userId);

}
