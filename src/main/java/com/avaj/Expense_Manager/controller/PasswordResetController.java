package com.avaj.Expense_Manager.controller;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.model.PasswordForm;
import com.avaj.Expense_Manager.service.EmailService;
import com.avaj.Expense_Manager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService; // Your user service

    @Autowired
    private EmailService emailService;

    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "forgotPassword"; // Thymeleaf template for forgot password form
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, HttpServletRequest request) {
        User user = userService.getUserByUserName(email);
        if (user != null) {
            String resetToken = userService.generateResetToken(); // Generate reset token
            userService.createPasswordResetTokenForUser(user, resetToken); // Save token with user

            String resetLink = request.getRequestURL().toString() + "/resetPassword?token=" + resetToken;
            String subject = "Password Reset Request";
            String message = "To reset your password, click on the following link: " + resetLink;

            emailService.sendSimpleMessage(email, subject, message); // Send email
        }
        return "redirect:/passwordResetSuccess"; // Redirect to success page
    }
    @GetMapping("/passwordResetSuccess")
    public String showPasswordResetSuccessPage() {
        return "passwordResetSuccess"; // Return the success page
    }

    @GetMapping("/forgotPassword/resetPassword")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        // Validate token, show reset password form
        if (userService.isValidPasswordResetToken(token)) {
            model.addAttribute("token", token);
            return "resetPassword"; // Thymeleaf template for reset password form
        } else {
            return "redirect:/invalidToken"; // Handle invalid/expired token scenario
        }
    }
    @GetMapping("/invalidToken")
    public String showInvalidTokenPage() {
        return "invalidToken"; // Return the invalid token page
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
        // Reset password if token is valid
        if (userService.isValidPasswordResetToken(token)) {
            userService.resetUserPassword(token, password);
            return "redirect:/showMyLoginPage"; // Redirect to login page or success page
        } else {
            return "redirect:/invalidToken"; // Handle invalid/expired token scenario
        }
    }
    @GetMapping("/changePassword")
    public String changePassword(Model theModel){
        PasswordForm passwordForm = new PasswordForm();
        theModel.addAttribute("passwordForm",passwordForm);
        return "changePassword";
    }
    @PostMapping("/changePassword")
    public String processPassword(@ModelAttribute("passwordForm") @Valid PasswordForm passwordForm, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return "changePassword"; // Return form if there are validation errors
        }

        String username = principal.getName();
        if (!userService.isValidOldPassword(username, passwordForm.getOldPassword())) {
            bindingResult.rejectValue("oldPassword", "error.passwordForm", "Incorrect old password");
            return "changePassword";
        }

        if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.passwordForm", "Passwords do not match");
            return "changePassword";
        }

        userService.changeUserPassword(username, passwordForm.getNewPassword());
        return "redirect:/passwordChangeSuccess"; // Redirect to a success page
    }
    @GetMapping("/passwordChangeSuccess")
    public String showPasswordChangeSuccessPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Retrieve the session if it exists
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }
        return "passwordChangeSuccess"; // Return the success page
    }
}
