package com.avaj.Expense_Manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="FinalSplit")
public class FinalSplit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="paid_to")
    private String finalPayTo;

    @Column(name="paid_by")
    private String finalPayBy;

    @Column(name="amount")
    private Float finalAmt;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group finalSplitGrp;

}

