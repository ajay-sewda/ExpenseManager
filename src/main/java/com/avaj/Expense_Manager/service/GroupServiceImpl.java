package com.avaj.Expense_Manager.service;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.FinalSplit;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.repository.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GroupServiceImpl implements GroupService{
    private GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public void createGroup(Group theGroup) {
        Group group = new Group();
        group.setGroupName(theGroup.getGroupName());
        group.setGroupType(theGroup.getGroupType());
        group.setGroupUsers(theGroup.getGroupUsers());
        group.setExpenses(null);
        group.setFinalSplits(null);
        groupRepository.save(group);
    }

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).get();
    }

    @Override
    public List<User> getGroupUsers(Long groupId) {
        return groupRepository.findById(groupId).get().getGroupUsers();
    }

    @Override
    public List<Expense> getGroupExpenses(Long groupId) {
        return groupRepository.findById(groupId).get().getExpenses();
    }
    @Override
    @Transactional
    public void updateGroup(Group updatedGroup) {
        Long id=updatedGroup.getId();
        Group tempGroup = groupRepository.findById(updatedGroup.getId()).get();
        tempGroup.setGroupName(updatedGroup.getGroupName());
        tempGroup.setGroupType(updatedGroup.getGroupType());
        groupRepository.save(tempGroup);
    }

    @Override
    public void updateTotalExpense(Group theGroup, Float expense) {
       Group updatedGroup = groupRepository.findById(theGroup.getId()).get();
       updatedGroup.setTotalExpense(theGroup.getTotalExpense()+expense);
       groupRepository.save(updatedGroup);
    }

    @Override
    @Transactional
    public void addUser(Long grpId, List<User> user) {
        Optional<Group> tempGroup = groupRepository.findById(grpId);
        if(tempGroup.isPresent()){
            tempGroup.get().getGroupUsers().addAll(user);
            groupRepository.save(tempGroup.get());
        }
    }

    @Override
    @Transactional
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

}
