package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    void createUser(User theUser);
    User getUserById(Long userId);
    User getUserByUserName(String userName);
    List<User> getAllUsers();
    List<Group> getUserGroups(Long userId);
    List<Expense> getUserExpenses(Long userId);
    void updateUserDetails(User theUser);
    void updateUserCredentials(User theUser);
    void deleteUserById(Long userId);


    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
