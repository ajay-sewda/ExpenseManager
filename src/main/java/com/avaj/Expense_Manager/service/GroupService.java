package com.avaj.Expense_Manager.service;
import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.FinalSplit;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;

import java.util.List;

public interface GroupService {
    void createGroup(Group theGroup);
    Group getGroupById(Long groupId);
    List<User> getGroupUsers(Long groupId);
    List<Expense> getGroupExpenses(Long groupId);
    void addUser(Long grpId, List<User> user);
    void updateGroup(Group theGroup);
    void updateTotalExpense(Group theGroup,Float expense);
    void deleteGroup(Long groupId);
}
