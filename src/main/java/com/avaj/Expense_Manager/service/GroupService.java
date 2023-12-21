package com.avaj.Expense_Manager.service;
import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.FinalSplit;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;

import java.util.List;

public interface GroupService {
    Group createGroup(Group theGroup);

    Group getGroupById(Long groupId);

    List<User> addUser(Long grpId, List<User> user);

    List<User> getGroupUsers(Long groupId);

    List<Expense> getGroupExpenses(Long groupId);

    List<FinalSplit> getGroupFinal(Long groupId);

    void deleteGroup(Long groupId);

    void updateGroup(Long groupId, String newGroupName, String newGroupType);
}
