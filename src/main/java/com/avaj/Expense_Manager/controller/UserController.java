package com.avaj.Expense_Manager.controller;

import com.avaj.Expense_Manager.entity.LoginDetail;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.LoginService;
import com.avaj.Expense_Manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private LoginService loginService;
    private UserService userService;
    @Autowired
    public UserController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService=userService;
    }

    @GetMapping("/registerUser")
    public String showUserRegsitrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registerUser"; // Thymeleaf template name for user creation form
    }
    // Show user creation form
    @GetMapping("/loginUser")
    public String showUserLoginForm(Model model) {
        model.addAttribute("user", new LoginDetail());
        return "loginUser"; // Thymeleaf template name for user creation form
    }

    // Process user creation form
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registerUser";
        }
        userService.createUser(user);
        return "redirect:/user/details/" + user.getUserName();
    }
    @PostMapping("/create")
    public String loginUser(@Valid @ModelAttribute("user") LoginDetail loginDetail, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "loginUser";
        }
        if(userService.getUserByUserName(loginDetail.getUserName())!=null &&userService.getUserByUserName(loginDetail.getUserName()).get().getPassword()==loginDetail.getPassword()){
            return "redirect:/user/details/" + loginDetail.getUserName();
        }
       else{
            return "loginUser";
        }
    }

    // Show user details
    @GetMapping("/details/{userName}")
    public String userDetails(@PathVariable String userName, Model model) {
        Optional<User> user = userService.getUserByUserName(userName);
        model.addAttribute("user", user);
        return "userDetails"; // Thymeleaf template name for user details
    }

    // Show all users
//    @GetMapping("/all")
//    public String allUsers(Model model) {
//        List<User> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "allUsers"; // Thymeleaf template name to display all users
//    }

    // Update user details
//    @PostMapping("/update/{userId}")
//    public String updateUser(@PathVariable Long userId, @ModelAttribute("user") User user, Model model) {
//        userService.updateUserById(userId, user.g, user.getUserPassword());
//        return "redirect:/user/details/" + userId;
//    }

    // Delete a user
//    @GetMapping("/delete/{userId}")
//    public String deleteUser(@PathVariable Long userId) {
//        userService.deleteUserById(userId);
//        return "redirect:createUser";
//    }
}
