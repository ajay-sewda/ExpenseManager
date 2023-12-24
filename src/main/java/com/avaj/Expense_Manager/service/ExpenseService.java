package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.User;

import java.util.List;

public interface ExpenseService {
    void createExpense(Expense theExpense);
    Expense getExpenseById(Long expenseId);
    void updateExpense(Expense theExpense);
    void deleteExpenseById(Long expenseId);
}
