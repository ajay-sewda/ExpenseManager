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
    public void createUser(User theUser) {
        User user = new User();
        user.setFirstName(theUser.getFirstName());
        user.setLastName(theUser.getLastName());
        user.setUserGroups(null);
        user.setExpenses(null);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return  userRepository.findById(userName);
    }

    @Override
    public List<Group> getUserGroups(String userName) {
        return userRepository.findById(userName).get().getUserGroups();
    }

    @Override
    public List<Expense> getUserExpense(String userName) {
        return userRepository.findById(userName).get().getExpenses();
    }
    @Override
    @Transactional
    public void updateUser(User theUser) {
        Optional<User> optionalUser = userRepository.findById(theUser.getUserName());
        optionalUser.ifPresent(user -> {
            user.setFirstName(theUser.getFirstName());
            user.setLastName(theUser.getLastName());
            userRepository.save(user);
        });
    }


    @Override
    @Transactional
    public void deleteUserByUserName(String userName) {
        userRepository.deleteById(userName);
    }





}
