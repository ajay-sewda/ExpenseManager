package com.avaj.Expense_Manager.controller;

import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class UserInfoController {
    private UserService userService;

    @Autowired
    public UserInfoController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/edit")
    public String userDetails(Principal principal, Model theModel){
        String username = principal.getName();
        User user = userService.getUserByUserName(username);
        theModel.addAttribute("user", user);
        return "old/userDetails";
    }
}
