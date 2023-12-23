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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinalSplitServiceImpl implements FinalSplitService {

    private GroupService groupService;
    private FinalSplitRepository finalSplitRepository;

    @Autowired
    public FinalSplitServiceImpl(GroupService groupService, FinalSplitRepository finalSplitRepository) {
        this.groupService = groupService;
        this.finalSplitRepository = finalSplitRepository;
    }

    @Override
    @Transactional
    public List<FinalSplit> getFinalSplit(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        List<User> users = group.getGroupUsers();
        List<Expense> expenses = group.getExpenses();

        Map<String, Float> balanceMap = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.##");

        // Initialize balance for each user in the group
        for (User user : users) {
            balanceMap.put((user.getUserName()), 0.0f);
        }

        // Calculate individual balances based on expenses
        for (Expense expense : expenses) {
            Float totalExpense = expense.getExpAmt();
            Float splitAmount = totalExpense / (float) expense.getUsrSplitBtw().size();

            balanceMap.put(expense.getExpPaidBy(), balanceMap.get(expense.getExpPaidBy()) - totalExpense);
            for (User user : expense.getUsrSplitBtw()) {
                balanceMap.put(user.getUserName(), balanceMap.get(user.getUserName()) + splitAmount);
            }
        }

        // Generate final split transactions to settle the balances
        List<FinalSplit> finalSplits = new ArrayList<>();
        for (User debtor : users) {
            for (User creditor : users) {
                if (!debtor.equals(creditor)) {
                    Float amount = balanceMap.get(debtor.getUserName());
                    if (amount < 0) {
                        Float transferAmount = Math.min(Math.abs(amount), balanceMap.get(creditor.getUserName()));
                        if (transferAmount > 0) {
                            FinalSplit finalSplit = new FinalSplit();
                            finalSplit.setFinalPayBy(debtor.getUserName());
                            finalSplit.setFinalPayTo(creditor.getUserName());
                            finalSplit.setFinalAmt(Float.parseFloat(df.format(transferAmount)));
                            finalSplit.setFinalSplitGrp(group);
                            finalSplits.add(finalSplit);

                            balanceMap.put(debtor.getUserName(), balanceMap.get(debtor.getUserName()) + transferAmount);
                            balanceMap.put(creditor.getUserName(), balanceMap.get(creditor.getUserName()) - transferAmount);
                        }
                    }
                }
            }
        }

        // Save the final splits to the repository
        return finalSplitRepository.saveAll(finalSplits);
    }
}

