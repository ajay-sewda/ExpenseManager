package com.avaj.Expense_Manager.service;


import com.avaj.Expense_Manager.entity.FinalSplit;
import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.repository.FinalSplitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class FinalSplitServiceImpl implements FinalSplitService {

    private GroupService groupService;
    ExpenseService expenseService;
    UserService userService;
    private FinalSplitRepository finalSplitRepository;

    @Autowired
    public FinalSplitServiceImpl(GroupService groupService, FinalSplitRepository finalSplitRepository,ExpenseService expenseService,UserService userService) {
        this.groupService = groupService;
        this.finalSplitRepository = finalSplitRepository;
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @Override
    public FinalSplit getFinalSplitById(Long finalSplitId) {
        return finalSplitRepository.findById(finalSplitId).get();
    }

    @Override
    @Transactional
    public List<FinalSplit> getFinalSplit(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return finalSplitRepository.findByFinalSplitGrp(group);
    }

    @Override
    public void updateFinalSplit(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        finalSplitRepository.deleteAllByFinalSplitGrp(group);
        createFinalSplits(groupId);
    }

    @Override
    public void settleUp(FinalSplit tempFinalSplit, User user,Long groupId) {
        if(user.getId()==tempFinalSplit.getFinalPayBy()){
            Expense theExpense = new Expense();
            theExpense.setExpAmt(tempFinalSplit.getFinalAmt());
            theExpense.setDate(new Date());
            theExpense.setExpGrp(groupService.getGroupById(groupId));
            theExpense.setUsrSplitBtw(Collections.singletonList(userService.getUserById(tempFinalSplit.getFinalPayBy())));
            theExpense.setExpPaidBy(tempFinalSplit.getFinalPayTo());
            theExpense.setExpName("Settle Up Transaction:- "+userService.getUserById(tempFinalSplit.getFinalPayTo()).getFirstName()+" settled up with "+user.getFirstName());
            expenseService.createExpenseForSettleUp(theExpense);
        }
        else if(user.getId()==tempFinalSplit.getFinalPayTo()){
            Expense theExpense = new Expense();
            theExpense.setExpAmt(tempFinalSplit.getFinalAmt());
            theExpense.setDate(new Date());
            theExpense.setExpGrp(groupService.getGroupById(groupId));
            theExpense.setUsrSplitBtw(Collections.singletonList(userService.getUserById(tempFinalSplit.getFinalPayBy())));
            theExpense.setExpPaidBy(user.getId());
            theExpense.setExpName("Settle Up Transaction:- "+user.getFirstName()+" settled up with "+userService.getUserById(tempFinalSplit.getFinalPayBy()).getFirstName());
            expenseService.createExpenseForSettleUp(theExpense);
        }
        updateFinalSplit(groupId);
    }

    @Override
    public void createFinalSplits(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        List<User> users = group.getGroupUsers();
        List<Expense> expenses = group.getExpenses();

        Map<Long, Float> balanceMap = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.##");

        // Initialize balance for each user in the group
        for (User user : users) {
            balanceMap.put(user.getId(), 0.0f);
        }

        // Calculate individual balances based on expenses
        for (Expense expense : expenses) {
            Float totalExpense = expense.getExpAmt();
            Float splitAmount = totalExpense / (float) expense.getUsrSplitBtw().size();

            balanceMap.put(expense.getExpPaidBy(), balanceMap.get(expense.getExpPaidBy()) - totalExpense);
            for (User user : expense.getUsrSplitBtw()) {
                balanceMap.put(user.getId(), balanceMap.get(user.getId()) + splitAmount);
            }
        }

        // Generate final split transactions to settle the balances
        List<FinalSplit> finalSplits = new ArrayList<>();
        for (User debtor : users) {
            for (User creditor : users) {
                if (!debtor.equals(creditor)) {
                    Float amount = balanceMap.get(debtor.getId());
                    if (amount < 0) {
                        Float transferAmount = Math.min(Math.abs(amount), balanceMap.get(creditor.getId()));
                        if (transferAmount > 0) {
                            FinalSplit finalSplit = new FinalSplit();
                            finalSplit.setFinalPayBy(debtor.getId());
                            finalSplit.setFinalPayTo(creditor.getId());
                            finalSplit.setFinalAmt(Float.parseFloat(df.format(transferAmount)));
                            finalSplit.setFinalSplitGrp(group);
                            finalSplits.add(finalSplit);

                            balanceMap.put(debtor.getId(), balanceMap.get(debtor.getId()) + transferAmount);
                            balanceMap.put(creditor.getId(), balanceMap.get(creditor.getId()) - transferAmount);
                        }
                    }
                }
            }
        }

        // Save the final splits to the repository
        finalSplitRepository.saveAll(finalSplits);
    }

    @Override
    public void deleteFinalSplit(Long finalSplitId) {
        finalSplitRepository.deleteById(finalSplitId);
    }
}

