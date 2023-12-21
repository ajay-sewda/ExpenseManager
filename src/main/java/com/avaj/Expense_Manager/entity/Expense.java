package com.avaj.Expense_Manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="description")
    private String expName;

    @Column(name="date")
    private Date date;

    @Column(name="amount")
    private Float expAmt;

    @Column(name="paid_by")
    private Long expPaidBy;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "User_Expense",
            joinColumns = {@JoinColumn(name = "expense_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @JsonIgnore
    private List<User> usrSplitBtw;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group expGrp;
}
