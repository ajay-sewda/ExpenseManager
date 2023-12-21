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
@Table(name="User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="first_name")
    @NotEmpty(message = "First name is required")
    private String userFirstName;

    @Column(name="last_name")
    private String userLastName;

    @Column(name="username")
    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String userName;

    //    @Column(length = 68)
    @Column(name="password", unique = true)
    @JsonIgnore
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String userPassword;

    @Column(name="enabled")
    private Boolean enabled = true;

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
