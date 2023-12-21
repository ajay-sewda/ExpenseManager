package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public  void UserServiceImpl(UserRepository theUserRepository,PasswordEncoder thePasswordEncoder){
        this.userRepository = theUserRepository;
        this.passwordEncoder = thePasswordEncoder;
    }

    @Override
    @Transactional
    public User createUser(User theUser) {
        User user = new User();
        user.setUserFirstName(theUser.getUserFirstName());
        user.setUserLastName(theUser.getUserLastName());
        user.setUserName(theUser.getUserName());
        user.setUserPassword(passwordEncoder.encode(theUser.getUserPassword()));
        user.setUserGroups(null);
        user.setRole("USER");
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void updateUserById(Long userId,String username, String password) {
        Optional<User> optionalUser = userRepository.findById(userId);

        optionalUser.ifPresent(user -> {
            user.setUserName(username);
            user.setUserPassword(passwordEncoder.encode(password)); // Remember to encode the password using PasswordEncoder

            userRepository.save(user);
        });
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Group> getUserGroups(Long userId) {
        return userRepository.findById(userId).get().getUserGroups();
    }

    @Override
    public List<Expense> getUserExpense(Long userId) {
        return userRepository.findById(userId).get().getExpenses();
    }
}
