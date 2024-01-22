package com.avaj.Expense_Manager.controller;

import com.avaj.Expense_Manager.entity.FeedBack;
import com.avaj.Expense_Manager.entity.Role;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.FeedBackService;
import com.avaj.Expense_Manager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private FeedBackService feedBackService;

    public AdminController(UserService userService, FeedBackService feedBackService) {
        this.userService = userService;
        this.feedBackService = feedBackService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model theModel){
        List<User> users = userService.getAllUsers();
        List<FeedBack> feedBacks = feedBackService.getFeedBack();
        theModel.addAttribute("users",users);
        theModel.addAttribute("feedBacks",feedBacks);
        return "dashboard";
    }
    @GetMapping("/updateRole")
    public String showUpdateRoleForm(@RequestParam("userId") Long userId,Model theModel){
        User user = userService.getUserById(userId);
        theModel.addAttribute("userId",userId);
        theModel.addAttribute("user",user);
        theModel.addAttribute("role",new Role());
        return "updateRole";
    }
    @PostMapping("/updateRole")
    public String processUpdateRole(@RequestParam("userId") Long userId,@ModelAttribute("role") Role role){
        User user = userService.getUserById(userId);
        userService.updateRole(user,role);
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/disable")
    public String disable(@RequestParam("userId") Long userId){
        userService.disableUser(userService.getUserById(userId).getUserName());
        return "redirect:/admin/dashboard";    }
    @GetMapping("/enable")
    public String enable(@RequestParam("userId") Long userId){
        userService.enableUser(userService.getUserById(userId).getUserName());
        return "redirect:/admin/dashboard";
    }
    @GetMapping("/deleteFeedback")
    public String deleteFeedback(@RequestParam("feedBackId") Long feedBackId){
        feedBackService.delete(feedBackId);
        return "redirect:/admin/dashboard";
    }
}
