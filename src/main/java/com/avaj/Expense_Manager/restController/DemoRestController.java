package com.avaj.Expense_Manager.restController;

import com.avaj.Expense_Manager.entity.Expense;
import com.avaj.Expense_Manager.entity.Group;
import com.avaj.Expense_Manager.entity.User;
import com.avaj.Expense_Manager.service.ExpenseService;
import com.avaj.Expense_Manager.service.FinalSplitService;
import com.avaj.Expense_Manager.service.GroupService;
import com.avaj.Expense_Manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class DemoRestController {

    private final UserService userService;
    private final ExpenseService expenseService;
    private final GroupService groupService;
    private final FinalSplitService finalSplitService;

    @Autowired
    public DemoRestController(UserService userService, ExpenseService expenseService,
                              GroupService groupService, FinalSplitService finalSplitService) {
        this.userService = userService;
        this.expenseService = expenseService;
        this.groupService = groupService;
        this.finalSplitService = finalSplitService;
    }
    @ResponseBody
    @PostMapping("/register/createUser")
    public ResponseEntity<User> create(@Valid @RequestBody User user, BindingResult result) {
        User newUser=new User();
        if(result.hasErrors()){
            return new ResponseEntity<>(newUser, HttpStatus.NOT_ACCEPTABLE);

        }
        try {
            newUser=userService.createUser(user);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(newUser, HttpStatus.OK);

    }
    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{userName}/createGroups")
    public ResponseEntity<User> createGroup(@PathVariable String userName,@RequestBody Group group) {

        User user =userService.getUserByUserName(userName);
//         Group theGroup=groupService.createGroup(group);
        List<Group>groupList= userService.getUserGroups(user.getId());
        groupList.add(group);
        user.setUserGroups(groupList);
        User user1=userService.updateUser(user);
        return new ResponseEntity<>(user1, HttpStatus.OK);
    }
    //Add Expense
    @PostMapping("/{userName}/group/{groupId}/addExpense")
    public ResponseEntity<User> addExpense(@PathVariable String userName,@PathVariable Long groupId,@RequestBody Expense expense) {

        User user =userService.getUserByUserName(userName);
        User user1=new User();
        List<Group>groupList=user.getUserGroups();
//        if(groupList.stream().anyMatch(x->x.getId().equals(groupId))){
//            List<Expense>expenseList= userService.getUserExpenses(user.getId());
//            expenseList.add(expense);
//            user.setExpenses(expenseList);
//            user1=userService.updateUser(user);
//        Expense expense1=expenseService.getExpenseById(expense.getId());
//        expense1.setExpGrp(groupService.getGroupById(groupId));
//        expenseService.updateExpense(expense1);
//        }
        for(int i=0;i<groupList.size();i++){
            if(groupList.get(i).getId().equals(groupId)){
                List<Expense>expenseList= userService.getUserExpenses(user.getId());
                expenseList.add(expense);
                user.setExpenses(expenseList);
                user1=userService.updateUser(user);
                Float totalExpense= groupService.getGroupById(groupId).getTotalExpense();
                totalExpense+=expense.getExpAmt();
                Group group= groupService.getGroupById(groupId);
                group.setTotalExpense(totalExpense);
                Expense expense1=expenseService.getExpenseById(expense.getId());
                expense1.setExpGrp(group);
                expenseService.updateExpense(expense1);

            }
        }

        return new ResponseEntity<>(user1, HttpStatus.OK);
    }
    // User endpoints
//    @GetMapping("/users/{userId}/Groups")
//    public ResponseEntity<List<Group>> getAllGroups(@PathVariable Long userId) {
//        List<Group> groups = userService.getUserGroups(userId);
//        return new ResponseEntity<>(groups, HttpStatus.OK);
//    }


//    @ResponseBody
//    @PostMapping("/createExpense")
//    public ResponseEntity<Expense>createExpense(@RequestBody  Expense expense){
//        expenseService.createExpense(expense);
//          return new ResponseEntity<>(expense, HttpStatus.OK);
//
//    }


    // Implement other user endpoints as needed

    // Expense endpoints

//    @GetMapping("/expenses/{expenseId}")
//    public ResponseEntity<Expense> getExpenseById(@PathVariable Long expenseId) {
//        Expense expense = expenseService.getExpenseById(expenseId);
//        if (expense != null) {
//            return new ResponseEntity<>(expense, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    // Implement other expense endpoints as needed

    // Group endpoints

//    @GetMapping("/groups/{groupId}/expenses")
//    public ResponseEntity<List<Expense>> getAllExpenses(@PathVariable Long groupId) {
//        List<Expense> expenses = expenseService.getGroupExpenses(groupId);
//        return new ResponseEntity<>(expenses, HttpStatus.OK);
//    }



//    @GetMapping("/groups/{groupId}/finalSplits")
//    public ResponseEntity<List<FinalSplit>> getAllFinalSplits(@PathVariable Long groupId) {
//        List<FinalSplit> finalSplits = finalSplitService.getFinalSplit(groupId);
//        return new ResponseEntity<>(finalSplits, HttpStatus.OK);
//    }

    // Implement other group endpoints as needed

    // Final Split endpoints


    // Implement other final split endpoints as needed
}
