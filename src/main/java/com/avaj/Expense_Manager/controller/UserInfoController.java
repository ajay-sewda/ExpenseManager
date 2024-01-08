package com.avaj.Expense_Manager.controller;

import com.avaj.Expense_Manager.entity.FeedBack;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.FeedBackService;
import com.avaj.Expense_Manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class UserInfoController {
    private UserService userService;
    private FeedBackService feedBackService;

    @Autowired
    public UserInfoController(UserService userService, FeedBackService feedBackService) {
        this.userService = userService;
        this.feedBackService = feedBackService;
    }
    @GetMapping("/edit")
    public String userDetails(Principal principal, Model theModel){
        String username = principal.getName();
        User user = userService.getUserByUserName(username);
        theModel.addAttribute("user", user);
        return "old/userDetails";
    }
    @GetMapping("/updateDetails")
    public String updateDetails(Principal principal, Model theModel){
        String username = principal.getName();
        User user = userService.getUserByUserName(username);
        theModel.addAttribute("webUser", user);
        return "old/updateDetails";
    }
    @PostMapping("/processUpdateDetails")
    public String processUpdateDetails(@ModelAttribute("webUser") User updatedUser){
        userService.updateUserDetails(updatedUser);
        return "redirect:/edit";
    }
    @GetMapping("/disable")
    public String disableProfile(Principal principal){
        String username = principal.getName();
        userService.disableUser(username);
        return "redirect:/showMyLoginPage";
    }
    @GetMapping("/delete")
    public String deleteProfile(Principal principal){
        String username = principal.getName();
        Boolean settledUp = userService.isSettledUp(username);
        if (settledUp){
            userService.deleteUser(username);
            return "redirect:/profile/feedBackForm";
        }
        return "old/notSettledUp";
    }
    @GetMapping("/feedBackForm")
    public String feedBackForm(Model theModel){
        FeedBack feedBack = new FeedBack();
        theModel.addAttribute("feedBackForm", feedBack);
        return "old/feedBackForm";
    }
    @PostMapping("/deleteAccountFeedback")
    public String processFeedBack(@ModelAttribute("feedBackForm") FeedBack feedBack){
        feedBackService.save(feedBack);
        return "redirect:/showMyLoginPage";
    }
}
