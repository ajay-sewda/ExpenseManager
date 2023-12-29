package com.avaj.Expense_Manager.controller;

import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
        UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
        public String showHome(Model theModel) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                // Extract the username from UserDetails
                String username = ((UserDetails) authentication.getPrincipal()).getUsername();

                // Fetch the updated user object from the service
                User updatedUser = userService.getUserByUserName(username);

                // Update the user details in the authentication (if needed)
                theModel.addAttribute("user",updatedUser);

                // Redirect to the home page
                return "home";
            } else {
                // Handle the case when there is no valid authentication
                return "redirect:/showMyLoginPage";
            }

        }

        // add a request mapping for /leaders

        @GetMapping("/developers")
        public String showLeaders() {

            return "developers";
        }

        // add request mapping for /systems

        @GetMapping("/admin")
        public String showSystems() {

            return "admin";
        }

    }
