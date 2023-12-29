package com.avaj.Expense_Manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="`group`")
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
    private Float totalExpense=0F;

    @JsonIgnore
    @OneToMany(mappedBy = "finalSplitGrp")
    private List<FinalSplit> finalSplits;

    @JsonIgnore
    @OneToMany(mappedBy = "expGrp",cascade = CascadeType.ALL)
    private  List<Expense> expenses;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "user_group",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> groupUsers;
}
