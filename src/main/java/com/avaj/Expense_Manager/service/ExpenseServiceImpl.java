package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseRepository expenseRepository;
    private GroupService groupService;
    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, GroupService groupService) {
        this.expenseRepository = expenseRepository;
        this.groupService = groupService;
    }

    @Override
    @Transactional
    public Expense createExpense(Expense theExpense) {
        Expense expense = new Expense();
        expense.setExpName(theExpense.getExpName());
        expense.setExpAmt(theExpense.getExpAmt());
        expense.setUsrSplitBtw(theExpense.getUsrSplitBtw());
        expense.setExpPaidBy(theExpense.getExpPaidBy());
        expense.setExpGrp(theExpense.getExpGrp());
        expenseRepository.save(expense);
        return expense;
    }

    @Override
    @Transactional
    public void deleteExpenseById(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public List<Expense> getGroupExpenses(Long groupId) {
        return groupService.getGroupById(groupId).getExpenses();
    }

    @Override
    @Transactional
    public void updateExpense(Expense theExpense) {
        Expense tempExpense = expenseRepository.findById(theExpense.getId()).get();
        tempExpense.setExpName(theExpense.getExpName());
        tempExpense.setExpAmt(theExpense.getExpAmt());
        tempExpense.setUsrSplitBtw(theExpense.getUsrSplitBtw());
        tempExpense.setExpPaidBy(theExpense.getExpPaidBy());
        tempExpense.setExpGrp(theExpense.getExpGrp());
        expenseRepository.save(tempExpense);
    }

    @Override
    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).get();
    }
}
