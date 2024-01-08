package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.Role;
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
    Boolean isSettledUp(String username);
    void updateUserDetails(User theUser);
    void updateRole(User user, Role role);
    void updateUserCredentials(User theUser);
    void disableUser(String username);
    void deleteUser(String username);
//    Password reset methods
    String generateResetToken();
    void createPasswordResetTokenForUser(User user, String token);
    boolean isValidPasswordResetToken(String token);
   void resetUserPassword(String token, String newPassword);
   void changeUserPassword(String username, String newPassword);
   boolean isValidOldPassword(String username, String oldPassword);


    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
