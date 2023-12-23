//package com.avaj.Expense_Manager.restController;
//
//import com.avaj.Expense_Manager.entity.*;
//import com.avaj.Expense_Manager.service.*;
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
//    private final LoginService loginService;
//
//    @Autowired
//    public DemoRestController(UserService userService, ExpenseService expenseService,
//                                    GroupService groupService, FinalSplitService finalSplitService,LoginService loginService) {
//        this.userService = userService;
//        this.expenseService = expenseService;
//        this.groupService = groupService;
//        this.finalSplitService = finalSplitService;
//        this.loginService=loginService;
//    }
////Login details endpoints
//    @PostMapping("users/login")
//    @ResponseBody
//    public String loginUserDetails(@RequestBody LoginDetail loginDetail){
//        loginService.saveUser(loginDetail);
//                return "User with username "+loginDetail.getUserName()+"has been logged in successfully";
//    }
//
//    // User endpoints
//    @GetMapping("/users/{userName}/Groups")
//    public ResponseEntity<List<Group>> getAllGroups(@PathVariable String userName) {
//        List<Group> groups = userService.getUserGroups(userName);
//        return new ResponseEntity<>(groups, HttpStatus.OK);
//    }
//
//    @GetMapping("/users/{userName}")
//    public ResponseEntity<User> getUserById(@PathVariable String userName) {
//        User user = userService.getUserByUserName(userName);
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
