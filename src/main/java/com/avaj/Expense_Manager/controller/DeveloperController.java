package com.avaj.Expense_Manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/developer")
public class DeveloperController {
    @GetMapping("/devboard")
    public String developerDashboard(){
        return "developer dashboard";
    }
}
