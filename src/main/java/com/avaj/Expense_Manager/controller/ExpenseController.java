package com.avaj.Expense_Manager.controller;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.ExpenseService;
import com.avaj.Expense_Manager.service.FinalSplitService;
import com.avaj.Expense_Manager.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/expense")
public class ExpenseController {

    private ExpenseService expenseService;
    private GroupService groupService;
    private FinalSplitService finalSplitService;
    @Autowired
    public ExpenseController(ExpenseService expenseService, GroupService groupService, FinalSplitService finalSplitService) {
        this.expenseService = expenseService;
        this.groupService = groupService;
        this.finalSplitService = finalSplitService;
    }

    @GetMapping("/create")
    public String showAddExpenseForm(@RequestParam("groupId") Long groupId, Model theModel) {
        Expense expense = new Expense();
        expense.setExpGrp(groupService.getGroupById(groupId));
        expense.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        List<User> groupUsers = groupService.getGroupUsers(groupId);
        theModel.addAttribute("groupUsers",groupUsers);
        theModel.addAttribute("expense",expense);
        return "old/addExpense";
    }

    @PostMapping("/process")
    public String addExpense(@ModelAttribute("expense") Expense expense) {
        expenseService.createExpense(expense);
        finalSplitService.updateFinalSplit(expense.getExpGrp().getId());
        return "redirect:/group/details?groupId=" + expense.getExpGrp().getId();
    }

//    @GetMapping("/edit/{id}")
//    public String showUpdateExpenseForm(@RequestParam("expenseId") long expenseId, Model model) {
//        Expense expense = expenseService.getExpenseById(expenseId);
//        model.addAttribute("expense", expense);
//        return "old/updateExpense";
//    }

    @GetMapping("/update")
    public String showUpdateExpenseForm(@RequestParam("expenseId") Long expenseId, Model theModel) {
        Expense expense = expenseService.getExpenseById(expenseId);
        List<User> groupUsers = expense.getExpGrp().getGroupUsers();
        theModel.addAttribute("groupUsers",groupUsers);
        theModel.addAttribute("expense",expense);
        return "old/updateExpense";
    }
    @PostMapping("/update")
    public String updateExpense(@Valid @ModelAttribute("expense") Expense expense) {
        expenseService.updateExpense(expense);
        finalSplitService.updateFinalSplit(expense.getExpGrp().getId());
        return "redirect:/group/details?groupId=" + expense.getExpGrp().getId();
    }

    @GetMapping("/delete")
    public String deleteExpense(@RequestParam("expenseId") long expenseId) {
        Long id=expenseService.getExpenseById(expenseId).getExpGrp().getId();
        expenseService.deleteExpenseById(expenseId);
        finalSplitService.updateFinalSplit(id);
        return "redirect:/group/details?groupId=" + id;
    }
}

