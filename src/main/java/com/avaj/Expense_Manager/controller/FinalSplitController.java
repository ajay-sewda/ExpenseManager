package com.avaj.Expense_Manager.controller;

import com.avaj.Expense_Manager.entity.FinalSplit;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.ExpenseService;
import com.avaj.Expense_Manager.service.FinalSplitService;
import com.avaj.Expense_Manager.service.GroupService;
import com.avaj.Expense_Manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FinalSplitController {
    private UserService userService;
    private GroupService groupService;
    private FinalSplitService finalSplitService;
    private ExpenseService expenseService;
    @Autowired
    public FinalSplitController(UserService userService, GroupService groupService, FinalSplitService finalSplitService,ExpenseService expenseService) {
        this.userService = userService;
        this.groupService = groupService;
        this.finalSplitService = finalSplitService;
        this.expenseService = expenseService;
    }

    @GetMapping("/balances")
    public String showBalances(@RequestParam("groupId") Long groupId, Model theModel, Principal principal){
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
            if(tempFinalSplit.getFinalAmt()!=0 && (user.getId()==tempFinalSplit.getFinalPayTo() || user.getId()==tempFinalSplit.getFinalPayBy())){
                finalSplits.add(tempFinalSplit);
            }
        }
        Group group = groupService.getGroupById(groupId);
        theModel.addAttribute("finalSplits",finalSplits);
        theModel.addAttribute("group", group);
        theModel.addAttribute("userId",user.getId());
        return "settleUp";
    }
    @GetMapping("/settleUpProcess")
    public String processSettleUp(@RequestParam("finalSplitId") Long finalSplitId, Principal principal){
        User user = userService.getUserByUserName(principal.getName());
        Long groupId = finalSplitService.getFinalSplitById(finalSplitId).getFinalSplitGrp().getId();
        finalSplitService.settleUp(finalSplitService.getFinalSplitById(finalSplitId),user,groupId);
        return "redirect:/settleUp?groupId=" + groupId;
    }
    @GetMapping("/settleUpAll")
    public String processSettleAll(@RequestParam("groupId") Long groupId, Principal principal){
        User user = userService.getUserByUserName(principal.getName());
        List<FinalSplit> finalSplits = new ArrayList<>();
        for(FinalSplit tempFinalSplit:finalSplitService.getFinalSplit(groupId)){
           finalSplitService.settleUp(tempFinalSplit,user,groupId);
        }
        return "redirect:/settleUp?groupId=" + groupId;
    }
    @GetMapping("/settleUpBalance")
    public String settleFromBalances(@RequestParam("finalSplitId") Long finalSplitId,Principal principal){
        User user = userService.getUserByUserName(principal.getName());
        Long groupId = finalSplitService.getFinalSplitById(finalSplitId).getFinalSplitGrp().getId();
        finalSplitService.settleUp(finalSplitService.getFinalSplitById(finalSplitId),user,groupId);
        return "redirect:/balances?groupId="+groupId;
    }
}
