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
@ToString(exclude = {"usrSplitBtw", "expGrp"})
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