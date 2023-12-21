//package com.avaj.Expense_Manager.controller;
//
//import com.avaj.Expense_Manager.entity.User;
//import com.avaj.Expense_Manager.service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//    private UserService userService;
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // Show user creation form
//    @GetMapping("/create")
//    public String showUserCreationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "createUser"; // Thymeleaf template name for user creation form
//    }
//
//    // Process user creation form
//    @PostMapping("/create")
//    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "createUser";
//        }
//        userService.createUser(user);
//        return "redirect:/user/details/" + user.getId();
//    }
//
//    // Show user details
//    @GetMapping("/details/{userId}")
//    public String userDetails(@PathVariable Long userId, Model model) {
//        User user = userService.getUserById(userId);
//        model.addAttribute("user", user);
//        return "userDetails"; // Thymeleaf template name for user details
//    }
//
//    // Show all users
////    @GetMapping("/all")
////    public String allUsers(Model model) {
////        List<User> users = userService.getAllUsers();
////        model.addAttribute("users", users);
////        return "allUsers"; // Thymeleaf template name to display all users
////    }
//
//    // Update user details
//    @PostMapping("/update/{userId}")
//    public String updateUser(@PathVariable Long userId, @ModelAttribute("user") User user, Model model) {
//        userService.updateUserById(userId, user.getUserName(), user.getUserPassword());
//        return "redirect:/user/details/" + userId;
//    }
//
//    // Delete a user
//    @GetMapping("/delete/{userId}")
//    public String deleteUser(@PathVariable Long userId) {
//        userService.deleteUserById(userId);
//        return "redirect:createUser";
//    }
//}
