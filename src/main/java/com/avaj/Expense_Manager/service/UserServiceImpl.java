package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.Role;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.repository.RoleRepository;
import com.avaj.Expense_Manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void createUser(User theUser) {
        User user = new User();
        user.setFirstName(theUser.getFirstName());
        user.setLastName(theUser.getLastName());
        user.setUserName(theUser.getUserName());
        user.setPassword(passwordEncoder.encode(theUser.getPassword()));
        user.setUserGroups(null);
        user.setExpenses(null);
        user.setRoles(Arrays.asList(roleRepository.findByRole("ROLE_USER")));
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
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
    public List<Expense> getUserExpenses(Long userId) {
        return userRepository.findById(userId).get().getExpenses();
    }

    @Override
    @Transactional
    public void updateUserDetails(User theUser) {
        Optional<User> optionalUser = userRepository.findById(theUser.getId());
        optionalUser.ifPresent(user -> {
            user.setFirstName(theUser.getFirstName());
            user.setLastName(theUser.getLastName());
            userRepository.save(user);
        });
    }

    @Override
    @Transactional
    public void updateUserCredentials(User theUser) {
        Optional<User> optionalUser = userRepository.findById(theUser.getId());
        optionalUser.ifPresent(user -> {
            user.setUserName(theUser.getUserName());
            user.setPassword(passwordEncoder.encode(theUser.getPassword()));
            userRepository.save(user);
        });
    }

    public User updateUser(User user){
        User theUser = userRepository.findByUserName(user.getUserName());
        theUser.setUserGroups(user.getUserGroups());
        theUser.setExpenses(user.getExpenses());
        userRepository.save(theUser);
        return theUser;
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getRole());
            authorities.add(tempAuthority);
        }

        return authorities;
    }
}
