package com.avaj.Expense_Manager.controller;

import java.util.logging.Logger;

import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;

	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("webUser", new User());
		
		return "register/registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
			@Valid @ModelAttribute("webUser") User theWebUser,
			BindingResult theBindingResult,
			HttpSession session, Model theModel) {

		String firstName = theWebUser.getFirstName();
		logger.info("Processing registration form for: " + firstName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "register/registration-form";
		 }

		// check the database if user already exists
        User existing = userService.getUserByUserName(theWebUser.getUserName());
        if (existing != null){
        	theModel.addAttribute("webUser", new User());
			theModel.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "register/registration-form";
        }
        
        // create user account and store in the database
        userService.createUser(theWebUser);
        
        logger.info("Successfully created user: " + firstName);

		// place user in the web http session for later use
		session.setAttribute("user", theWebUser);

        return "register/registration-confirmation";
	}
}
