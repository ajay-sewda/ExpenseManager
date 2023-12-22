package com.avaj.Expense_Manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="first_name")
    @NotEmpty(message = "First name is required")
    private String FirstName;

    @Column(name="last_name")
    private String LastName;

    @Column(name="role")
    private String role;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonIgnore
    @JoinTable(name = "User_Group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private List<Group> userGroups;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "User_Expense",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "expense_id")})
    @JsonIgnore
    private List<Expense> expenses;
}
