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
import java.util.List;

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
        List<User> users = userService.getAllUsers();
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
    public String groupDetails(@RequestParam("groupId") Long theId,Model theModel) {
        Group group = groupService.getGroupById(theId);
        List<FinalSplit> finalSplits = finalSplitService.getFinalSplit(theId);
        // Add group, users, expenses to the model
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
        groupService.deleteGroup(groupId);
        return "redirect:/";
    }

//    balances
    @GetMapping("/balances")
    public String showBalances(@RequestParam("groupId") Long groupId, Model theModel,Principal principal){
        User user = userService.getUserByUserName(principal.getName());
        List<FinalSplit> finalSplits =finalSplitService.getFinalSplit(groupId);
        theModel.addAttribute("finalSplits",finalSplits);
        Group group = groupService.getGroupById(groupId);
        theModel.addAttribute("group", group);
        theModel.addAttribute("userId",user.getId());
        return "balances";
    }
    @GetMapping("/settleUp")
    public String showSettleUpPage(@RequestParam("groupId") Long groupId, Model theModel, Principal principal){
        User user = userService.getUserByUserName(principal.getName());
        List<FinalSplit> finalSplits = new ArrayList<>();
        for(FinalSplit tempFinalSplit:finalSplitService.getFinalSplit(groupId)){
            if(user.getId()==tempFinalSplit.getFinalPayTo() || user.getId()==tempFinalSplit.getFinalPayBy()){
                finalSplits.add(tempFinalSplit);
            }
        }
        Group group = groupService.getGroupById(groupId);
        theModel.addAttribute("finalSplits",finalSplits);
        theModel.addAttribute("group", group);
        theModel.addAttribute("userId",user.getId());
        return "settleUp";
    }
    @PostMapping("/settleUp")
    public String processSettleUp(@RequestParam("finalSplitId") Long finalSplitId,@ModelAttribute("finalSplits") List<FinalSplit> finalSplits){
        Long groupId = finalSplits.get(0).getFinalSplitGrp().getId();
        finalSplitService.deleteFinalSplit(finalSplitId);
        return "redirect:/group/settleUp?groupId=" + groupId;
    }
    @PostMapping("/settleUpAll")
    public String processSettleAll(@ModelAttribute("finalSplits") List<FinalSplit> finalSplits){
        Long groupId = finalSplits.get(0).getFinalSplitGrp().getId();
        for(FinalSplit tempFinalSplit: finalSplits){
            finalSplitService.deleteFinalSplit(tempFinalSplit.getId());
        }
        return "redirect:/group/settleUp?groupId=" + groupId;
    }
//    @GetMapping("/addMember")
//    public String
}

