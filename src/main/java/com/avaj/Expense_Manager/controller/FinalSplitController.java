//package com.avaj.Expense_Manager.controller;
//
//import com.avaj.Expense_Manager.entity.FinalSplit;
//import com.avaj.Expense_Manager.service.FinalSplitService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/finalSplit")
//public class FinalSplitController {
//
//    private final FinalSplitService finalSplitService;
//
//    @Autowired
//    public FinalSplitController(FinalSplitService finalSplitService) {
//        this.finalSplitService = finalSplitService;
//    }
//
//    @GetMapping("/list/{groupId}")
//    public String getFinalSplit(@PathVariable("groupId") Long groupId, Model model) {
//        List<FinalSplit> finalSplits = finalSplitService.getFinalSplit(groupId);
//        model.addAttribute("finalSplits", finalSplits);
//        return "finalSplitList"; // Assuming finalSplitList.html Thymeleaf template exists
//    }
//
//    // Other methods for adding, updating, deleting Final Splits based on your requirements
//}
