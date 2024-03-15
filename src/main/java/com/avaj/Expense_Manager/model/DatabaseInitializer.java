package com.avaj.Expense_Manager.model;

import com.avaj.Expense_Manager.entity.Role;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.repository.RoleRepository;
import com.avaj.Expense_Manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if admin profile already exists
        if (userService.getUserByUserName("admin")==null) {
            // Create admin user
            User admin = new User();
            admin.setId(1L);
            admin.setFirstName("admin");
            admin.setUserName("admin@gmail.com");
            admin.setPassword("password");
            userService.createUser(admin);

            // Create roles
            Role adminRole = new Role(3L,"ROLE_ADMIN");
            Role developerRole = new Role(2L,"ROLE_DEVELOPER");
            Role userRole = new Role(1L,"ROLE_USER");

            // Save roles to the database
            roleRepository.save(adminRole);
            roleRepository.save(developerRole);
            roleRepository.save(userRole);

            userService.updateRole(admin,adminRole);
        }
    }
}
