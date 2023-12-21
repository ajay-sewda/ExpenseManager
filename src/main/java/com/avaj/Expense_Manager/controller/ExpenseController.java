//package com.avaj.Expense_Manager.controller;
//
//import com.avaj.Expense_Manager.entity.Expense;
//import com.avaj.Expense_Manager.service.ExpenseService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/expenses")
//public class ExpenseController {
//
//    private final ExpenseService expenseService;
//
//    @Autowired
//    public ExpenseController(ExpenseService expenseService) {
//        this.expenseService = expenseService;
//    }
//
//    @GetMapping("/list")
//    public String getAllExpenses(Model model, Long groupId) {
//        List<Expense> expenses = expenseService.getGroupExpenses(groupId);
//        model.addAttribute("expenses", expenses);
//        return "expenseList"; // Assuming expenseList.html Thymeleaf template exists
//    }
//
//    @GetMapping("/add")
//    public String showAddExpenseForm(Expense expense) {
//        return "addExpense"; // Assuming addExpense.html Thymeleaf template exists
//    }
//
//    @PostMapping("/add")
//    public String addExpense(@ModelAttribute("expense") Expense expense) {
//        expenseService.createExpense(expense);
//        return "redirect:/expenses/list";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showUpdateExpenseForm(@PathVariable("id") long id, Model model) {
//        Expense expense = expenseService.getExpenseById(id);
//        model.addAttribute("expense", expense);
//        return "updateExpense"; // Assuming updateExpense.html Thymeleaf template exists
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateExpense(@PathVariable("id") long id, @Valid Expense expense,
//                                BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            expense.setId(id);
//            return "updateExpense";
//        }
//
//        expenseService.updateExpense(expense);
//        return "redirect:/expenses/list";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteExpense(@PathVariable("id") long id) {
//        expenseService.deleteExpenseById(id);
//        return "redirect:/expenses/list";
//    }
//}
//
