//package com.avaj.Expense_Manager.restController;
//
//import com.avaj.Expense_Manager.entity.Expense;
//import com.avaj.Expense_Manager.entity.FinalSplit;
//import com.avaj.Expense_Manager.entity.Group;
//import com.avaj.Expense_Manager.entity.User;
//import com.avaj.Expense_Manager.service.ExpenseService;
//import com.avaj.Expense_Manager.service.FinalSplitService;
//import com.avaj.Expense_Manager.service.GroupService;
//import com.avaj.Expense_Manager.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class DemoRestController {
//
//    private final UserService userService;
//    private final ExpenseService expenseService;
//    private final GroupService groupService;
//    private final FinalSplitService finalSplitService;
//
//    @Autowired
//    public DemoRestController(UserService userService, ExpenseService expenseService,
//                                    GroupService groupService, FinalSplitService finalSplitService) {
//        this.userService = userService;
//        this.expenseService = expenseService;
//        this.groupService = groupService;
//        this.finalSplitService = finalSplitService;
//    }
//
//    // User endpoints
//    @GetMapping("/users/{userId}/Groups")
//    public ResponseEntity<List<Group>> getAllGroups(@PathVariable Long userId) {
//        List<Group> groups = userService.getUserGroups(userId);
//        return new ResponseEntity<>(groups, HttpStatus.OK);
//    }
//
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
//        User user = userService.getUserById(userId);
//        if (user != null) {
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Implement other user endpoints as needed
//
//    // Expense endpoints
//
//    @GetMapping("/expenses/{expenseId}")
//    public ResponseEntity<Expense> getExpenseById(@PathVariable Long expenseId) {
//        Expense expense = expenseService.getExpenseById(expenseId);
//        if (expense != null) {
//            return new ResponseEntity<>(expense, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Implement other expense endpoints as needed
//
//    // Group endpoints
//
//    @GetMapping("/groups/{groupId}/expenses")
//    public ResponseEntity<List<Expense>> getAllExpenses(@PathVariable Long groupId) {
//        List<Expense> expenses = expenseService.getGroupExpenses(groupId);
//        return new ResponseEntity<>(expenses, HttpStatus.OK);
//    }
//
//    @GetMapping("/groups/{groupId}")
//    public ResponseEntity<Group> getGroupById(@PathVariable Long groupId) {
//        Group group = groupService.getGroupById(groupId);
//        if (group != null) {
//            return new ResponseEntity<>(group, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/groups/{groupId}/finalSplits")
//    public ResponseEntity<List<FinalSplit>> getAllFinalSplits(@PathVariable Long groupId) {
//        List<FinalSplit> finalSplits = finalSplitService.getFinalSplit(groupId);
//        return new ResponseEntity<>(finalSplits, HttpStatus.OK);
//    }
//
//    // Implement other group endpoints as needed
//
//    // Final Split endpoints
//
//
//    // Implement other final split endpoints as needed
//}
