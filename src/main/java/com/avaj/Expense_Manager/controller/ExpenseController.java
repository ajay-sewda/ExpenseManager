package com.avaj.Expense_Manager.controller;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.ExpenseService;
import com.avaj.Expense_Manager.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

    private ExpenseService expenseService;
    private GroupService groupService;

    @Autowired
    public ExpenseController(ExpenseService expenseService,GroupService groupService) {
        this.expenseService = expenseService;
        this.groupService =groupService;
    }

    @GetMapping("/create")
    public String showAddExpenseForm(@RequestParam("groupId") Long groupId, Model theModel) {
        Expense expense = new Expense();
        expense.setExpGrp(groupService.getGroupById(groupId));
        List<User> groupUsers = groupService.getGroupUsers(groupId);
        theModel.addAttribute("groupUsers",groupUsers);
        theModel.addAttribute("expense",expense);
        return "old/addExpense"; // Assuming addExpense.html Thymeleaf template exists
    }

    @PostMapping("/process")
    public String addExpense(@ModelAttribute("expense") Expense expense) {
        expenseService.createExpense(expense);
        return "redirect:/group/details?groupId=" + expense.getExpGrp().getId();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateExpenseForm(@RequestParam("expenseId") long expenseId, Model model) {
        Expense expense = expenseService.getExpenseById(expenseId);
        model.addAttribute("expense", expense);
        return "old/updateExpense"; // Assuming updateExpense.html Thymeleaf template exists
    }

    @GetMapping("/update")
    public String showUpdateExpenseForm(@RequestParam("expenseId") Long expenseId, Model theModel) {
        Expense expense = expenseService.getExpenseById(expenseId);
        List<User> groupUsers = expense.getExpGrp().getGroupUsers();
        theModel.addAttribute("groupUsers",groupUsers);
        theModel.addAttribute("expense",expense);
        return "old/updateExpense"; // Assuming addExpense.html Thymeleaf template exists
    }
    @PostMapping("/update")
    public String updateExpense(@Valid @ModelAttribute("expense") Expense expense) {
        expenseService.updateExpense(expense);
        return "redirect:/group/details?groupId=" + expense.getExpGrp().getId();
    }

    @GetMapping("/delete")
    public String deleteExpense(@RequestParam("expenseId") long expenseId) {
        Long id=expenseService.getExpenseById(expenseId).getExpGrp().getId();
        expenseService.deleteExpenseById(expenseId);
        return "redirect:/group/details?groupId=" + id;
    }
}

