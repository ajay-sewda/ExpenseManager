package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    
    @Autowired
    public  void UserServiceImpl(UserRepository theUserRepository){
        this.userRepository = theUserRepository;
    }

    @Override
    @Transactional
    public User createUser(User theUser) {
        User user = new User();
        user.setFirstName(theUser.getFirstName());
        user.setLastName(theUser.getLastName());
        user.setUserGroups(null);
        user.setExpenses(null);
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
    public void updateUserById(User theUser) {
        Optional<User> optionalUser = userRepository.findById(theUser.getId());
        optionalUser.ifPresent(user -> {
            user.setFirstName(theUser.getFirstName());
            user.setLastName(theUser.getLastName());
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
