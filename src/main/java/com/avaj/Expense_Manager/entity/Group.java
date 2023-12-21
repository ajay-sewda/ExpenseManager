package com.avaj.Expense_Manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="group_name")
    @NotEmpty(message = "Group name is required")
    @Size(min = 2, max = 50, message = "Group name must be between 2 and 50 characters")
    private String groupName;

    @Column(name="group_type")
    private String groupType;

    @Column(name="total_expense")
    private Long totalExpense;

    @OneToMany(mappedBy = "finalSplitGrp")
    @JsonIgnore
    private List<FinalSplit> finalSplits;

    @OneToMany(mappedBy = "expGrp")
    @JsonIgnore
    private  List<Expense> expenses;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "User_Group",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @JsonIgnore
    private List<User> groupUsers;
}
