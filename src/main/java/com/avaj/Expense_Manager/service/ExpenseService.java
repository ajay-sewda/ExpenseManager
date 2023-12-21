package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.User;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(Expense theExpense);
    void deleteExpenseById(Long expenseId);
    List<Expense> getGroupExpenses(Long groupId);
    void updateExpense(Expense theExpense);
    Expense getExpenseById(Long expenseId);

}
