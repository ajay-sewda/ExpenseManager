//package com.avaj.Expense_Manager.controller;
//import com.avaj.Expense_Manager.entity.Expense;
//import com.avaj.Expense_Manager.entity.Group;
//import com.avaj.Expense_Manager.entity.User;
//import com.avaj.Expense_Manager.service.GroupService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/group")
//public class GroupController {
//
//    private GroupService groupService;
//    @Autowired
//    public GroupController(GroupService groupService) {
//        this.groupService = groupService;
//    }
//
//    // Show group creation form
//    @GetMapping("/create")
//    public String showGroupCreationForm(Model model) {
//        model.addAttribute("group", new Group());
//        return "createGroup"; // Thymeleaf template name for group creation form
//    }
//
//    // Process group creation form
//    @PostMapping("/create")
//    public String createGroup(@Valid @ModelAttribute("group") Group group) {
//        groupService.createGroup(group);
//        return "redirect:/group/details/" + group.getId();
//    }
//
//    // Show group details
//    @GetMapping("/details/{groupId}")
//    public String groupDetails(@PathVariable Long groupId, Model model) {
//        Group group = groupService.getGroupById(groupId);
//        List<User> groupUsers = groupService.getGroupUsers(groupId);
//        List<Expense> groupExpenses = groupService.getGroupExpenses(groupId);
//        // Add group, users, expenses to the model
//        model.addAttribute("group", group);
//        model.addAttribute("groupUsers", groupUsers);
//        model.addAttribute("groupExpenses", groupExpenses);
//        return "groupDetails"; // Thymeleaf template name for group details
//    }
//
//
//
//    // Update group details
//    @PostMapping("/update/{groupId}")
//    public String updateGroup(@PathVariable Long groupId, @ModelAttribute("group") Group group) {
//        groupService.updateGroup(groupId, group.getGroupName(), group.getGroupType());
//        return "redirect:/group/details/" + groupId;
//    }
//
//    // Delete a group
//    @GetMapping("/delete/{groupId}")
//    public String deleteGroup(@PathVariable Long groupId) {
//        groupService.deleteGroup(groupId);
//        return "redirect:/group/all";
//    }
//}
//
