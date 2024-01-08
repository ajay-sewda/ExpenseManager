package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.*;
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
    public Boolean isSettledUp(String username) {
        User user = userRepository.findByUserName(username);
        for(Group tempGroup:user.getUserGroups()){
            for(FinalSplit tempFinalSplit:tempGroup.getFinalSplits()){
                if(user.getId()==tempFinalSplit.getFinalPayBy() || user.getId()==tempFinalSplit.getFinalPayTo()){
                    return false;
                }
            }
        }
        return true;
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
    public void updateRole(User user, Role role) {
        User theUser = userRepository.findById(user.getId()).orElse(null);
        if (theUser != null) {
            // Remove existing roles associated with the user
            theUser.getRoles().clear();
            userRepository.save(theUser);

            // Fetch the new role by ID
            Role fetchedRole = roleRepository.findById(role.getId()).orElse(null);
            if (fetchedRole != null) {
                switch (fetchedRole.getRole()) {
                    case "ROLE_USER":
                        theUser.getRoles().add(fetchedRole);
                        break;
                    case "ROLE_DEVELOPER":
                        theUser.getRoles().addAll(Arrays.asList(
                                roleRepository.findByRole("ROLE_USER"),
                                fetchedRole));
                        break;
                    case "ROLE_ADMIN":
                        theUser.getRoles().addAll(Arrays.asList(
                                roleRepository.findByRole("ROLE_USER"),
                                roleRepository.findByRole("ROLE_DEVELOPER"),
                                fetchedRole));
                        break;
                }
                userRepository.save(theUser);
            }
        }
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

    @Override
    public void disableUser(String username) {
       User user = userRepository.findByUserName(username);
       user.setEnabled(false);
       userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUserName(username);
        userRepository.deleteById(user.getId());
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
    @Override
    public String generateResetToken() {
        return UUID.randomUUID().toString(); // Generate a random UUID as the reset token
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        user.setResetToken(token);
        userRepository.save(user); // Save the user entity with the reset token
    }

    @Override
    public boolean isValidPasswordResetToken(String token) {
        User user = userRepository.findByResetToken(token);
        return user != null; // Check if user with the token exists
    }
    public boolean isValidOldPassword(String username, String oldPassword) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            return passwordEncoder.matches(oldPassword, user.getPassword());
        }
        return false;
    }
    public void changeUserPassword(String username, String newPassword) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }
    @Override
    public void resetUserPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword)); // Set the new password
            user.setResetToken(null); // Clear the reset token after password reset
            userRepository.save(user); // Save the updated user entity
        }
    }
}
