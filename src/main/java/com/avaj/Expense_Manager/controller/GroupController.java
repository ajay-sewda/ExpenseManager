package com.avaj.Expense_Manager.controller;
import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.FinalSplit;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.FinalSplitService;
import com.avaj.Expense_Manager.service.GroupService;
import com.avaj.Expense_Manager.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/group")
public class GroupController {

    private GroupService groupService;
    private UserService userService;
    private FinalSplitService finalSplitService;
    @Autowired
    public GroupController(GroupService groupService, UserService userService,FinalSplitService finalSplitService) {
        this.groupService = groupService;
        this.userService = userService;
        this.finalSplitService =finalSplitService;
    }

    // Show group creation form
    @GetMapping("/create")
    public String showGroupCreationForm(Model model) {
        List<User> users = userService.getAllUsers().stream()
                .filter(user -> user.getRoles().stream()
                        .noneMatch(role -> role.getRole().equals("ROLE_ADMIN") || role.getRole().equals("ROLE_DEVELOPER")))
                .collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("group", new Group());
        return "old/createGroup"; // Thymeleaf template name for group creation form
    }

    @PostMapping("/processGroup")
    public String createGroup(@Valid @ModelAttribute("group") Group group) {
        groupService.createGroup(group);
        return "redirect:/";
    }

    // Show group details
    @GetMapping("/details")
    public String groupDetails(@RequestParam("groupId") Long theId,Model theModel,Principal principal) {
        Group group = groupService.getGroupById(theId);
        finalSplitService.updateFinalSplit(theId);
        List<FinalSplit> finalSplits = finalSplitService.getFinalSplit(theId);
        Boolean settledUp = true;
        User user = userService.getUserByUserName(principal.getName());
        for(FinalSplit temp:finalSplits){
            if(temp.getFinalAmt()==0){
                finalSplitService.deleteFinalSplit(temp.getId());
                finalSplits.remove(temp);
                continue;
            }
            if(user.getId()== temp.getFinalPayBy() || user.getId()== temp.getFinalPayTo()){
                settledUp=false;
            }
        }
        // Sorting expenses based on date and ID in descending order (newest first)
        List<Expense> expenses = new ArrayList<>(group.getExpenses());
        Collections.sort(expenses, Comparator
                .comparing(Expense::getDate, Comparator.reverseOrder())
                .thenComparing(Expense::getId, Comparator.reverseOrder()));
        group.setExpenses(expenses);
        // Add group, users, expenses to the model
        theModel.addAttribute("settledUp",settledUp);
        theModel.addAttribute("finalSplit",finalSplits);
        theModel.addAttribute("group", group);
        return "old/groupDetails"; // Thymeleaf template name for group details
    }

//     Update group details
@GetMapping("/update")
public String showFormForUpdate(@RequestParam("groupId") Long theId,Model theModel){
//        Get the group from the service
    Group theGroup = groupService.getGroupById(theId);

//        add group to model
    theModel.addAttribute("group",theGroup);
//        send to form
    return "old/updateGroup";
}
//processUpdate
@PostMapping("/processUpdate")
public String updateGroup(@Valid @ModelAttribute("group") Group group) {
    groupService.updateGroup(group);
    return "redirect:/";
}

    // Delete a group
    @GetMapping("/delete")
    public String deleteGroup(@RequestParam("groupId") Long groupId) {
        List<FinalSplit> finalSplits = groupService.getGroupById(groupId).getFinalSplits();
        // Check if all amounts in finalSplits are zero
        boolean allAmountsZero = finalSplits.stream().allMatch(finalSplit -> finalSplit.getFinalAmt() == 0);

        if(allAmountsZero){
            groupService.deleteGroup(groupId);
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/addMember")
    public String addMember(@RequestParam("groupId") Long groupId, Model theModel){
        Group group = groupService.getGroupById(groupId);
        List<User> users = userService.getAllUsers();
        theModel.addAttribute("group",group);
        theModel.addAttribute("allUsers",users);
        return "old/addMember";
    }
    @PostMapping("/addMember")
    public String processAddMember(@RequestParam("groupId") Long groupId,@ModelAttribute("group") Group group){
        groupService.addUser(groupId,group.getGroupUsers());
        return "redirect:/group/details?groupId="+groupId;
    }
}

