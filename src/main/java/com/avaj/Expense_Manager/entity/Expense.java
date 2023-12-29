package com.avaj.Expense_Manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="description")
    private String expName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="date")
    private Date date;

    @Column(name="amount")
    private Float expAmt;

    @Column(name="paid_by")
    private Long expPaidBy;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_expense",
            joinColumns = {@JoinColumn(name = "expense_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> usrSplitBtw;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group expGrp;
}


/*
<div>
    <!-- Display final split -->
    <label>Final Split: </label>
    <ul>
        <li th:each="split : ${finalSplit}">
            <span th:with="loggedUser=${group.groupUsers.?[userName == #authentication.principal.username][0]}">
                <span th:with="otherUser=${group.groupUsers.?[id == __${split.finalPayTo}__][0]}">
                    <span th:if="${split.finalPayBy == loggedUser.id}">
                        <!-- Display the message when the logged-in user paid -->
                        <span th:text="${otherUser.firstName} + ' owes you ' + ${split.finalAmt}"></span>
                    </span>
                    <span th:if="${split.finalPayTo == loggedUser.id}">
                        <!-- Display the message when the logged-in user is owed -->
                        <span th:text="'You owe ' + ${otherUser.firstName} + ' ' + ${split.finalAmt}"></span>
                    </span>
                </span>
            </span>
        </li>
    </ul>
</div>

*/

























