package com.avaj.Expense_Manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name="finalsplit")
public class FinalSplit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="paid_to")
    private Long finalPayTo;

    @Column(name="paid_by")
    private Long finalPayBy;

    @Column(name="amount")
    private Float finalAmt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group finalSplitGrp;

}

